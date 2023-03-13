package sg.edu.nus.iss.paf_day21.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    
    private Integer id;
    private String firstName;  // firstName(Entity) --> first_name(MySQL)
    private String lastName;
    private Integer salary;

    private List<Dependent> dependents = new ArrayList<>();

}
