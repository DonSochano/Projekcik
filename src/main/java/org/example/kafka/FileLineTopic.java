package org.example.kafka;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class FileLineTopic implements Topic<LineDTO> {

    @Override
    public final String getName() {
        return "lineDTOTopic";
    }
}
