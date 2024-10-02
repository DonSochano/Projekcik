package org.example.projekcik1.Kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projekcik1.Repository.FileTextRepository;
import org.example.projekcik1.Entity.LineClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final FileTextRepository textRepository;


    @KafkaListener(topics = "fileLineTopic", groupId = "group_id")
    public void consume(KafkaMessage message) {

        logger.info("Odebrano wiadomość: ID: {}, linenumber: {}, name: {}, lastname: {}, age: {}, salary: {}, town: {}",
                message.getId(), message.getLineNumber(), message.getName(), message.getLastName(), message.getAge(), message.getSalary(), message.getTown());

        LineClass newLine = new LineClass();

        Long l = Math.round(message.getSalary());

        newLine.setLineID(message.getId() + message.getLineNumber() + message.getAge()+ Math.round(message.getSalary()));

        log.info("tutaj mamona w longu: " + Math.round(message.getSalary()));
        log.info("tutaj w sumie wszystko dodane: "+ message.getId() + message.getLineNumber() + message.getAge()+ Math.round(message.getSalary()));
        newLine.setFileId(message.getId());
        newLine.setLineNumber(message.getLineNumber());
        newLine.setName(message.getName());
        newLine.setLastName(message.getLastName());
        newLine.setAge(message.getAge());
        newLine.setSalary(message.getSalary());
        newLine.setTown(message.getTown());

        textRepository.save(newLine);
    }
}
