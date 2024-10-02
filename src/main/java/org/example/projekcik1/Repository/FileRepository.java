package org.example.projekcik1.Repository;

import org.example.projekcik1.Entity.FileClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileClass, Long> {
}
