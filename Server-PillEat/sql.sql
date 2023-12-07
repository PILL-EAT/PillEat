use pilleatDB;
-----------------------------------------------------------

CREATE TABLE taker (
  taker_id  int not null auto_increment,
  taker_email varchar(50) not null,
  taker_password varchar(50) not null,
  taker_name varchar(50) not null,
  taker_birth varchar(50) not null,
  taker_number varchar(50) not null,
  taker_date varchar(50) not null,
 primary key(taker_id)
);

INSERT INTO taker
(taker_email, taker_password, taker_name, taker_birth, taker_number, taker_date)
VALUES 
('sus32578', '1234', 'Sang Ho', '1990-01-01', '1234567890', '2023-01-01');

select * from taker;
--------------------------------------------------------------------------------------
CREATE TABLE protector (
  protector_id  int auto_increment,
  protector_email varchar(50) not null,
  protector_password varchar(50) not null,
  protector_name varchar(50) not null,
  protector_birth varchar(50) not null,
  protector_number varchar(50) not null,
  protector_date varchar(50) not null,
  taker_id  int not null,
 primary key(protector_id),
 foreign key(taker_id) references taker(taker_id));
 
---------------------------------------------------------------------------------

CREATE TABLE pill_information (
  pill_id  int auto_increment,
  pill_name  VARCHAR(20) not null,
  pill_instruction varchar(1000),
  primary key(pill_id)
);

INSERT INTO pill_information (pill_name, pill_instruction) VALUES
('Aspirin', 'Take one tablet with water after meals.'),
('Ibuprofen', 'Take two tablets every 8 hours as needed.'),
('Acetaminophen', 'Take one tablet every 6 hours. Do not exceed recommended dosage.');
----------------------------------------------------------------------------------

CREATE TABLE pill_alert (
  pill_alert_id INT AUTO_INCREMENT,
  taker_id INT NOT NULL,
  pill_name VARCHAR(50) NOT NULL,
  pill_kind VARCHAR(50) NOT NULL,
  alert_time VARCHAR(50) NOT NULL,
  alert_day VARCHAR(50) NOT NULL,
  alert_memo VARCHAR(100) NOT NULL,
  PRIMARY KEY (pill_alert_id),
  FOREIGN KEY (taker_id) REFERENCES taker(taker_id)
);

INSERT INTO Pill_Alert (taker_id, pill_name, pill_kind, alert_time, alert_day, alert_memo) VALUES
(1, '아스피린', '진통제', '08:00', '월요일', '아침 식사와 함께 복용'),
(1, '이부프로펜', '소염제', '12:00', '수요일', '점심 식사와 함께 복용'),
(1, '아세트아미노펜', '진통제', '20:00', '금요일', '취침 전 복용');

select * from pill_alert


--------------------------------------------------------------------
