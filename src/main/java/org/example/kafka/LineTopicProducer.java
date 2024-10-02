package org.example.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LineTopicProducer extends KafkaMessageProducer<KafkaMessage, FileLineTopic> { // zastosować strategie taka na przyszłosc , postarać sie zrozumieć :D

    public LineTopicProducer(KafkaTemplate<String, KafkaMessage> kafkaTemplate) { // spring wsztrzykuje tylko KafkaTemplate, a reszte do super samemu
        super(new FileLineTopic(), true, kafkaTemplate); // super to wywołanie konstruktora z klasy KafkaMessageProducer, ono wymaga swoich rzeczy
    }
}

