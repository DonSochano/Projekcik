package org.example.projekcik1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileClass, Long> {
     FileClass fileName(String fileName); // wyjebać public

}
