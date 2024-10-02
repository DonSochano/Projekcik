package org.example.repository;

import org.example.entity.LineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileTextRepository extends JpaRepository<LineEntity, Long> { // LineRepository lepiej nazwać
}
