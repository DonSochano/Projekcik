package org.example.projekcik1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
public class FileController {
    @Autowired
    private FileProcesser fileProcesser;
    @Autowired
    private FileRepository entityRepository;
    @Autowired
    private FileTextRepository textRepository;

    @PostMapping("/test")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileProcesser.newFile(file);
        return "";
    }
    @GetMapping("/upload")
    public String uploadPage() {
        return "upload"; // Nazwa widoku lub ścieżka do pliku HTML
    }

    @GetMapping("/list")
    public List<FileClass> getFileList(){
      return entityRepository.findAll();
    }
    @GetMapping("/lines")
    public List<LineClass> getLineList(){
        return textRepository.findAll();
    }
}

