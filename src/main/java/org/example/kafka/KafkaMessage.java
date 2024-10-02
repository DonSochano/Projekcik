package org.example.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.models.TopicMessage;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage implements TopicMessage { // do zmiany nazwa opisana gdzie indziej

    private Long id;
    private Integer lineNumber;
    private String name;
    private String lastName;
    private Integer age;
    private Double salary;
    private String town;

}
