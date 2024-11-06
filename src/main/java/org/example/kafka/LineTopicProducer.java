package org.example.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LineTopicProducer extends KafkaMessageProducer<LineDTO, FileLineTopic> {

    public LineTopicProducer(KafkaTemplate<String, LineDTO> kafkaTemplate) { // spring wstrzykuje tylko KafkaTemplate, a reszte do super samemu
        super(new FileLineTopic(), true, kafkaTemplate);
    }
}