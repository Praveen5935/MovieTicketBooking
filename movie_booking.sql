CREATE DATABASE movie_booking;

USE movie_booking;

CREATE TABLE movies (
    movie_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_name VARCHAR(100) NOT NULL,
    language VARCHAR(30) NOT NULL,
    duration INT NOT NULL
);

CREATE TABLE theaters (
    theater_id INT PRIMARY KEY AUTO_INCREMENT,
    theater_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL
);

CREATE TABLE shows (
    show_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_id INT NOT NULL,
    theater_id INT NOT NULL,
    show_time DATETIME NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL,

    FOREIGN KEY (movie_id)
        REFERENCES movies(movie_id),

    FOREIGN KEY (theater_id)
        REFERENCES theaters(theater_id)
);

CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    show_id INT NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    seats_booked INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    booking_status VARCHAR(20) DEFAULT 'CONFIRMED',

    FOREIGN KEY (show_id)
        REFERENCES shows(show_id)
);

INSERT INTO movies
(movie_name, language, duration)
VALUES
('Leo', 'Tamil', 164),
('Jailer', 'Tamil', 168),
('KGF', 'Kannada', 156),
('RRR', 'Telugu', 182);

INSERT INTO theaters
(theater_name, city)
VALUES
('PVR Cinemas', 'Chennai'),
('INOX', 'Chennai'),
('AGS Cinemas', 'Chennai'),
('PVR Forum Mall', 'Bangalore');

INSERT INTO shows
(movie_id, theater_id, show_time, total_seats, available_seats)
VALUES
(1, 1, '2026-10-05 10:00:00', 100, 100),
(1, 2, '2026-10-05 14:00:00', 80, 80),
(2, 1, '2026-10-05 18:00:00', 100, 100),
(3, 3, '2026-10-05 21:00:00', 120, 120);


