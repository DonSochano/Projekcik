package org.example.projekcik1;

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
        oldFile.renameTo(newFile);

        log.info("Plik: " + fileName + " został przeniesiony do: " + newFile.getPath());

        fileProcessor.processing1(newFile);
    }

    public void moveToProcessed(String fileName) throws IOException {

        File oldFileDirectory = new File("src/main/resources/work/" + fileName);
        File newFileDirectory = new File("src/main/resources/processed/" + fileName);
        oldFileDirectory.renameTo(newFileDirectory);
        System.out.println("Plik: " + fileName + " został przeniesiony do: " + newFileDirectory.getPath());
    }
}

