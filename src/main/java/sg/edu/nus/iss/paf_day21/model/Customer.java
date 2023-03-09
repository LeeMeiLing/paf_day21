package sg.edu.nus.iss.paf_day21.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    private Integer id;

    private String firstName;

    private String lastName;

    private Date dob;
}
