package com.supreet.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicsDTO {

    private String uuid;
    private String name;
    private String description;
}
