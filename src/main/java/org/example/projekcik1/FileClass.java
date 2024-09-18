package org.example.projekcik1;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "files")
public class FileClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private LocalDate detectionTime;
    private Long fileSize;
    private Integer linesAmount;

    public FileClass(String fileName, LocalDate detectionTime, Long fileSize) {
        this.fileName = fileName;
        this.detectionTime = detectionTime;
        this.fileSize = fileSize;
    }
}
