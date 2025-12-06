DROP TABLE IF EXISTS punishment;
DROP TABLE IF EXISTS habit;

CREATE TABLE punishment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255),
    details VARCHAR(255)
);

CREATE TABLE habit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    date_created DATE
);