package org.example.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.FileProcessorConfig;
import org.example.enums.FileStatus;
import org.example.event.EventProducer;
import org.example.kafka.LineTopicProducer;
import org.example.enums.ApplicationPath;
import org.example.repository.FileProcessingConfigRepository;
import org.example.repository.FileRepository;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileManager {

    final private EventProducer eventProducer;
    final private FileRepository fileRepository;
    final private LineTopicProducer lineTopicProducer;
    final private GenericApplicationContext applicationContext;
    final private FileProcessingConfigRepository processingConfigRepository;


    @Scheduled(fixedRate = 5000)
    public void listFiles() {

        File folder = new File(ApplicationPath.PathWork.getPath());
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                Optional<FileProcessorConfig> configOptional = processingConfigRepository.findByName(file.getName());
                boolean l = configOptional.isEmpty();
                if (configOptional.isEmpty()) {
                    startNewThread(file);
                }
            }
            listThreads();
        }
    }

    public FileProcessorConfig createConfig(File file) {
        return new FileProcessorConfig(file.getName(), FileStatus.NEW);
    }

    public void startNewThread(File file) {
        log.info("Creating file processor for file:  " + file.getName());
        FileProcessorConfig config = createConfig(file);
        processingConfigRepository.save(config);
        FileProcessor fileProcessor = new FileProcessor(config,fileRepository, eventProducer, lineTopicProducer, processingConfigRepository);
        applicationContext.registerBean(config.getId(), FileProcessor.class, () -> fileProcessor);
        Thread processorThread = new Thread(fileProcessor);

        processorThread.start();
    }

    public void listThreads() {

        List<FileProcessorConfig> finishedConfigs = processingConfigRepository.findAllByStatus(FileStatus.FINISHED);
        for (FileProcessorConfig config : finishedConfigs) {
            if (applicationContext.containsBean(config.getId())) {
                applicationContext.removeBeanDefinition(config.getId());
                log.info("FileProcessor for: " + config.getName() + " removed after finish");
            }
        }
    }
}