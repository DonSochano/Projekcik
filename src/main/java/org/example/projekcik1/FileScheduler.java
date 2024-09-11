package org.example.projekcik1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.File;
import java.time.LocalDate;

@Component
public class FileScheduler {
    @Autowired
    FileRepository entityRepository;
    @Autowired
    EventProducer eventProducer;
    public static final String eventMessage = "MoveFromNew";


    @Scheduled(fixedRate = 10000)
    public void Scheduler(){
        File folder = new File("src/main/resources/new");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                    FileClass newFile = new FileClass(file.getName(), LocalDate.now(), file.length());
                    entityRepository.save(newFile);
                    entityRepository.flush();
                    System.out.println("Znaleziono nowy plik: " + file.getName());
                    File file1 = new File("src/main/resources/new/" + newFile.getId().toString()+".csv");
                    file.renameTo(file1);
                    eventProducer.produceEvent(new Event(eventMessage, file1.getName()));
            }
                }
                }
            }










