package com.supreet.security.service;

import com.supreet.security.dto.ViewDTO;
import java.util.List;

public interface ViewService {

    ViewDTO createView(String userUuid, List<String> topicUuids);
}
