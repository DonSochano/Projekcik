package org.example.projekcik1.Kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public void sendLineInfoMessage(KafkaMessage message) {
        kafkaTemplate.send("fileLineTopic", message);

        log.info("Message sent: {}", message.getId());
    }


}

