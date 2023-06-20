package com.cyolo.fileRetention.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "links")
@Data
public class Links {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "link")
    private String link;

    @Column(name = "ttl")
    private int ttl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors, getters, and setters

    public Links() {
    }

    public Links(Long fileId, String link, int ttl) {
        this.fileId = fileId;
        this.link = link;
        this.ttl = ttl;
        this.createdAt = LocalDateTime.now();
    }

    // Getter and Setter methods

    // ... (getter and setter methods for other fields)
}
