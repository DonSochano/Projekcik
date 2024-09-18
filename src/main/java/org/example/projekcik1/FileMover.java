package org.example.projekcik1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@RequiredArgsConstructor
@Service
public class FileMover {

    private final FileProcessor fileProcessor;

    public void moveToWork(String fileName) throws IOException {

        File oldFileDirectory = new File("src/main/resources/new/" + fileName);
        File newFileDirectory = new File("src/main/resources/work/" + fileName);
        oldFileDirectory.renameTo(newFileDirectory);
        System.out.println("Plik: " + fileName + " został przeniesiony do: " + newFileDirectory.getPath());

        fileProcessor.processing1(newFileDirectory);
    }

    public void moveToProcessed(String fileName) throws IOException {

        File oldFileDirectory = new File("src/main/resources/work/" + fileName);
        File newFileDirectory = new File("src/main/resources/processed/" + fileName);
        oldFileDirectory.renameTo(newFileDirectory);
        System.out.println("Plik: " + fileName + " został przeniesiony do: " + newFileDirectory.getPath());
    }
}