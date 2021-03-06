DROP TABLE IF EXISTS ROLE;
DROP TABLE IF EXISTS DESIGNATION;
DROP TABLE IF EXISTS PEERS;

CREATE TABLE ROLE (
	ROLE_ID INT(2) NOT NULL AUTO_INCREMENT,
	ROLE_NAME VARCHAR(20) NOT NULL,
	CONSTRAINT PK_ROLE_ID PRIMARY KEY (ROLE_ID)
) ENGINE=InnoDB;

ALTER TABLE ROLE AUTO_INCREMENT = 1;

CREATE TABLE DESIGNATION (
	DESIGNATION_ID INT(2) NOT NULL AUTO_INCREMENT,
	DESIGNATION_NAME VARCHAR(20) NOT NULL,
	CONSTRAINT PK_DESIGNATION_ID PRIMARY KEY (DESIGNATION_ID)
) ENGINE=InnoDB;

ALTER TABLE DESIGNATION AUTO_INCREMENT = 1;



CREATE TABLE PEERS (
  PEER_ID INT(4) NOT NULL AUTO_INCREMENT,
  PEER_FNAME VARCHAR(50) NOT NULL,
  PEER_LNAME VARCHAR(50) NOT NULL,
  PEER_FULL_NAME VARCHAR(101) NOT NULL,
  PEER_CTS_ID INT(10) NOT NULL,
  PEER_PSWD VARCHAR(10) NOT NULL,
  PEER_DESIG_ID INT(2) NOT NULL,
  PEER_ROLE_ID INT(2) NOT NULL,
  PEER_CREATED_BY VARCHAR(50) NOT NULL,
  PEER_CREATION_TS TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PEER_LAST_UPDATED_BY VARCHAR(50) NULL,
  PEER_LAST_UPDATED_TS TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  CONSTRAINT PK_PEER_ID PRIMARY KEY (PEER_ID),
  CONSTRAINT FK_PEER_ROLE_ID FOREIGN KEY (PEER_ROLE_ID) REFERENCES ROLE (ROLE_ID),
  CONSTRAINT FK_PEER_DESIG_ID FOREIGN KEY (PEER_DESIG_ID) REFERENCES DESIGNATION(DESIGNATION_ID)
) ENGINE=InnoDB;

ALTER TABLE PEERS AUTO_INCREMENT = 1;



INSERT INTO DESIGNATION(DESIGNATION_ID, DESIGNATION_NAME) VALUES(1, 'Programmer Analyst');
INSERT INTO DESIGNATION(DESIGNATION_ID, DESIGNATION_NAME) VALUES(2, 'Associate');
INSERT INTO DESIGNATION(DESIGNATION_ID, DESIGNATION_NAME) VALUES(3, 'Senior Associate');
INSERT INTO DESIGNATION(DESIGNATION_ID, DESIGNATION_NAME) VALUES(4, 'Manager');
INSERT INTO DESIGNATION(DESIGNATION_ID, DESIGNATION_NAME) VALUES(5, 'Senior Manager');
INSERT INTO DESIGNATION(DESIGNATION_ID, DESIGNATION_NAME) VALUES(6, 'Director');
INSERT INTO DESIGNATION(DESIGNATION_ID, DESIGNATION_NAME) VALUES(7, 'Anonymous');


INSERT INTO ROLE(ROLE_ID, ROLE_NAME) VALUES (1, 'ADMIN');
INSERT INTO ROLE(ROLE_ID, ROLE_NAME) VALUES (2, 'REVIEWER');
INSERT INTO ROLE(ROLE_ID, ROLE_NAME) VALUES (3, 'EVALUTER');

INSERT INTO PEERS (PEER_FNAME, PEER_LNAME, PEER_FULL_NAME, PEER_DESIG_ID, PEER_ROLE_ID, PEER_CREATED_BY, PEER_CREATION_TS, PEER_LAST_UPDATED_BY, PEER_LAST_UPDATED_TS, PEER_CTS_ID, PEER_PSWD) VALUES ('ANONYMOUS', 'USER', 'ANONYMOUS USER', '7', '1', 'SYSTEM', sysdate(), 'SYSTEM', sysdate(), 123456, 'Password1$');