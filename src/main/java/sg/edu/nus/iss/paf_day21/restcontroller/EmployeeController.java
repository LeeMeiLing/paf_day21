package sg.edu.nus.iss.paf_day21.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.paf_day21.model.Employee;
import sg.edu.nus.iss.paf_day21.service.EmployeeService;

@RestController
@RequestMapping(path={"/api/employees"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {
    
    @Autowired
    EmployeeService empSvc;

    @PostMapping
    public ResponseEntity<Boolean> save(Employee employee){
        Boolean saved = empSvc.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping
    public ResponseEntity<Integer> update(Employee employee){ // try w/o @RequestBody
        Integer updated = empSvc.update(employee);  
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id){
        Integer deleted = empSvc.delete(id);  
        return ResponseEntity.status(HttpStatus.CREATED).body(deleted);   
    }


    @GetMapping
    public ResponseEntity<List<Employee>> findAll(){
        List<Employee> employeeList = empSvc.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(employeeList);   
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findByEmployeeId(@PathVariable Integer id){
        Employee employee = empSvc.findByEmployeeId(id);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

}
