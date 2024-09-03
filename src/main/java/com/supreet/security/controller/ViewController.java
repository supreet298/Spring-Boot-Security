package com.supreet.security.controller;

import com.supreet.security.dto.ViewDTO;
import com.supreet.security.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/views")
public class ViewController {

    @Autowired
    private ViewService viewService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
    @GetMapping("/{userUuid}")
    public ResponseEntity<ViewDTO> recordView(@PathVariable String userUuid, @RequestParam List<String> topicUuids) {
        ViewDTO viewDTO = viewService.createView(userUuid, topicUuids);
        return ResponseEntity.ok(viewDTO);
    }
}
