package sg.edu.nus.iss.paf_day21.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_day21.model.Dependent;
import sg.edu.nus.iss.paf_day21.model.Employee;

@Repository
public class EmployeeRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    String findAllSQL = "select emp.id as employee_id, emp.first_name, emp.last_name, emp.salary," + 
        " dep.id as dependent_id, dep.full_name as dependent_full_name, dep.relationship, dep.birth_date as dependent_birth_date" +
        " from employee as emp" +
        " inner join dependent as dep" +
        " on emp.id = dep.employee_id";

    String findByIdSQL = "select emp.id as employee_id, emp.first_name, emp.last_name, emp.salary," + 
        " dep.id as dependent_id, dep.full_name as dependent_full_name, dep.relationship, dep.birth_date as dependent_birth_date" +
        " from employee as emp" +
        " inner join dependent as dep" +
        " on emp.id = dep.employee_id" +
        " where emp.id = ?";
    
    String insertSQL = "insert into employee (first_name, last_name,salary) values (?, ?, ?)";
    
    String updateSQL = "update employee set first_name = ?, last_name = ?, salary = ? where id=?";
    
    String deleteSQL = "delete from employee where id = ?";


    // Read All
    public List<Employee> findAll(){

        List<Employee> employeeList = new ArrayList<>();
        
        employeeList = jdbcTemplate.query(findAllSQL, new ResultSetExtractor<List<Employee>>() {

            @Override
            public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {

                List<Employee> emps = new ArrayList<>();
        
                while(rs.next()){

                    //  employee_id, emp.first_name, emp.last_name, emp.salary
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("employee_id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    emp.setSalary(rs.getInt("salary"));

                    //  dependent_id, dependent_full_name, dep.relationship, dependent_birth_date
                    Dependent dep = new Dependent();
                    dep.setId(rs.getInt("dependent_id"));
                    dep.setFullName(rs.getString("dependent_full_name"));
                    dep.setRelationship(rs.getString("relationship"));
                    dep.setBirthDate(rs.getDate("dependent_birth_date"));

                    if (emps.size() == 0){

                        emp.getDependents().add(dep);
                        emps.add(emp);

                    }else{
                    // if array ady has employee, enter this else loop

                        List<Employee> tmpList = emps.stream().filter(e -> e.getId() == emp.getId()).collect(Collectors.toList());

                        if( tmpList.size() > 0) {

                            // append to dependent list for the found employee
                            int idx = emps.indexOf(tmpList.get(0));

                            if(idx >= 0){
                                emps.get(idx).getDependents().add(dep);
                            }

                        }else{
                            // if the employee record not found in the list of employees
                            // add a new employee record together with the dependent information
                            emp.getDependents().add(dep);
                            emps.add(emp);
                        }

                    }

                }

                return emps;
            }
            
        });

        return employeeList;

    }

    // Read by Id
    public Employee findById(Integer id){

        return jdbcTemplate.query(findByIdSQL, new ResultSetExtractor<Employee>() {

            @Override
            public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {

                Employee employee = new Employee();

                // Boolean flag = true;

                while(rs.next()){
                    //  employee_id, emp.first_name, emp.last_name, emp.salary
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("employee_id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    emp.setSalary(rs.getInt("salary"));

                    //  dependent_id, dependent_full_name, dep.relationship, dependent_birth_date
                    Dependent dep = new Dependent();
                    dep.setId(rs.getInt("dependent_id"));
                    dep.setFullName(rs.getString("dependent_full_name"));
                    dep.setRelationship(rs.getString("relationship"));
                    dep.setBirthDate(rs.getDate("dependent_birth_date"));

                    // if (flag){      
                    if(rs.isFirst()){

                        // only enter here for the first time
                        employee = emp;
                        employee.getDependents().add(dep);
                        // flag = false; 

                    }else{

                        employee.getDependents().add(dep);

                    }
                }

                return employee;
            }

        },id);

    }

    // Create
    public Boolean save(Employee employee){

        Boolean bSave = false;
        PreparedStatementCallback<Boolean> psc = new PreparedStatementCallback<Boolean>() {

            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, employee.getFirstName());
                ps.setString(2, employee.getLastName());
                ps.setInt(3, employee.getSalary());

                ps.execute(); // !!!!!!!
                return true;
            }
            
        };

        bSave = jdbcTemplate.execute(insertSQL,psc);

        return bSave;
    }

    // Update By Id
    public int update(Employee employee){

        int iUpdated = 0;

        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, employee.getFirstName());
                ps.setString(2, employee.getLastName());
                ps.setInt(3, employee.getSalary());
                ps.setInt(4, employee.getId());
            }
            
        };

        iUpdated = jdbcTemplate.update(updateSQL, pss);

        return iUpdated;

    }

    // Delete By Id
    public int delete(Integer id){

        int iDeleted = 0;

        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }
            
        };

        iDeleted = jdbcTemplate.update(deleteSQL, pss);

        return iDeleted;

    }

}
