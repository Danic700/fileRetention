package com.cyolo.fileRetention;

import com.cyolo.fileRetention.model.File;
import com.cyolo.fileRetention.model.Link;
import com.cyolo.fileRetention.repository.FileRepository;
import com.cyolo.fileRetention.service.FileService;
import com.cyolo.fileRetention.service.FileServiceImpl;
import com.cyolo.fileRetention.service.LinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FileServiceTest {

    private FileService fileService;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private LinkService linkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileService = new FileServiceImpl(fileRepository, linkService);
    }

    @Test
    void testUpload_NewFile() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());

                when(fileRepository.findIdByFileHash(any())).thenReturn(null);

        File savedFile = new File();
        savedFile.setId(1L);
        when(fileRepository.save(any(File.class))).thenReturn(savedFile);

        Link expectedLink = new Link();
        expectedLink.setId(1L);
        expectedLink.setFileId(savedFile.getId());
        when(linkService.createLink(savedFile.getId(), 1)).thenReturn(Optional.of(expectedLink));

        Optional<Link> result = fileService.upload(multipartFile, 1);

        assertEquals(Optional.of(expectedLink), result);
    }

    @Test
    void testUpload_DuplicateFile() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());

                Long duplicateFileId = 1L;
        when(fileRepository.findIdByFileHash(any())).thenReturn(duplicateFileId);

        Link expectedLink = new Link();
        expectedLink.setId(2L);
        expectedLink.setFileId(duplicateFileId);
        when(linkService.createLink(duplicateFileId, 1)).thenReturn(Optional.of(expectedLink));

        Optional<Link> result = fileService.upload(multipartFile, 1);

        assertEquals(Optional.of(expectedLink), result);
    }

    // Add more tests for other scenarios

}
