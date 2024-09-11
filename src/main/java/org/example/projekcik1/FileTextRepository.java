package org.example.projekcik1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileTextRepository extends JpaRepository<LineClass, Long> {
}
