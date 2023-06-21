package com.cyolo.fileRetention.controller;

import com.cyolo.fileRetention.model.File;
import com.cyolo.fileRetention.model.Link;
import com.cyolo.fileRetention.service.FileService;
import com.cyolo.fileRetention.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class FileController {

    private final FileService fileService;
    private final LinkService linkService;


    @Value("${shareableUrlPrefix}")
    private String shareableUrlPrefix;

    @PutMapping("/file")
    public ResponseEntity<String> uploadImage(@RequestPart  MultipartFile file,
                                             @RequestHeader(value = "Retention-Time", defaultValue = "1") int retentionTime) throws IOException {

        Optional<Link> link = fileService.upload(file, retentionTime);
        if(link.isEmpty()){
            return new ResponseEntity<>("Error in uploading file" ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(shareableUrlPrefix+ link.get().getLink() ,HttpStatus.CREATED);
    }

    @GetMapping("/{fileUrl}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileUrl) {
        Optional<Link> link = linkService.getLink(fileUrl);
        if(link.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Optional<File> file = fileService.retrieve(link.get().getFileId());
        if(file.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // Retrieve the file data from the service
        byte[] imageData = file.get().getFileData();
        Resource resource = new ByteArrayResource(imageData);

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        // Return the byte array as a ResponseEntity
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
