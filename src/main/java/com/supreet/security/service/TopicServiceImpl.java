package com.supreet.security.service;

import com.supreet.security.dto.TopicProjection;
import com.supreet.security.dto.TopicsDTO;
import com.supreet.security.model.Topics;
import com.supreet.security.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Topics> getAllTopic() {
        return topicRepository.findAll();
    }

    @Override
    public Topics saveTopic(Topics topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Optional<TopicsDTO> getTopicByUuid(String uuid) {
        return topicRepository.findByUuid(uuid)
                .map(this::convertToDTO);
    }

    @Override
    public List<TopicProjection> getTopicsWithCourse() {
        return topicRepository.findTopicsWithCourse();
    }

    @Override
    public Optional<Topics> updateTopics(String uuid, Topics topics) {
        return topicRepository.findByUuid(uuid)
                .map(existingTopics -> {
                    // Update the existing category with values from categoryDetails
                    existingTopics.setName(topics.getName());
                    existingTopics.setDescription(topics.getDescription());
                    // Save and return the updated category
                    return topicRepository.save(existingTopics);
                });
    }

    @Override
    public boolean deleteTopic(String uuid) {
        Optional<Topics> existingCategory = topicRepository.findByUuid(uuid);
        if (existingCategory.isPresent()) {
            topicRepository.delete(existingCategory.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<TopicsDTO> getAllCategories() {
        List<Topics> categories = topicRepository.findAll();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TopicsDTO convertToDTO(Topics topics) {
        TopicsDTO dto = new TopicsDTO();
        dto.setUuid(topics.getUuid());
        dto.setName(topics.getName());
        dto.setDescription(topics.getDescription());
        return dto;
    }
}