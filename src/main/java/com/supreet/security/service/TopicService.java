package com.supreet.security.service;

import com.supreet.security.dto.TopicProjection;
import com.supreet.security.dto.TopicsDTO;
import com.supreet.security.model.Topics;

import java.util.List;
import java.util.Optional;

public interface TopicService {

    List<Topics> getAllTopic();

    List<TopicProjection> getTopicsWithCourse();

    Optional<TopicsDTO> getTopicByUuid(String uuid);

    List<TopicsDTO> getAllCategories();

    Topics saveTopic(Topics topic);

    Optional<Topics> updateTopics(String uuid, Topics topics);

    boolean deleteTopic(String uuid);
}
