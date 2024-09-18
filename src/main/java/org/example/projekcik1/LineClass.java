package org.example.projekcik1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class LineClass {

    @Id
    private Long lineID;
    private Integer lineNumber;
    private Long fileId;
    private String name;
    private String lastName;
    private Integer age;
    private Double salary;
    private String town;

}
