CREATE TABLE IF NOT EXISTS USERS (
    ID INT auto_increment PRIMARY KEY NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(35) NOT NULL,
    age INT,
    email VARCHAR(35) NOT NULL,
    password VARCHAR(60) NOT NULL,
    user_type ENUM('CUSTOMER', 'ADMIN') DEFAULT 'CUSTOMER',
    is_account_confirmed BOOLEAN
);

CREATE TABLE IF NOT EXISTS RAILWAY_LINES (
    ID INT auto_increment PRIMARY KEY NOT NULL,
    railway_line_number MEDIUMINT UNSIGNED NOT NULL,
    established DATE DEFAULT (CURRENT_DATE)
);

CREATE TABLE IF NOT EXISTS TRAINS (
    ID INT auto_increment PRIMARY KEY NOT NULL,
    train_number INT UNSIGNED NOT NULL UNIQUE,
    commissioning_date DATE DEFAULT (CURRENT_DATE),
    train_type ENUM('LOCAL_TRAIN', 'EXPRESS', 'BULLET_TRAIN') DEFAULT 'LOCAL_TRAIN',
    total_seats INT UNSIGNED NOT NULL CHECK (total_seats > 0),
    are_seats_limited BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS ROUTES (
    ID INT auto_increment PRIMARY KEY NOT NULL,
    departure_station VARCHAR(35) NOT NULL,
    arrival_station VARCHAR(35) NOT NULL,
    departure_date DATE NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    price DECIMAL(5, 2) NOT NULL,
    stops INT NOT NULL,
    changes INT NOT NULL,
    days_of_week JSON NOT NULL,
    train_stations JSON NOT NULL,
    arrival_times JSON NOT NULL,
    train_id INT NOT NULL,
    railway_line_id INT NOT NULL,
    FOREIGN KEY (railway_line_id) REFERENCES RAILWAY_LINES(ID),
    FOREIGN KEY (train_id) REFERENCES TRAINS(ID)
);

CREATE TABLE IF NOT EXISTS TICKETS (
    ID INT auto_increment PRIMARY KEY NOT NULL,
    ticket_number INT UNSIGNED NOT NULL UNIQUE,
    ticket_type ENUM('ONE_WAY', 'RETURN') DEFAULT 'ONE_WAY',
    departure_station VARCHAR(35) NOT NULL,
    arrival_station VARCHAR(35) NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    price DECIMAL(3, 2) NOT NULL,
    stops INT NOT NULL,
    changes INT NOT NULL,
    ticket_status ENUM('BOOKED', 'PENDING', 'CANCELED') DEFAULT 'PENDING',
    train_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (train_id) REFERENCES TRAINS(ID),
    FOREIGN KEY (user_id) REFERENCES USERS(ID)
);
