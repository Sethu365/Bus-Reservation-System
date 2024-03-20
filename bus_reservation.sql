CREATE DATABASE bus_reservation;
USE bus_reservation;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);
INSERT INTO users (username, password) VALUES ('user1', 'pass123');
INSERT INTO users (username, password) VALUES ('user2', 'pass1234');
select * from users;
