-- Crear la tabla User
CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(255) PRIMARY KEY NOT NULL,
  _name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  gender VARCHAR(20) NOT NULL,
  picture VARCHAR(255) NOT NULL,
  country VARCHAR(255) NOT NULL,
  state VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL
);

-- Inserts aleatorios de objetos User
INSERT INTO users (username, _name, email, gender, picture, country, state, city)
VALUES
('user1', 'User 1', 'user1@example.com', 'MALE', 'http://example.com/picture1', 'Country 1', 'State 1', 'City 1'),
('user2', 'User 2', 'user2@example.com', 'FEMALE', 'http://example.com/picture2', 'Country 1', 'State 1', 'City 1'),
('user3', 'User 3', 'user3@example.com', 'MALE', 'http://example.com/picture3', 'Country 1', 'State 1', 'City 2'),
('user4', 'User 4', 'user4@example.com', 'FEMALE', 'http://example.com/picture4', 'Country 1', 'State 1', 'City 2'),
('user5', 'User 5', 'user5@example.com', 'FEMALE', 'http://example.com/picture5', 'Country 1', 'State 2', 'City 3'),
('user6', 'User 6', 'user6@example.com', 'MALE', 'http://example.com/picture6', 'Country 1', 'State 2', 'City 3'),
('user7', 'User 7', 'user7@example.com', 'FEMALE', 'http://example.com/picture7', 'Country 1', 'State 3', 'City 4'),
('user8', 'User 8', 'user8@example.com', 'MALE', 'http://example.com/picture8', 'Country 1', 'State 3', 'City 4'),
('user1b', 'User 1b', 'user1b@example.com', 'MALE', 'http://example.com/picture1b', 'Country 1b', 'State 1b', 'City 1'),
('user2b', 'User 2b', 'user2b@example.com', 'FEMALE', 'http://example.com/picture2b', 'Country 1b', 'State 1b', 'City 1'),
('user3b', 'User 3b', 'user3b@example.com', 'MALE', 'http://example.com/picture3b', 'Country 1b', 'State 1b', 'City 2b'),
('user4b', 'User 4b', 'user4b@example.com', 'FEMALE', 'http://example.com/picture4b', 'Country 1b', 'State 1b', 'City 2b'),
('user5b', 'User 5b', 'user5b@example.com', 'FEMALE', 'http://example.com/picture5b', 'Country 1b', 'State 2b', 'City 3b'),
('user6b', 'User 6b', 'user6b@example.com', 'MALE', 'http://example.com/picture6b', 'Country 1b', 'State 2b', 'City 3b'),
('user7b', 'User 7b', 'user7b@example.com', 'FEMALE', 'http://example.com/picture7b', 'Country 1b', 'State 3b', 'City 4b'),
('user8b', 'User 8b', 'user8b@example.com', 'MALE', 'http://example.com/picture8b', 'Country 1b', 'State 3b', 'City 4b');