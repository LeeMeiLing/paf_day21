package sg.edu.nus.iss.paf_day21.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.paf_day21.model.Room;
import sg.edu.nus.iss.paf_day21.repository.IRoomRepository;

@Service
public class RoomService {
    
    @Autowired
    IRoomRepository roomRepo;

    public int count() {
        return roomRepo.count();
    }


    public Boolean save(Room room) {
       return roomRepo.save(room);
    }

    
    public List<Room> findAll() {
        return roomRepo.findAll();
    }

    
    public Room findById(Integer id) {

        // return roomRepo.findById(id);
        try{
            return roomRepo.findById(id);
        }catch(Exception ex){
            throw ex;
        }
        
    }

    public int update(Room room) {
        return roomRepo.update(room);
    }

    public int deleteById(Integer id) {
        return roomRepo.deleteById(id);
    }


}
