package sg.edu.nus.iss.paf_day21.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.paf_day21.exception.ResourceNotFoundException;
import sg.edu.nus.iss.paf_day21.model.Room;
import sg.edu.nus.iss.paf_day21.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    
    @Autowired
    RoomService roomSvc;

    @GetMapping("/count")
    public ResponseEntity<Integer> getRoomCount(){

        Integer roomCount = roomSvc.count();
        return new ResponseEntity<Integer>(roomCount, HttpStatus.OK);
        // return ResponseEntity.ok().body(roomCount);   // alternative way

    }

    @GetMapping()
    public ResponseEntity<List<Room>> getAllRooms(){

        List<Room> rooms = new ArrayList<>();
        rooms = roomSvc.findAll();

        if(rooms.isEmpty()){

            throw new ResourceNotFoundException("No room found"); // using custom exception class
            // return ResponseEntity.noContent().build();
            // return ResponseEntity<>(HttpStatus.NO_CONTENT); // alternative way

        }else{
            return ResponseEntity.ok().body(rooms);
        }


    }


    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Integer id){
        
        // Room room = roomSvc.findById(id);
        // if(room == null){
        //     return ResponseEntity.noContent().build();
        // }else{
        //     return ResponseEntity.ok().body(room);
        // }

        try{
            Room room = roomSvc.findById(id);
            return ResponseEntity.ok().body(room);
        }catch(Exception ex){
            throw new ResourceNotFoundException("Room not found"); // using custom exception class
        }

        
    }

    @PostMapping
    public ResponseEntity<Boolean> createRoom(@RequestBody Room room){

        System.out.println(room);

        Boolean created = roomSvc.save(room);

        if(created){
            return new ResponseEntity<Boolean>(created, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<Boolean>(created, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Integer> updateRoom(@RequestBody Room room) {
        int updated = 0;

        updated = roomSvc.update(room);

        if (updated == 1){
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(updated, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteRoom(@PathVariable Integer id){
        
        int deleted = 0;

        deleted = roomSvc.deleteById(id);

        if (deleted == 1){
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(deleted, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
