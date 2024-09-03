package com.supreet.security.repository;

import com.supreet.security.dto.TopicProjection;
import com.supreet.security.model.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topics, Long> {

    @Query(value = "SELECT t.uuid, t.name, t.description, c.course " +
            "FROM topics t " +
            "JOIN categories c ON t.category_id = c.id", nativeQuery = true)
    List<TopicProjection> findTopicsWithCourse();

    Optional<Topics> findByUuid(String uuid);

}
