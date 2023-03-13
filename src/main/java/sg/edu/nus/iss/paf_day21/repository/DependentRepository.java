package sg.edu.nus.iss.paf_day21.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_day21.model.Dependent;
import sg.edu.nus.iss.paf_day21.model.Employee;

@Repository
public class DependentRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    String findAllSQL = "select dep.id as dependent_id, dep.full_name, dep.relationship, dep.birth_date," +
                        " emp.id as employee_id, emp.first_name, emp.last_name, emp.salary" +
                        " from dependent as dep" +
                        " inner join employee as emp" +
                        " on dep.employee_id = emp.id";

    String findByIdSQL = "select dep.id as dependent_id, dep.full_name, dep.relationship, dep.birth_date," +
                        " emp.id as employee_id, emp.first_name, emp.last_name, emp.salary" +
                        " from dependent as dep" +
                        " inner join employee as emp" +
                        " on dep.employee_id = emp.id" +
                        " where dep.id = ?";
    
    String insertSQL = "insert into dependent (employee_id, full_name, relationship, birth_date ) values (?,?, ?, ?)";
    
    String updateSQL = "update dependent set employee_id = ?, full_name = ?, relationship = ? , birth_date = ? where id=?";
    
    String deleteSQL = "delete from dependent where id = ?";

    // Read All
    public List<Dependent> findAll(){

        List<Dependent> dependents = new ArrayList<>();
        
        dependents = jdbcTemplate.query(findAllSQL, new ResultSetExtractor<List<Dependent>>() {

            @Override
            public List<Dependent> extractData(ResultSet rs) throws SQLException, DataAccessException {

                List<Dependent> deps = new ArrayList<>();
        
                while(rs.next()){

                    // dependent_id, full_name, relationship, birth_date
                    Dependent dep = new Dependent();
                    dep.setId(rs.getInt("dependent_id"));
                    dep.setEmployeeId(rs.getInt("employee_id"));
                    dep.setFullName(rs.getString("full_name"));
                    dep.setRelationship(rs.getString("relationship"));
                    dep.setBirthDate(rs.getDate("birth_date"));
                    
                    // employee_id, first_name, last_name,salary
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("employee_id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    emp.setSalary(rs.getInt("salary"));

                    dep.setEmployee(emp);
                    
                    deps.add(dep);

                }

                return deps;

            }
                
        });

        return dependents;

    }

    // Read by Id
    public Dependent findById(Integer id){

        RowMapper<Dependent> rowMapper = new RowMapper<>() {

            @Override
            public Dependent mapRow(ResultSet rs, int rowNum) throws SQLException {

                // dependent_id, full_name, relationship, birth_date
                Dependent dep = new Dependent();
                dep.setId(rs.getInt("dependent_id"));
                dep.setEmployeeId(rs.getInt("employee_id"));
                dep.setFullName(rs.getString("full_name"));
                dep.setRelationship(rs.getString("relationship"));
                dep.setBirthDate(rs.getDate("birth_date"));
                
                // employee_id, first_name, last_name,salary
                Employee emp = new Employee();
                emp.setId(rs.getInt("employee_id"));
                emp.setFirstName(rs.getString("first_name"));
                emp.setLastName(rs.getString("last_name"));
                emp.setSalary(rs.getInt("salary"));

                dep.setEmployee(emp);

                return dep;
            }
            
        };

        return jdbcTemplate.queryForObject(findByIdSQL, rowMapper, id);

    }

    // Create
    public Boolean save(Dependent dependent){

        Boolean bSave = false;

        PreparedStatementCallback<Boolean> psc = new PreparedStatementCallback<Boolean>() {

            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

                // employee_id, full_name, relationship, birth_date 
                // ps.setInt(1, dependent.getEmployee().getId());
                ps.setInt(1, dependent.getEmployeeId());
                ps.setString(2, dependent.getFullName());
                ps.setString(3,dependent.getRelationship());
                ps.setDate(4, dependent.getBirthDate());

                try{
                    ps.execute(); // !!!!!!! cannot use return ps.execute() because it returns null for update
                    return true;
                }catch(Exception ex){
                    return false;
                }
            }
            
        };

        bSave = jdbcTemplate.execute(insertSQL,psc);

        return bSave;
    }

    // Update By Id
    public int update(Dependent dependent){

        int iUpdated = 0;

        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                // ps.setInt(1, dependent.getEmployee().getId());
                ps.setInt(1, dependent.getEmployeeId());
                ps.setString(2, dependent.getFullName());
                ps.setString(3,dependent.getRelationship());
                ps.setDate(4, dependent.getBirthDate());
                ps.setInt(5, dependent.getId());
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
