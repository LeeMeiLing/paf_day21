package sg.edu.nus.iss.paf_day21.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dependent {
    
    private Integer id;
    private Integer employeeId;
    private String fullName;
    private String relationship;
    private Date birthDate;

    private Employee employee;

}
