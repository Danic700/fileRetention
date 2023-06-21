package com.cyolo.fileRetention.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "file")
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_data", columnDefinition = "MEDIUMBLOB")
    private byte[] fileData;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_hash", unique = true)
    private String fileHash;

    @Column(name = "links")
    private Integer links;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    // Constructors, getters, and setters

    public File() {
    }

    public File(byte[] fileData, String fileName, String fileHash) {
        this.fileData = fileData;
        this.fileName = fileName;
        this.fileHash = fileHash;
        this.links = null;
        this.createdAt = LocalDateTime.now();
    }

    // Getter and Setter methods

    // ... (getter and setter methods for other fields)


}
