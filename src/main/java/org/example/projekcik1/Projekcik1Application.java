package org.example.projekcik1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class Projekcik1Application { // wyjebać wszystkie występujące polskie nazyw

    public static void main(String[] args) {
        // w każdej klasie zastosować : 1. ctrl+a 2.ctrl+alt+o 3. ctrl+alt+l !!!!
        // posegregować w pakiety wszystkie klasy: zastosować pakiety : components (serwisy, processory), repository (repa), models (klasy które nie są entity), entity (encje)
        // albo zastosowac pakiety które mówią za co komponenty odpowiadają: pakiet user w środku, userController, userService, UserRepository itd
        SpringApplication.run(Projekcik1Application.class, args);
    }

}
