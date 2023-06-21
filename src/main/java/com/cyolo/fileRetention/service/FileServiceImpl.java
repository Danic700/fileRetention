package com.cyolo.fileRetention.service;

import com.cyolo.fileRetention.model.File;
import com.cyolo.fileRetention.model.Link;
import com.cyolo.fileRetention.repository.FileRepository;
import com.cyolo.fileRetention.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);


    private final FileRepository fileRepository;
    private final LinkService linkService;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public Optional<Link> upload(MultipartFile file, int retentionTime) throws IOException {

        logger.info("Acquiring file_update_lock");
        entityManager.createNativeQuery("SELECT GET_LOCK('file_update_lock', 0)").getSingleResult();
        try {
            String fileHash = Utils.calculateFileHash(file);
            Long duplicateFileId = fileRepository.findIdByFileHash(fileHash);
            if (duplicateFileId != null) {
                logger.info("Creating new link for duplicate file");
                return linkService.createLink(duplicateFileId,retentionTime);

            }
            File image = new File(file.getBytes(), file.getOriginalFilename(), fileHash);
            logger.info("Saving new file in Files table");
            File savedImage = fileRepository.save(image);
            logger.info("File saved in file table");
            return linkService.createLink(savedImage.getId(),retentionTime);

        }
        finally {
            logger.info("Releasing file_update_lock");
            entityManager.createNativeQuery("SELECT RELEASE_LOCK('file_update_lock')").getSingleResult();

        }
    }

    @Override
    public Optional<File> retrieve(Long imageId) {
        logger.info("Retrieving file : " + imageId);
        return fileRepository.findById(imageId);
    }
}
