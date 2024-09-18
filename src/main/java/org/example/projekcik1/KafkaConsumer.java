package org.example.projekcik1;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final FileTextRepository textRepository;
    private final FileRepository fileRepository;

    @KafkaListener(topics = "fileLineTopic", groupId = "group_id")
    public void consume(KafkaMessege message) {

        logger.info("Odebrano wiadomość: ID: {}, linenumber: {}, name: {}, lastname: {}, age: {}, salary: {}, town: {}",
                message.getId(), message.getLineNumber(), message.getName(), message.getLastName(), message.getAge(), message.getSalary(), message.getTown());
        LineClass newLine = new LineClass();
        newLine.setLineID(message.getId() + message.getLineNumber() + message.getAge());
        newLine.setFileId(message.getId());
        newLine.setLineNumber(message.getLineNumber());
        newLine.setName(message.getName());
        newLine.setLastName(message.getLastName());
        newLine.setAge(message.getAge());
        newLine.setSalary(message.getSalary());
        newLine.setTown(message.getTown());
        //tu nic nie zwraca ale tez kompozycyjnie łątwiej - ustawiamy nad enterem zapisujemy pod
        textRepository.save(newLine);
    }
}
