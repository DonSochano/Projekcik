package org.example.projekcik1;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class LineClass {
    private Integer LineNumber;
    private Long fileId;
    private String Name;
    private String lastName;
    private Integer Age;
    private Double Salary;
    private String Town;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LineID;



public LineClass(Integer LineNumber, Long fileId, String Name, String lastName, int Age, Double Salary, String Town) {
    this.LineNumber = LineNumber;
    this.fileId = fileId;
    this.Name = Name;
    this.lastName = lastName;
    this.Age = Age;
    this.Salary = Salary;
    this.Town = Town;
}
public LineClass() {}
}
