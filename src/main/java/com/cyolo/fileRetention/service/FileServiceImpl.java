package com.cyolo.fileRetention.service;

import com.cyolo.fileRetention.model.Files;
import com.cyolo.fileRetention.repository.FileRepository;
import com.cyolo.fileRetention.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public Long uploadFile(MultipartFile file) throws IOException {

        String fileHash = Utils.calculateFileHash(file);
        Long duplicateFileId = fileRepository.findIdByFileHash(fileHash);
        if (duplicateFileId != null) {
            return duplicateFileId;
        } else {
            Files files = new Files(file.getBytes(), file.getOriginalFilename(), fileHash);
            Files savedFile = fileRepository.save(files);
            return savedFile.getId();

        }
    }

    @Override
    public Optional<Files> retrieveFile(Long fileId) {
        return fileRepository.findById(fileId);
    }
}
