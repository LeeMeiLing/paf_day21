package sg.edu.nus.iss.paf_day21.repository;

import java.util.List;

import sg.edu.nus.iss.paf_day21.model.Room;

public interface IRoomRepository {
    
    int count();

    Boolean save(Room room);

    List<Room> findAll();

    Room findById(Integer id);

    int update(Room room);

    int deleteById(Integer id);
    
}
