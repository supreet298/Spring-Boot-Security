package com.supreet.security.FileManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class FileManagerController {

    public static final Logger log = Logger.getLogger(FileManagerController.class.getName());
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            fileStorageService.saveFile(file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception during upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed.");
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName) throws IOException {
        try {
            var fileToDownload = fileStorageService.getDownloadFile(fileName);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").contentLength(fileToDownload.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(new FileSystemResource(fileToDownload));
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
