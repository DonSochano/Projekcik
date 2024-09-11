package org.example.projekcik1;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.w3c.dom.Text;

@Service
public class KafkaConsumer {

    @Autowired
    FileTextRepository textRepository;


    @KafkaListener(topics = "fileLineTopic", groupId = "group_id")
    public void consume(KafkaMessege message) {
        System.out.println("Odebrano wiadomość: " +"ID: " +message.getId() +"linenumber: " +message.getLineNumber()+"name: " + message.getName()+"name: " +message.getLastName()+"lastname: " +message.getAge()+"Salary: " +message.getSalary()+"Town: " +message.getTown() );
        LineClass newLine = new LineClass();
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
