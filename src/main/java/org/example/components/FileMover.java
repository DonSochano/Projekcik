package org.example.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileMover { // przerobić to na intefejs z metodą moveFile(ApplicationPath basePath, ApplicationPath destinationPath, String fileName) - ApplicationPath masz przykładowe stworzone

    //

    // to stąd do wyjebania, o przeniesionym pliku jeżeli sie uda przenieść implementacja tego interfejsu (jak przerobisz ) to co będzie ten plik czytac będzie tez nałuchiwac na folder schedulerowo czy tam sie nic nie pojawiło
    private final FileProcessor fileProcessor;

// do przenoszenia skorzystać z java.nio.channels tak aby jak plik bedzie duży była na nim tworozna blokada
// - chat gpt - jak przenosić plik w javie pomiedzy folderami aby była na nim stworzona blokada dopuki plik nie zostanie całkowicie przeneisiony
// - o mechaniźmie bardziej bezpiecznego przenoszenia pliku beziemy myslec potem bo pomyśl że plik ma 100 gb a aplikacja zgasła jak przeniosło się 50 gb
// FileMover ma sprawdzić czy w folderze work już nie istnieje taki plik o takiej nazwie, jak tak to ma przeniesc to do duplikatów

    //zostawic jedna metode tak jak opisałem i przerobić to na interfejs
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

        log.info("Plik: " + fileName + " został przeniesiony do: " + newFile.getPath()); // tej metody tu nie bedzie ale komunikaty po angielsku
    }

    public void moveFromToError(String fileName, String directory) throws IOException {

        File oldFileDirectory = new File("src/main/resources/" + directory + "/" + fileName);
        File newFileDirectory = new File("src/main/resources/error/" + fileName);
        oldFileDirectory.renameTo(newFileDirectory);

        log.info("Plik: " + fileName + " został przeniesiony do: " + newFileDirectory.getPath());
    }
}

