package org.example.kafka;


import lombok.extern.slf4j.Slf4j;
import org.example.repository.LineRepository;
import org.example.entity.LineEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LineDTOConsumer {

    private final LineRepository textRepository;

    public LineDTOConsumer(LineRepository textRepository) {
        this.textRepository = textRepository;
    }


    @KafkaListener(topics = "#{fileLineTopic.getName()}", groupId = "group_id")
    public void consume(List<LineDTO> messages, Acknowledgment comit) {
        List<LineEntity> entities = new ArrayList<>();
        for (LineDTO message : messages) {
        setLineRepository(message, entities);

        }
        textRepository.saveAll(entities);
        comit.acknowledge();
    }

    public void setLineRepository(LineDTO message, List<LineEntity> entities) {
        LineEntity newLine = new LineEntity();

        newLine.setLineID(message.getId() + message.getLineNumber() + message.getAge() + Math.round(message.getSalary()));
        newLine.setFileId(message.getId());
        newLine.setLineNumber(message.getLineNumber());
        newLine.setName(message.getName());
        newLine.setLastName(message.getLastName());
        newLine.setAge(message.getAge());
        newLine.setSalary(message.getSalary());
        newLine.setTown(message.getTown());

        entities.add(newLine);
    }

}

