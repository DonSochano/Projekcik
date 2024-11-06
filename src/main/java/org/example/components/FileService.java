package org.example.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.event.Event;
import org.example.event.EventProducer;
import org.example.entity.FileEntity;
import org.example.enums.ApplicationPath;
import org.example.enums.EventType;
import org.example.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final EventProducer eventProducer;

    public String saveFile(MultipartFile file) throws IOException {

        File newFile = new File(ApplicationPath.PathNew.getPath() + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            fos.write(file.getBytes());
        }
        log.info("Find new file: " + newFile.getName()+ "path: " + newFile.getPath());
        FileEntity newFile1 = new FileEntity(file.getName(), LocalDate.now(), newFile.length());
        fileRepository.save(newFile1);
        fileRepository.flush();
        File file1 = new File(ApplicationPath.PathNew.getPath() + newFile1.getId().toString()  + ".csv");
        boolean result = newFile.renameTo(file1);
            if (!result){
                log.error("Failed to rename file from: " + newFile.getName() + " to: " + file1.getName());
            }
        eventProducer.produceEvent(new Event(EventType.MOVE_FROM_NEW_TO_WORK, file1.getName()));

        return "File: " + file1.getName() + " saved successfully!";
    }
}
