package org.example.projekcik1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileController {
    @Autowired // ...
    private FileProcesser fileProcesser;
    @Autowired
    private FileRepository entityRepository;
    @Autowired
    private FileTextRepository textRepository;

    @PostMapping("/test")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException { // endopoint nie może rzucac błedu, poniżej przyjład jak poprawnie zrobić
        fileProcesser.newFile(file); // nazwa metody do zmiany, bardziej saveFile lub coś
        return "";
    }
    // entery !

    @PostMapping("poprawnyTest")
    public ResponseEntity<String> uploadFilePoprawnie(@RequestParam("file") MultipartFile file) {
        try {
            var nwCoToZwraca = fileProcesser.newFile(file); // metody serwisów mogą rzucac błedami, kontrolery powinny je ewentualnie tylko łapać
            return ResponseEntity.ok(nwCoToZwraca); // reponse entity to taka klasa która zwraca komunikaty http
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // reszte przerobić według przykładu na górze na resposne entity, jak nie trzeba robić try to poprostu zwrócić ResposneEntity.ok z ewentyalnym komunikatem stringowym lub bez, wtedy w definicji metody ResponseEntity<?>
    @GetMapping("/upload")
    public String uploadPage() {
        return "upload"; // Nazwa widoku lub ścieżka do pliku HTML
    }

    @GetMapping("/list")
    public List<FileClass> getFileList() {
        return entityRepository.findAll();
    }

    @GetMapping("/lines")
    public List<LineClass> getLineList() {
        return textRepository.findAll();
    }
}

