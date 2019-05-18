USE paperreviews; 

INSERT INTO Author(EmailAddress, firstName, lastName) 
VALUES 
("java@sql.com", "java_first", "java_last"),
("java2@sql.com", "java2_first", "java2_last"),
("java3@sql.com", "java3_first", "java3_last");

INSERT INTO Topic(topicName)
VALUES("CS"),("MATH"), ("BIO");

INSERT INTO Reviewer(EmailAddress, firstName, lastName, 
authorFeedBack, commiteeFeedBack, phoneNum, affiliation)
VALUES
("reviewer1@mit.edu", "r1_first", "r1_last", 'authorFeedBack1', "commiteeFeedBack1", "1111", "aff1"),
("reviewer2@stanford.edu", "r2_first", "r2_last", 'authorFeedBack2', "commiteeFeedBack2", "2222", "aff2"),
("reviewer3@sth.edu", "r3_first", "r3_last", 'authorFeedBack3', "commiteeFeedBack3", "333", "aff3");


INSERT INTO Paper(title, abstract, fileName, authorId)
VALUES
('title1', 'abstract1', 'fileName1', "java@sql.com"),
('title2', 'abstract2', 'fileName2', "java@sql.com"),
('title3', 'abstract3', 'fileName3', "java2@sql.com");

INSERT INTO Review(recommendation, meritScore, readabilityScore, relevanceScore,
                   originalityScore, paperId, reviewerId)
VALUES
('published', 1, 1, 1, 1, 1, 'reviewer1@mit.edu')
;                   