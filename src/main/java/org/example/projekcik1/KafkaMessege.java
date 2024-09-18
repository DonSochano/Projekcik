package org.example.projekcik1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessege {
    private Long id;
    private Integer lineNumber;
    private String name;
    private String lastName;
    private Integer Age;
    private Double salary;
    private String town;


}
