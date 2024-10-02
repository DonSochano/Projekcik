package org.example.projekcik1.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class Projekcik1Application { // wyjebać wszystkie występujące polskie nazyw

    public static void main(String[] args) {
        // posegregować w pakiety wszystkie klasy: zastosować pakiety : components (serwisy, processory), repository (repa), models (klasy które nie są entity), entity (encje)
        // albo zastosowac pakiety które mówią za co komponenty odpowiadają: pakiet user w środku, userController, userService, UserRepository itd
        SpringApplication.run(Projekcik1Application.class, args);
    }

}
