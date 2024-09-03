package com.supreet.security.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"uuid", "name", "description", "course"})
public interface TopicProjection {

    String getUuid();

    String getName();

    String getDescription();

    String getCourse();
}
