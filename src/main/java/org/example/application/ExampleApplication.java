package org.example.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class ExampleApplication {

    // wszystko po ANG !

    // pakiety z małej

    // logika FileController woła FileService żeby zapisac plik
    //FileService generuje event że nowy plik w new
    //EventConsumer będzie wołac FileMovera aby przeniósł plik do work
    // FileManager będzie tworzył se beany - 1 bean per 1 plik, czyli jeden wątek dla pliku który bezie go zjadała


    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

}
