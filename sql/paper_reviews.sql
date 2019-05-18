CREATE DATABASE PaperReviews;
 
USE PaperReviews; 

#Creating Author Table with EmailAddress as PK
CREATE TABLE Author (
	EmailAddress VARCHAR(255) PRIMARY KEY,
	firstName VARCHAR(255) NOT NULL,
	lastName VARCHAR(255) NOT NULL
);

#Creating Topic Table with id as PK
CREATE TABLE Topic (
	id INT PRIMARY KEY AUTO_INCREMENT,
	topicName VARCHAR(255) NOT NULL
);

#Creating Reviewer Table with EmailAddress as PK
CREATE TABLE Reviewer (
	EmailAddress VARCHAR(255) PRIMARY KEY,
	firstName VARCHAR(255) NOT NULL,
	lastName VARCHAR(255) NOT NULL,
	authorFeedBack TEXT,
	commiteeFeedBack TEXT,
	phoneNum VARCHAR(13) NOT NULL,
	affiliation VARCHAR(255) NOT NULL
);

#Creating Reviewer Table with id as PK
CREATE TABLE ReviewerTopic (
	id INT PRIMARY KEY AUTO_INCREMENT,
	topicId INT NOT NULL,
    ReviewerId VARCHAR(255) NOT NULL
    FOREIGN KEY (ReviewerId) REFERENCES Reviewer(EmailAddress),
    FOREIGN KEY (topicId) REFERENCES Topic(id)
);

#Creating Paper with id as PK
CREATE TABLE Paper (
	id INT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL,
	abstract TEXT,
	fileName VARCHAR(255) NOT NULL,
	authorId VARCHAR(255) NOT NULL,
	FOREIGN KEY (authorId) REFERENCES Author(EmailAddress)
);

#Creating weak entity Review Table with id as PK
CREATE TABLE Review (
	id INT PRIMARY KEY AUTO_INCREMENT,
	recommendation TEXT,
	meritScore TINYINT NOT NULL,
	readabilityScore TINYINT NOT NULL,
	relevanceScore TINYINT NOT NULL,	
	originalityScore TINYINT NOT NULL,
	paperId INT NOT NULL,
	reviewerId VARCHAR(255) NOT NULL,
	FOREIGN KEY (PaperId) REFERENCES Paper(Id),
	FOREIGN KEY (ReviewerId) REFERENCES Reviewer(EmailAddress)
);