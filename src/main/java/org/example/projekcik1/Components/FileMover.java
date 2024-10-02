package org.example.projekcik1.Components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileMover {

    private final FileProcessor fileProcessor;

    public void moveToWork(String fileName) throws IOException {

        File oldFile = new File("src/main/resources/new/" + fileName);
        File newFile = new File("src/main/resources/work/" + fileName);
        if(!oldFile.renameTo(newFile)){
            log.error("Could not move file to work");
        }

        log.info("Plik: " + fileName + " został przeniesiony do: " + newFile.getPath());

        fileProcessor.processing(newFile);
    }

    public void moveToProcessed(String fileName) throws IOException {

        File oldFile = new File("src/main/resources/work/" + fileName);
        File newFile = new File("src/main/resources/processed/" + fileName);
        if(!oldFile.renameTo(newFile)){
            log.error("Could not move file to processed");
        }

        log.info("Plik: " + fileName + " został przeniesiony do: " + newFile.getPath());
    }

    public void moveFromToError(String fileName, String directory) throws IOException {

        File oldFileDirectory = new File("src/main/resources/" + directory + "/" + fileName);
        File newFileDirectory = new File("src/main/resources/error/" + fileName);
        oldFileDirectory.renameTo(newFileDirectory);

        log.info("Plik: " + fileName + " został przeniesiony do: " + newFileDirectory.getPath());
    }
}

