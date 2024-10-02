package org.example.components;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.entity.FileEntity;
import org.example.entity.LineEntity;
import org.example.repository.FileRepository;
import org.example.repository.FileTextRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController { // to jest git napisane w sumie, tak to plus minus wygląda na produkcji już

    private final FileProcessor fileProcessor;
    private final FileRepository entityRepository;
    private final FileTextRepository textRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFilePoprawnie(@RequestParam("file") MultipartFile file) { // tu jest nazwa metody do poprawy
        try {
            String result = fileProcessor.saveFile(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Błąd podczas zapisu pliku: " + e.getMessage()); // komunikat po angielsku
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileEntity>> getFileList() {
        List<FileEntity> files = entityRepository.findAll();

        return ResponseEntity.ok(files);
    }

    @GetMapping("/lines")
    public ResponseEntity<List<LineEntity>> getLineList() {
        List<LineEntity> lines = textRepository.findAll();

        return ResponseEntity.ok(lines);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAll() {
        textRepository.deleteAll();

        return ResponseEntity.ok().build();
    }
}

