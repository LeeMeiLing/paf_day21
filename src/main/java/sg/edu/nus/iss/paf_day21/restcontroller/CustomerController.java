package sg.edu.nus.iss.paf_day21.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.paf_day21.model.Customer;
import sg.edu.nus.iss.paf_day21.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    
    @Autowired
    CustomerService custSvc;

    @GetMapping
    public List<Customer> getAllCustomers(){
        return custSvc.retrieveAllCustomers();
    }

    @GetMapping("/limit")
    public List<Customer> getAllCustomers(@RequestParam int limit, @RequestParam int offset){
        return custSvc.retrieveAllCustomersWithLimitOffset(limit, offset);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable int id){
        return custSvc.findCustomerById(id);
    }
}
