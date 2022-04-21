BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS potholes;
DROP TABLE IF EXISTS severity;
DROP TABLE IF EXISTS progress_status;

DROP SEQUENCE IF EXISTS seq_user_id;
DROP SEQUENCE IF EXISTS pothole_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE pothole_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE progress_status (
	progress_status_id serial NOT NULL,
	progress_status_desc varchar(25),
	CONSTRAINT PK_progress_status PRIMARY KEY (progress_status_id)
);

CREATE TABLE severity (
	severity_id serial NOT NULL,
	severity_type_desc varchar(25),
	CONSTRAINT PK_severity PRIMARY KEY (severity_id)
);

CREATE TABLE potholes (
	pothole_id int DEFAULT nextval('pothole_id'::regclass) NOT NULL,
	user_id int NOT NULL,
	severity_id int,
	latitude varchar(100),
	longitude varchar(100),
	street_address varchar(500),
	progress_status_id int NOT NULL,
	reported_date date NOT NULL DEFAULT CURRENT_DATE,
	inspected_date varchar(50),
	repaired_date varchar(50),
	notes varchar(1000),
	CONSTRAINT PK_pothole PRIMARY KEY (pothole_id),
	CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT FK_severity FOREIGN KEY (severity_id) REFERENCES severity(severity_id),
	CONSTRAINT FK_progress_status FOREIGN KEY (progress_status_id) REFERENCES progress_status(progress_status_id)
);


INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO progress_status (progress_status_desc) VALUES ('Reported');
INSERT INTO progress_status (progress_status_desc) VALUES ('Inspected');
INSERT INTO progress_status (progress_status_desc) VALUES ('Repaired');
INSERT INTO progress_status (progress_status_desc) VALUES ('Does Not Require Repair');

INSERT INTO severity (severity_type_desc) VALUES ('Mild');
INSERT INTO severity (severity_type_desc) VALUES ('Moderate');
INSERT INTO severity (severity_type_desc) VALUES ('Severe');


--DUMMY DATA AND SELECT STATEMENTS FOR TESTING
INSERT INTO potholes (user_id, severity_id, latitude, longitude, street_address, progress_status_id, reported_date) 
	VALUES (1, 2, '-34.397', '150.644', '123 Main St.', 1, '4/11/2022');
INSERT INTO potholes (user_id, severity_id, latitude, longitude, street_address, progress_status_id, reported_date, inspected_date) 
	VALUES (1, 3, '-37.97', '155.44', '123 Broad St.', 2, '4/11/2022', '4/12/2022');
INSERT INTO potholes (user_id, severity_id, latitude, longitude, street_address, progress_status_id, reported_date, inspected_date, repaired_date) 
	VALUES (2, 1, '-38.9', '145.84', '123 Apple St.', 3, '4/11/2022', '4/12/2022', '4/13/2022');


SELECT * FROM users;
SELECT * FROM potholes;
SELECT * FROM progress_status;
SELECT * FROM severity;

SELECT p.pothole_id, p.user_id, p.severity_id, s.severity_type_desc, p.latitude, p.longitude, p.street_address, p.progress_status_id, ps.progress_status_desc, p.reported_date, p.inspected_date, p.repaired_date, p.notes
FROM potholes AS p
	INNER JOIN severity AS s ON p.severity_id = s.severity_id
	INNER JOIN progress_status AS ps ON p.progress_status_id = ps.progress_status_id;


--ROLLBACK TRANSACTION;
COMMIT TRANSACTION;
