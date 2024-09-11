package org.example.projekcik1;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class KafkaProducer {


    private final KafkaTemplate<String, KafkaMessege> kafkaTemplate;

    private final String topic = "fileLineTopic";

    public void sendMessage(KafkaMessege message) {
        kafkaTemplate.send(topic,message);
        System.out.println("Message sent: " + message.getId());
    }
}

