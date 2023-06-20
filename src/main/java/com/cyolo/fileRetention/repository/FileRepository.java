package com.cyolo.fileRetention.repository;

import com.cyolo.fileRetention.model.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<Files, Long> {
    //boolean existsByFileHash(String fileHash);
    @Query("SELECT f.id FROM Files f WHERE f.fileHash = ?1 AND f.links > 0")
    Long findIdByFileHash(String fileHash);


}
