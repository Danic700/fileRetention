package com.cyolo.fileRetention.service;

import com.cyolo.fileRetention.model.File;
import com.cyolo.fileRetention.model.Link;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface FileService {
    Optional<Link> upload(MultipartFile image, int retentionTime) throws IOException;

    Optional<File> retrieve(Long imageId);

}
