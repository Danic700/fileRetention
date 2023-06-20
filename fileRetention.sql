CREATE TABLE files (
  id INT AUTO_INCREMENT PRIMARY KEY,
  file_data MEDIUMBLOB,
  file_name VARCHAR(255),
  file_hash VARCHAR(64),
  links INT DEFAULT 0,
  created_at DATETIME,
  UNIQUE KEY unique_file_hash (file_hash)
);


CREATE TABLE links (
  id INT AUTO_INCREMENT PRIMARY KEY,
  file_id INT,
  link VARCHAR(255),
  ttl INT,
  created_at DATETIME,
  FOREIGN KEY (file_id) REFERENCES files(id)
);

-- Insert the file into the 'files' table
INSERT INTO files (file_data, file_name, file_hash, created_at)
VALUES (LOAD_FILE('C:\Users\rezed\Downloads\StudyCertificateDanielCohen.jpg'), 'example.jpg', 'abcd12345', NOW());

-- Retrieve the ID of the inserted file
SET @file_id = LAST_INSERT_ID();

-- Generate a unique link
SET @link = CONCAT(@file_id, '-', UUID());

-- Insert the link into the 'links' table
INSERT INTO links (file_id, link, ttl, created_at)
VALUES (@file_id, @link, 24, NOW());

CREATE TRIGGER update_links_count AFTER INSERT ON links
FOR EACH ROW
	UPDATE files  
	SET links = links + 1
	WHERE id = NEW.file_id;

CREATE TRIGGER decrease_links_count AFTER DELETE ON links
FOR EACH ROW
	UPDATE files
	SET links = links - 1
	WHERE id = OLD.file_id;

CREATE EVENT delete_files_event
ON SCHEDULE EVERY 1 MINUTE
DO
  DELETE FROM files WHERE links = 0;
  
CREATE EVENT delete_expired_links
ON SCHEDULE EVERY 1 MINUTE -- Adjust the schedule as needed
DO
  DELETE FROM links WHERE created_at <= NOW() - INTERVAL ttl MINUTE;


SELECT * from files;
SELECT * from links;