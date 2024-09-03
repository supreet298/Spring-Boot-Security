CREATE TABLE view (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    topic_id BIGINT NOT NULL,
    viewed_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (topic_id) REFERENCES topics(id)
);