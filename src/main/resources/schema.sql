CREATE TABLE TOPIC(
  TOPIC_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
  TOPIC_NAME VARCHAR(100) NOT NULL,
  TOPIC_DESCRIPTION VARCHAR(100) NOT NULL,
);

CREATE TABLE COURSE(
  COURSE_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
  COURSE_NAME VARCHAR(100) NOT NULL,
  COURSE_DESCRIPTION VARCHAR(100) NOT NULL,
  TOPIC_TOPIC_ID BIGINT
);

ALTER TABLE COURSE ADD FOREIGN KEY (TOPIC_TOPIC_ID) references TOPIC(TOPIC_ID);