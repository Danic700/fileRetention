package com.cyolo.fileRetention.repository;

import com.cyolo.fileRetention.model.Links;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Links, Long> {

    Links findByLink(String link);

}
