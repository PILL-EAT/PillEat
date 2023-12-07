use pilleatDB;

CREATE TABLE user (
  user_id int NOT NULL AUTO_INCREMENT,
  user_email varchar(50) NOT NULL unique,
  user_password varchar(50) NOT NULL,
  user_name varchar(50) NOT NULL,
  user_birth varchar(50) NOT NULL,
  user_number varchar(50) NOT NULL,
  user_date varchar(50) NOT NULL,
  taker_id int,
  user_type varchar(10) NOT NULL,
  PRIMARY KEY (user_id),
  FOREIGN KEY (taker_id) REFERENCES user(user_id) on delete set NULL
);
INSERT INTO user (user_email,user_password,user_name,user_birth,user_number,user_date,taker_id,user_type) 
VALUES ('sus32578@naver.com','1234','이상호','1990-01-01','123456789','2023-12-06', NULL, 'taker');

INSERT INTO user (user_email,user_password,user_name,user_birth,user_number,user_date,taker_id,user_type) 
VALUES ('sus325789@naver.com','1234','보호자','1990-01-01','123456789','2023-12-06', 1, 'protector');

INSERT INTO user (user_email,user_password,user_name,user_birth,user_number,user_date,taker_id,user_type) 
VALUES ('pilleat','1234','관리자','0','0','0', null, 'manager');

select * from user;
-----------------------------------------------------------

CREATE TABLE pill_information (
  pill_id  int auto_increment,
  pill_name  VARCHAR(20) not null,
  pill_use varchar(1000),
  primary key(pill_id)
);

INSERT INTO pill_information (pill_name, pill_use) VALUES
('Aspirin', 'Take one tablet with water after meals.'),
('Ibuprofen', 'Take two tablets every 8 hours as needed.'),
('Acetaminophen', 'Take one tablet every 6 hours. Do not exceed recommended dosage.');
----------------------------------------------------------------------------------

CREATE TABLE pill_alert (
  pill_alert_id INT AUTO_INCREMENT,
  taker_id INT NOT NULL,
  pill_name VARCHAR(50) NOT NULL,
  pill_kind VARCHAR(50) NOT NULL,
  pill_volumn VARCHAR(20) NOT NULL,
  alert_time VARCHAR(50) NOT NULL,
  alert_day VARCHAR(50) NOT NULL,
  
  PRIMARY KEY (pill_alert_id),
  FOREIGN KEY (taker_id) REFERENCES taker(taker_id)
);

INSERT INTO pill_alert (taker_id, pill_name, pill_kind, pill_volumn, alert_time, alert_day) VALUES
(1, '아스피린', '진통제','2.5mg', '08:00', '월요일'),
(1, '이부프로펜', '소염제','1알', '12:00', '수요일'),
(1, '아세트아미노펜', '진통제','2알', '20:00', '금요일');

select * from pill_alert


--------------------------------------------------------------------
