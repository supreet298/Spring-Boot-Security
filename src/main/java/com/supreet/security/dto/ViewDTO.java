package com.supreet.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewDTO {

    private String userUuid;
    private String username;
    private String email;
    private String role;
    private List<ViewedTopicDTO> viewedTopic;
    private LocalDateTime viewedAt;

    @Data
    public static class ViewedTopicDTO {
        private String topicUuid;
        private String name;

    }
}
