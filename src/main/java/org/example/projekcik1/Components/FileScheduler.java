package org.example.projekcik1.Components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projekcik1.Entity.FileClass;
import org.example.projekcik1.Repository.FileRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileScheduler {

    public static final String eventMessage = "MoveFromNew";

    private final FileRepository entityRepository;
    private final EventProducer eventProducer;

    @Scheduled(fixedRate = 10000)
    public void scheduler() {
        File folder = new File("src/main/resources/new");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                FileClass newFile = new FileClass(file.getName(), LocalDate.now(), file.length());
                entityRepository.save(newFile);
                entityRepository.flush();
                log.info("Znaleziono nowy plik: " + file.getName());
                File file1 = new File("src/main/resources/new/" + newFile.getId().toString() + ".csv");
                if (!file.renameTo(file1)) {
                    log.info("Zmiana nazwy nie powiodła się");
                }
                eventProducer.produceEvent(new Event(eventMessage, file1.getName()));
            }
        }
    }
}










