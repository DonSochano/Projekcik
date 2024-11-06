package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.FileStatus;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FileProcessorConfig {

    @Id
    private String id;
    private String name;
    private FileStatus status;

    public FileProcessorConfig(String name, FileStatus status) {
        this.name = name;
        this.status = status;
        this.id = UUID.randomUUID().toString();
    }

}
