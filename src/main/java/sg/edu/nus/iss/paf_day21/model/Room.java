package sg.edu.nus.iss.paf_day21.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    
    private Integer id;
    private String roomType;
    private Float price;
 
}
