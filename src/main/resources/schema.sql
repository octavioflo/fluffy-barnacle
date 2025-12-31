DROP TABLE IF EXISTS punishment;
DROP TABLE IF EXISTS habit;

CREATE TABLE punishment (
    id CHAR(36) PRIMARY KEY,
    type VARCHAR(255),
    details VARCHAR(255)
);

CREATE TABLE habit (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    date_created DATE,
    punishment_id CHAR(36),
    CONSTRAINT fk_punishment FOREIGN KEY (punishment_id) REFERENCES punishment(id)
);