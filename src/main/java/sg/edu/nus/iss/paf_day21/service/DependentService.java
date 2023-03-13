package sg.edu.nus.iss.paf_day21.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.paf_day21.model.Dependent;
import sg.edu.nus.iss.paf_day21.repository.DependentRepository;

@Service
public class DependentService{
    
    @Autowired
    DependentRepository depRepo;

    public List<Dependent> findAll() {
        return depRepo.findAll();
    }

    public Dependent findById(Integer id) {
        return depRepo.findById(id);
    }

    public Boolean save(Dependent dependent) {
        return depRepo.save(dependent);
    }

    public int update(Dependent dependent) {
        return depRepo.update(dependent);
    }

    public int delete(Integer id) {
        return depRepo.delete(id);
    }

    

}
