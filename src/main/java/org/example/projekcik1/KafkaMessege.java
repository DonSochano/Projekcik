package org.example.projekcik1;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KafkaMessege{
    private Long id;
    private Integer lineNumber;
    private String name;
    private String lastName;
    private Integer Age;
    private Double salary;
    private String town;
    public KafkaMessege(){}
    public KafkaMessege(Long id, Integer lineNumber, String name, String lastName, Integer Age, Double salary, String town){
        this.id = id;
        this.lineNumber = lineNumber;
        this.name = name;
        this.lastName = lastName;
        this.Age = Age;
        this.salary = salary;
        this.town = town;
    }
}
