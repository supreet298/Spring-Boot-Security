package com.supreet.security.FileManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public FileDB uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.uploadFile(file);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        FileDB fileModel = fileService.downloadFile(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileModel.getName() + "\"").contentType(MediaType.parseMediaType(fileModel.getType())).body(new ByteArrayResource(fileModel.getData()));
    }
}
