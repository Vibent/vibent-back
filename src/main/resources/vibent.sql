CREATE TABLE user
(
    id INT NOT NULL AUTO_INCREMENT,
    ref CHAR(36) NOT NULL CHARACTER SET ascii,
    first_name VARCHAR(64),
    last_name VARCHAR(64),
    name VARCHAR(64),
    email VARCHAR(320),
    image_path VARCHAR(500),
    pays VARCHAR(255),
    password VARCHAR(255),
    salt VARCHAR(64),
    PRIMARY KEY(id)
);

CREATE TABLE GroupT
(
    id INT NOT NULL,
    ref CHAR(36) NOT NULL,
    name VARCHAR(64),
    image_path VARCHAR(255),
    has_default_admin BOOLEAN,
    PRIMARY KEY(id)
);

CREATE TABLE EventParticipation
(
    user_ref CHAR(36) REFERENCES User(ref),
    group_ref CHAR(36) REFERENCES GroupT(ref),
    answer ENUM('Yes', 'No', 'Maybe', 'Unanswered'),
    isVisible BOOLEAN
);

CREATE TABLE Event
(
    id INT NOT NULL,
    ref CHAR(36) NOT NULL,
    group_ref CHAR(36) NOT NULL REFERENCES GroupT(ref),
    title VARCHAR(255),
    description VARCHAR(500),
    start_date DATETIME,
    end_date DATETIME,
    PRIMARY KEY(id)
);

CREATE TABLE GroupMemberShip
(
    user_ref CHAR(36) NOT NULL REFERENCES User(ref),
    group_ref CHAR(36) NOT NULL REFERENCES GroupT(ref),
    is_admin BOOLEAN NOT NULL,
    is_accepted BOOLEAN NOT NULL
);

CREATE TABLE GroupInviteLink
(
    id INT NOT NULL,
  group_ref CHAR(36) NOT NULL REFERENCES GroupT(ref),
    hash VARCHAR(255),
    expires DATETIME,
    PRIMARY KEY(id)
);

CREATE TABLE BubbleOwnerShip
(
    id INT NOT NULL,
    event_ref CHAR(36) NOT NULL REFERENCES Event(id),
    bubble_type ENUM('TravelBubble', 'LocationBubble', 'AlimentationBubble', 'SurveyBubble','CheckBoxBubble', 'PlanningBubble', 'FreeBubble'),
    bubble_id INT,
    creator_ref CHAR(36),
    PRIMARY KEY(id)
);

CREATE TABLE TravelBubble
(
    id INT PRIMARY KEY NOT NULL
);

CREATE TABLE TravelProposal
(
    id INT NOT NULL,
    bubble_id INT REFERENCES TravelBubble(id),
    driver_ref CHAR(36) REFERENCES User(ref),
    capacity INT,
    pass_by_cities VARCHAR(500),
    PRIMARY KEY(id)
);

CREATE TABLE TravelRequest
(
    id INT NOT NULL,
    creator_ref CHAR(36) REFERENCES User(ref),
    bubble_id INT REFERENCES TravelBubble(id),
    capacity INT,
    attached_to_proposal BOOLEAN,
    PRIMARY KEY(id)
);

CREATE TABLE AttachedRequest
(
    proposal_id INT REFERENCES TravelProposal(id),
	  request_id INT REFERENCES TravelRequest(id)
);

CREATE TABLE LocationBubble
(
    id INT PRIMARY KEY NOT NULL,
    coord VARCHAR(255)
);

CREATE TABLE AlimentationBubble
(
    id INT PRIMARY KEY NOT NULL
);

CREATE TABLE AlimentationEntry
(
    id INT NOT NULL,
    bubble_id INT REFERENCES AlimentationBubble(id),
    name VARCHAR(64),
    total_requested INT,
    total_current INT,
    type ENUM('Food', 'Drink'),
    PRIMARY KEY(id)
);

CREATE TABLE AlimentationBringing
(
    entry_id INT REFERENCES AlimentationEntry(id),
    user_ref CHAR(36),
    quantity INT
);

CREATE TABLE SurveyBubble 
(
    id INT,
	  title VARCHAR(500)
);

CREATE TABLE SurveyResponse
(
    id INT NOT NULL,
    survey_id INT REFERENCES SurveyBubble(id),
    content VARCHAR(500),
    PRIMARY KEY(id)
);

CREATE TABLE UsersSurveyResponses
(
    user_ref CHAR(36) REFERENCES User(ref),
    surveyresponse_id INT REFERENCES SurveyResponse(id)
);

CREATE TABLE CheckBoxBubble
(
   id INT,
   title VARCHAR(500)
);

CREATE TABLE CheckBoxResponse
(
   id INT NOT NULL,
   bubble_id INT REFERENCES CheckBoxBubble(id),
   content VARCHAR(500),
   PRIMARY KEY(id)
);

CREATE TABLE UsersCheckBoxResponses
(
    user_ref CHAR(36) REFERENCES User(ref),
    checkboxresponse_id INT REFERENCES CheckBoxResponse(id)
);

CREATE TABLE PlanningBubble
(
     id INT NOT NULL,
     PRIMARY KEY(id)
);

CREATE TABLE PlanningEntry
(
    id INT NOT NULL,
    bubble_id INT REFERENCES CheckBoxBubble(id),
    creator_ref CHAR(36) REFERENCES User(ref),
    start DATETIME,
    end DATETIME,
    content VARCHAR(500),
    PRIMARY KEY(id)
);

CREATE TABLE FreeBubble
(
    id INT NOT NULL,
    title VARCHAR(100),
    content VARCHAR(1000),
    PRIMARY KEY(id)
);
