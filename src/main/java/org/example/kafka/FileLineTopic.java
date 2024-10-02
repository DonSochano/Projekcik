package org.example.kafka;

import lombok.NoArgsConstructor;
import org.example.models.Topic;

@NoArgsConstructor
public class FileLineTopic implements Topic<KafkaMessage> {

    @Override
    public final String getName() {
        return "fileLineTopic";
    }
}
