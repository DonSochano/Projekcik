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
    private Long LineID;
    private Integer lineNumber;
    private Long fileId;
    private String name;
    private String lastName;
    private Integer age;
    private Double salary;
    private String town;


    public LineClass(Integer LineNumber, Long fileId, String Name, String lastName, int Age, Double Salary, String Town) { // nie uzywane wiec po co ?
        this.lineNumber = LineNumber;
        this.fileId = fileId;
        this.name = Name;
        this.lastName = lastName;
        this.age = Age;
        this.salary = Salary;
        this.town = Town;
    }
}
