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

import sg.edu.nus.iss.paf_day21.model.Dependent;
import sg.edu.nus.iss.paf_day21.service.DependentService;

@RestController
@RequestMapping(path={"/api/dependents"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class DependentController {
    
    @Autowired
    DependentService depSvc;

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody Dependent dependent){
        Boolean saved = depSvc.save(dependent);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping
    public ResponseEntity<Integer> update(@RequestBody Dependent dependent){
        Integer updated = depSvc.update(dependent);  
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id){
        Integer deleted = depSvc.delete(id);  
        return ResponseEntity.status(HttpStatus.CREATED).body(deleted);   
    }


    @GetMapping
    public ResponseEntity<List<Dependent>> findAll(){
        List<Dependent> dependents = depSvc.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(dependents);   
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dependent> findByDependentId(@PathVariable Integer id){
        Dependent dependent = depSvc.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dependent);
    }

}
