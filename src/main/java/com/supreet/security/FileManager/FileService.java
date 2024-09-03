package com.supreet.security.FileManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;


    public FileDB uploadFile(MultipartFile file) throws IOException {
        FileDB existingFile = fileRepository.findByName(file.getOriginalFilename()).orElse(new FileDB());
        existingFile.setName(file.getOriginalFilename());
        existingFile.setType(file.getContentType());
        existingFile.setData(file.getBytes());
        return fileRepository.save(existingFile);
    }

    public FileDB downloadFile(String filename) {
        return fileRepository.findByName(filename).orElseThrow(() -> new RuntimeException("File not found"));
    }
}
