package com.supreet.security.service;

import com.supreet.security.dto.ViewDTO;
import com.supreet.security.model.Topics;
import com.supreet.security.model.User;
import com.supreet.security.model.View;
import com.supreet.security.repository.TopicRepository;
import com.supreet.security.repository.UserRepository;
import com.supreet.security.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ViewServiceImpl implements ViewService {

    @Autowired
    private ViewRepository viewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public ViewDTO createView(String userUuid, List<String> topicUuids) {
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ViewDTO.ViewedTopicDTO> viewedTopics = new ArrayList<>();

        for (String topicUuid : topicUuids) {
            Topics topic = topicRepository.findByUuid(topicUuid)
                    .orElseThrow(() -> new RuntimeException("Topic not found"));

            View view = new View();
            view.setUser(user);
            view.setTopic(topic);
            view.setViewedAt(LocalDateTime.now());

            viewRepository.save(view);

            ViewDTO.ViewedTopicDTO viewedTopicDTO = new ViewDTO.ViewedTopicDTO();
            viewedTopicDTO.setTopicUuid(topic.getUuid());
            viewedTopicDTO.setName(topic.getDescription());

            viewedTopics.add(viewedTopicDTO);
        }

        ViewDTO viewDTO = new ViewDTO();
        viewDTO.setUserUuid(user.getUuid());
        viewDTO.setUsername(user.getUsername());
        viewDTO.setEmail(user.getEmail());
        viewDTO.setRole(user.getRole().name());
        viewDTO.setViewedTopic(viewedTopics);
        viewDTO.setViewedAt(LocalDateTime.now());

        return viewDTO;
    }
}
