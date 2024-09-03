CREATE TABLE user(
    id        bigint NOT NULL AUTO_INCREMENT,
    uuid      CHAR(36) DEFAULT (UUID()) UNIQUE NOT NULL,
    username  varchar(255) NOT NULL,
    email     varchar(255) UNIQUE,
    password  varchar(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_by VARCHAR(255),
    last_modified_by VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    role    enum('ADMIN','MANAGER','USER') NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO user (id,uuid,username,email,password,is_active,created_by,last_modified_by,role) VALUES (1,UUID(),'admin','admin@gmail.com','$2a$12$xGIwhdApp9TWBipQmi2syulZXtKUkLi4qG7uEyBhoPONe8QYdV5Ce',TRUE,'admin@gmail.com','admin@gmail.com','ADMIN');





