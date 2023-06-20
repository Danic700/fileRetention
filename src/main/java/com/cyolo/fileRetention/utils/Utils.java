package com.cyolo.fileRetention.utils;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static String calculateFileHash(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            // Calculate the MD5 hash of the file
            String fileHash = DigestUtils.md5DigestAsHex(inputStream);
            return fileHash;
        }
    }
}
