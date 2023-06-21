CREATE TABLE file (
  id INT AUTO_INCREMENT PRIMARY KEY,
  file_data MEDIUMBLOB,
  file_name VARCHAR(255),
  file_hash VARCHAR(64),
  links INT DEFAULT null,
  created_at DATETIME,
  UNIQUE KEY unique_file_hash (file_hash)
);


CREATE TABLE link (
  id INT AUTO_INCREMENT PRIMARY KEY,
  file_id INT,
  link VARCHAR(255),
  ttl INT,
  created_at DATETIME,
  FOREIGN KEY (file_id) REFERENCES file(id)
);


CREATE TRIGGER update_links_count AFTER INSERT ON link
FOR EACH ROW
	UPDATE file  
	SET links = COALESCE(links, 0) + 1
	WHERE id = NEW.file_id;


CREATE TRIGGER decrease_links_count AFTER DELETE ON link
FOR EACH ROW
	UPDATE file
	SET links = links - 1
	WHERE id = OLD.file_id;
    

delimiter |


CREATE EVENT delete_files_event
ON SCHEDULE EVERY 1 MINUTE
DO
BEGIN   -- Check if lock is taken (by uploadFile)
	IF IS_FREE_LOCK('file_update_lock') THEN
		-- Perform the deletion logic
		DELETE FROM file WHERE links = 0;
          END IF;
     END |
delimiter ;

    
CREATE EVENT delete_expired_links
ON SCHEDULE EVERY 1 MINUTE -- Adjust the schedule as needed
DO
  DELETE FROM link WHERE created_at <= NOW() - INTERVAL ttl MINUTE;
