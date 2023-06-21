package com.cyolo.fileRetention.repository;

import com.cyolo.fileRetention.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    //boolean existsByFileHash(String fileHash);
    @Query("SELECT f.id FROM File f WHERE f.fileHash = ?1 AND f.links > 0")
    Long findIdByFileHash(String fileHash);


}
