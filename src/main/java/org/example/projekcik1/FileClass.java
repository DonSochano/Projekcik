package org.example.projekcik1;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "files")
public class FileClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String fileName;
    LocalDate detectionTime;
    Long fileSize;

public FileClass(String fileName, LocalDate detectionTime, Long fileSize) {
    this.fileName = fileName;
    this.detectionTime = detectionTime;
    this.fileSize = fileSize;
}
    public FileClass(LocalDate detectionTime, Long fileSize) {
        this.fileName = fileName;
        this.detectionTime = detectionTime;
    }

    public FileClass() {}

}
