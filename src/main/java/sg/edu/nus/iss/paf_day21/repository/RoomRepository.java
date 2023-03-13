package sg.edu.nus.iss.paf_day21.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_day21.model.Room;

@Repository
public class RoomRepository implements IRoomRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String countSQL = "select count(*) from room";
    private final String findAllSQL = "select * from room";
    private final String findByIdSQL = "select * from room where id = ?";
    private final String deleteByIdSQL = "delete from room where id = ?";
    private final String insertSQL = "insert into room (room_type, price) values (?,?)";
    private final String updateSQL = "update room set price = ? where id = ?";

    @Override
    public int count() {

        Integer result = 0;
        result = jdbcTemplate.queryForObject(countSQL, Integer.class);

        if (result == null){
            return 0;
        } else {
            return result;
        }

    }

    // CREATE single record using jdbcTemplate.execute( with PreparedStatementCallback )
    @Override
    public Boolean save(Room room) {

        Boolean saved = false;

        saved = jdbcTemplate.execute(insertSQL, new PreparedStatementCallback<Boolean>() {

            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

                ps.setString(1, room.getRoomType());
                ps.setFloat(2, room.getPrice());
                Boolean result = ps.execute(); // return false if no resultset
                return true; // cannot use return result
            }
            
        });

        return saved;

    }

    // READ all
    @Override
    public List<Room> findAll() {
        
        List<Room> rooms = new ArrayList<>();
        rooms = jdbcTemplate.query(findAllSQL, BeanPropertyRowMapper.newInstance(Room.class));

        return rooms;
    }

    // READ specific using jdbcTemplate.queryForObject()
    @Override
    public Room findById(Integer id) {
        
        // return jdbcTemplate.queryForObject(findByIdSQL, 
        //     BeanPropertyRowMapper.newInstance(Room.class), id);

        try{
            return jdbcTemplate.queryForObject(findByIdSQL, 
            BeanPropertyRowMapper.newInstance(Room.class), id);
        }catch(Exception ex){
            throw ex;
        }

    }

    // UPDATE single record using jdbcTemplate.update( with PSS to replace ? )
    @Override
    public int update(Room room) {
        
        int updated = 0;

        updated = jdbcTemplate.update(updateSQL, new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setFloat(1, room.getPrice());
                ps.setInt(2, room.getId());
            }
            
        });

        return updated;
    }


    // DELETE single record using jdbcTemplate.update( with PSS to replace ? )
    @Override
    public int deleteById(Integer id) {
        
        int deleted = 0;

        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }
            
        };

        deleted = jdbcTemplate.update(deleteByIdSQL, pss);

        return deleted;
    }
    

}
