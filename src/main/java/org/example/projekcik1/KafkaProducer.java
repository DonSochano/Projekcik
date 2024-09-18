package org.example.projekcik1;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, KafkaMessege> kafkaTemplate;

    public void sendLineInfoMessage(KafkaMessege message) {
        String topic = "fileLineTopic";
        kafkaTemplate.send(topic, message);
        System.out.println("Message sent: " + message.getId());
    }


}

