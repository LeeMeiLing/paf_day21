package sg.edu.nus.iss.paf_day21.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.paf_day21.model.Customer;
import sg.edu.nus.iss.paf_day21.repository.CustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    CustomerRepository custRepo;

    public List<Customer> retrieveAllCustomers(){
        
        return custRepo.getAllCustomers();
    }

    public List<Customer> retrieveAllCustomersWithLimitOffset(int limit, int offset){
        
        return custRepo.getAllCustomersWithLimitOffset(limit,offset);
    }

    
}
