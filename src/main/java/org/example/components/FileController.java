package org.example.components;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.entity.FileEntity;
import org.example.entity.LineEntity;
import org.example.repository.FileProcessingConfigRepository;
import org.example.entity.FileProcessorConfig;
import org.example.repository.FileRepository;
import org.example.repository.LineRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FileRepository fileRepository;
    private final LineRepository lineRepository;
    private final FileProcessingConfigRepository configRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String result = fileService.saveFile(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Błąd podczas zapisu pliku: " + e.getMessage()); // komunikat po angielsku
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileEntity>> getFileList() {
        List<FileEntity> files = fileRepository.findAll();

        return ResponseEntity.ok(files);
    }

    @GetMapping("/lines")
    public ResponseEntity<List<LineEntity>> getLineList() {
        List<LineEntity> lines = lineRepository.findAll();

        return ResponseEntity.ok(lines);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        lineRepository.deleteAll();
        fileRepository.deleteAll();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/configList")
    public ResponseEntity<List<FileProcessorConfig>> getConfigList() {
        List<FileProcessorConfig> files = configRepository.findAll();

        return ResponseEntity.ok(files);
    }
}