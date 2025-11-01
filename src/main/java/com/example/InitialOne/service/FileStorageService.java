package com.example.InitialOne.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {
	@Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file, String subDir) {
        try {
            Path directory = Paths.get(uploadDir, subDir);
            Files.createDirectories(directory);
            Path filePath = directory.resolve(file.getOriginalFilename());
            file.transferTo(filePath.toFile());
            return "/uploads/" + subDir + "/" + file.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }
}