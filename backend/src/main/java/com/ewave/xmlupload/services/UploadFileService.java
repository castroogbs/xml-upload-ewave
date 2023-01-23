package com.ewave.xmlupload.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

    private String uploadDir;
    
    UploadFileService(@Value("${app.path.upload-dir}") String uploadDir){
        this.uploadDir = uploadDir;
    }

    public Path uploadFile(MultipartFile uploadedFile) throws IOException {
        String originalFileName = StringUtils.cleanPath(uploadedFile.getOriginalFilename());
        
        // generating the filename
        String fileName = UUID.randomUUID() + "." + this.getFileExtension(originalFileName);

        // checks if the path exists, and if not, he create it
        Path uploadPath = Paths.get(this.uploadDir + "files");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        InputStream inputStream = uploadedFile.getInputStream();
        // copy the file into the filePath
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        
        return filePath;
    }

    public boolean deleteFile(Path filePath) throws IOException {
        return Files.deleteIfExists(filePath);
    }

    private String getFileExtension(String filename) {
        int filenameLenght = filename.length();
        return filename.substring(filenameLenght - 3);
    }
}
