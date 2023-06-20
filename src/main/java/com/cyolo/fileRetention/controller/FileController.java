package com.cyolo.fileRetention.controller;

import com.cyolo.fileRetention.model.Files;
import com.cyolo.fileRetention.service.FileService;
import com.cyolo.fileRetention.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.InetAddress;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class FileController {

    private final FileService fileService;
    private final LinkService linkService;


    @PutMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestPart  MultipartFile file,
                                             @RequestHeader(value = "Retention-Time", defaultValue = "60") int retentionTime) throws IOException {

        Long fileId = fileService.uploadFile(file);
        String fileUrl = linkService.createLink(fileId, retentionTime);
        String localHost = InetAddress.getLocalHost().getHostAddress();
        return new ResponseEntity<>(localHost+ '/' + fileUrl,HttpStatus.CREATED);
    }

    @GetMapping("/{fileUrl}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileUrl) {
        Long fileId = linkService.getLink(fileUrl);
        Optional<Files> file =  fileService.retrieveFile(fileId);
        if(file.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // Retrieve the file data as a byte array from the repository or service
        byte[] imageData = file.get().getFileData();// Fetch the file data by fileId
        Resource resource = new ByteArrayResource(imageData);

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        // Return the byte array as a ResponseEntity
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
