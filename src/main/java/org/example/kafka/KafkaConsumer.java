package org.example.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repository.FileTextRepository;
import org.example.entity.LineEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor // usunąć
public class KafkaConsumer { // LineDTOConsumer

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class); // nie potrzebne jak masz SLF4J
    private final FileTextRepository textRepository;

    //dodać rećznie konstruktor jak bedzie niżej metoda batchowo, w konstruktorze dodać jakaś zmienną z adnotacją @Value która bedzie wyciagana automatycznie przez springa z application.properties
    //ta zmienna będzie miała batchSize - czyli ile naraz zczytac rekordów z kafki

    @KafkaListener(topics = "fileLineTopic", groupId = "group_id") // wrzucić tutaj FileLineTopic().getName() i jak w sumie ta klasa message bedzie sie nazywac Line dto to tez przystosowac nazwe topica
    public void consume(KafkaMessage message) {

        // w zasadzie chyba możesz tworzyć LineEnttity chyba bez id, wysyłać na topic, tutaj odbierać, dorzucac id i zapisywac na bazie danych
        // ale tak też może być - jest nawet bardziej poprawnie tylko nie nazwa KafkaMessage tylko zmienic nazwe na LineDTO

        logger.info("Odebrano wiadomość: ID: {}, linenumber: {}, name: {}, lastname: {}, age: {}, salary: {}, town: {}", // przejść na angielskie, zamiast tutaj tworzyć jak ma wyglądać string z tej wiadomości to w KafkaMessage zoverridowac w odpowieni sposób metode toString, a nawet nie trzeba by jej overridować bo ona powinna plus minus cos takieog wyswietlić ale możesz dla zasady i tutaj poprostu zawołać toString
                message.getId(), message.getLineNumber(), message.getName(), message.getLastName(), message.getAge(), message.getSalary(), message.getTown());

        LineEntity newLine = new LineEntity();

        Long l = Math.round(message.getSalary()); // po co ? tzn może potrzebne ale nw :D - może zmienić struktóre tabeli i zapisywac tam zamiast longa to właśnie doubla ? tylko nw jak sa liczby zmiennoprzecinkowe przechowywane w postgresie

        newLine.setLineID(message.getId() + message.getLineNumber() + message.getAge()+ Math.round(message.getSalary()));

        log.info("tutaj mamona w longu: " + Math.round(message.getSalary()));
        log.info("tutaj w sumie wszystko dodane: "+ message.getId() + message.getLineNumber() + message.getAge()+ Math.round(message.getSalary()));
        newLine.setFileId(message.getId());
        newLine.setLineNumber(message.getLineNumber());
        newLine.setName(message.getName());
        newLine.setLastName(message.getLastName());
        newLine.setAge(message.getAge());
        newLine.setSalary(message.getSalary());
        newLine.setTown(message.getTown());

        textRepository.save(newLine); // zapytac czatu jak zczytywać batchowo z kafki, czyli np 10 reordów naraz i tez dodać do repo metode do zapisu 10 reordów naraz

        // i na sam koniec dodać komitowanie do kawki, czyli zatwierdzenie zczytanych reordów jako zczytane i przetoworzone, jak nie będziesz wiedział jak zczytac batchow (czyli większym partem a nie pojedynczo)
    }
}
