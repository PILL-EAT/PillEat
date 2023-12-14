use db23322;

CREATE TABLE user (
  user_id int NOT NULL AUTO_INCREMENT,
  user_email varchar(50) NOT NULL unique,
  user_password varchar(50) NOT NULL,
  user_name varchar(50) NOT NULL,
  user_birth varchar(50) NOT NULL,
  user_number varchar(50) NOT NULL,
  user_date varchar(50) NOT NULL,
  protector_id int,
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
----------------------------------------------------------------------------------

CREATE TABLE pill_alert (
  pill_alert_id INT AUTO_INCREMENT,
  taker_id INT,
  pill_name VARCHAR(50) NOT NULL,
  pill_kind VARCHAR(50) NOT NULL,
  alert_time VARCHAR(50) NOT NULL,
  alert_day VARCHAR(50) NOT NULL,
  iotYN BOOLEAN not null default false
  
  PRIMARY KEY (pill_alert_id),
  FOREIGN KEY (taker_id) REFERENCES user(user_id) on delete set NULL
);

INSERT INTO pill_alert (taker_id, pill_name, pill_kind, alert_time, alert_day) VALUES
(1, '아스피린', '진통제', '08:00', '1010101');


select * from pill_alert


--------------------------------------------------------------------

CREATE TABLE pill_history (
  pill_history_id INT AUTO_INCREMENT,
  date VARCHAR(20) NOT NULL,
  pill_alert_id INT,
  is_taken BOOLEAN NOT NULL,
  
  PRIMARY KEY (pill_history_id),
  FOREIGN KEY (pill_alert_id) REFERENCES pill_alert(pill_alert_id) on delete set NULL
);

INSERT INTO pill_history (date, pill_alert_id, is_taken) 
VALUES 
  ('2023-12-12', 10, 0),
  ('2023-12-12', 11, 1),
  ('2023-12-12', 12, 0);
