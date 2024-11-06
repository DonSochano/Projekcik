package org.example.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LineDTO implements TopicMessage {

    private Long id;
    private Integer lineNumber;
    private String name;
    private String lastName;
    private Integer age;
    private Double salary;
    private String town;

}
