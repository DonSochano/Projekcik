package org.example.repository;

import org.example.enums.FileStatus;
import org.example.entity.FileProcessorConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileProcessingConfigRepository extends JpaRepository<FileProcessorConfig,Long> {

    Optional <FileProcessorConfig> findByName(String fileName);
    List<FileProcessorConfig> findAllByStatus (FileStatus fileStatus);
}
