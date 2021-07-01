DROP TABLE IF EXISTS Library_Demo;

CREATE TABLE Library_Demo (
  id VARCHAR(250),
  book_name VARCHAR(250) NOT NULL,
  isbn VARCHAR(250) NOT NULL,
  aisle INT ,
  author VARCHAR(250) DEFAULT NULL
);
INSERT INTO Library_Demo(id, book_name, isbn, aisle ,author)
VALUES
('123456','DevOps','202107',123,'wasinapl'),
('123457','Spring','202107',123,'wasinapl'),
('123458','NodeJS','202106',123,'wasinapl')
;

--DROP TABLE IF EXISTS billionaires;
--
--CREATE TABLE billionaires (
--  id INT AUTO_INCREMENT  PRIMARY KEY,
--  first_name VARCHAR(250) NOT NULL,
--  last_name VARCHAR(250) NOT NULL,
--  career VARCHAR(250) DEFAULT NULL
--);
--
--INSERT INTO billionaires (first_name, last_name, career) VALUES
--  ('Aliko', 'Dangote', 'Billionaire Industrialist'),
--  ('Bill', 'Gates', 'Billionaire Tech Entrepreneur'),
--  ('Folrunsho', 'Alakija', 'Billionaire Oil Magnate');