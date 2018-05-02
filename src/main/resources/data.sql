INSERT INTO TOPIC(TOPIC_NAME, TOPIC_DESCRIPTION) VALUES('Java', 'Java Programming Language');
INSERT INTO TOPIC(TOPIC_NAME, TOPIC_DESCRIPTION) VALUES('Spring', 'Spring Framework for Java Language');
INSERT INTO TOPIC(TOPIC_NAME, TOPIC_DESCRIPTION) VALUES('JavaScript', 'JavaScript Programming Language');

INSERT INTO COURSE(COURSE_NAME, COURSE_DESCRIPTION, TOPIC_TOPIC_ID) VALUES('Java 8 Lambda Expressions', 'A course on Lambda expressions in Java 8', SELECT TOPIC_ID FROM TOPIC WHERE TOPIC_NAME='Java');
INSERT INTO COURSE(COURSE_NAME, COURSE_DESCRIPTION, TOPIC_TOPIC_ID) VALUES('Spring data', 'A course on database application development using Spring', SELECT TOPIC_ID FROM TOPIC WHERE TOPIC_NAME='Spring');
INSERT INTO COURSE(COURSE_NAME, COURSE_DESCRIPTION, TOPIC_TOPIC_ID) VALUES('Closures', 'A course on closures in JavaScript', SELECT TOPIC_ID FROM TOPIC WHERE TOPIC_NAME='JavaScript');