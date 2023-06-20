package com.cyolo.fileRetention.service;

import com.cyolo.fileRetention.model.Files;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface FileService {
    Long uploadFile(MultipartFile file) throws IOException;

    Optional<Files> retrieveFile(Long fileId);

}
