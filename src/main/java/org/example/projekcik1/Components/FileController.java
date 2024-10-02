package org.example.projekcik1.Components;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.projekcik1.Entity.FileClass;
import org.example.projekcik1.Entity.LineClass;
import org.example.projekcik1.Repository.FileRepository;
import org.example.projekcik1.Repository.FileTextRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;





@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileProcessor fileProcessor;
    private final FileRepository entityRepository;
    private final FileTextRepository textRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFilePoprawnie(@RequestParam("file") MultipartFile file) {
        try {
            String result = fileProcessor.saveFile(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Błąd podczas zapisu pliku: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileClass>> getFileList() {
        List<FileClass> files = entityRepository.findAll();

        return ResponseEntity.ok(files);
    }

    @GetMapping("/lines")
    public ResponseEntity<List<LineClass>> getLineList() {
        List<LineClass> lines = textRepository.findAll();

        return ResponseEntity.ok(lines);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAll() {
        textRepository.deleteAll();

        return ResponseEntity.ok().build();
    }
}

