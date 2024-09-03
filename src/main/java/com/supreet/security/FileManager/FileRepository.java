package com.supreet.security.FileManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileDB, Long> {

    Optional<FileDB> findByName(String filename);

}
