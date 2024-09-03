package com.supreet.security.controller;

import com.supreet.security.dto.TopicProjection;
import com.supreet.security.dto.TopicsDTO;
import com.supreet.security.model.Topics;
import com.supreet.security.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
    @GetMapping("/allTopics")
    public List<Topics> getAll() {
        return topicService.getAllTopic();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
    @GetMapping("/topicWithCourse")
    public List<TopicProjection> getTopicsWithCourse() {
        return topicService.getTopicsWithCourse();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
    @GetMapping("/{uuid}")
    public Optional<TopicsDTO> getTopicByUuid(@PathVariable String uuid) {
        return topicService.getTopicByUuid(uuid);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER','USER')")
    @GetMapping
    public ResponseEntity<List<TopicsDTO>> getAllCategories() {
        List<TopicsDTO> users = topicService.getAllCategories();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public Topics createTopic(@RequestBody Topics topic) {
        return topicService.saveTopic(topic);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{uuid}")
    public Topics updateCategory(@PathVariable String uuid, @RequestBody Topics TopicsDetails) {
        return topicService.updateTopics(uuid, TopicsDetails).orElse(null);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{uuid}")
    public boolean deleteTopic(@PathVariable String uuid) {
        return topicService.deleteTopic(uuid);
    }

}
