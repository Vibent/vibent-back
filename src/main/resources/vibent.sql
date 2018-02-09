CREATE TABLE User
(
    id INT NOT NULL,
    ref char(36),
    first_name VARCHAR(64),
    name VARCHAR(64),
    email VARCHAR(320),
    image_path VARCHAR(500),
    pays VARCHAR(255),
    password VARCHAR(255),
    salt VARCHAR(64),
    PRIMARY KEY(id)
)

CREATE TABLE Group
(
    id INT NOT NULL,
    ref char(36) NOT NULL,
    name VARCHAR(64),
    image_path VARCHAR(255),
    has_default_admin bool,
    PRIMARY KEY(id)
)

CREATE TABLE EventParticipation
(
    user_ref char(36) NOT NULL,
    groupe_ref char(36) NOT NULL,
    answer ENUM('Yes', 'No', 'Maybe', 'Unanswered')
    isVisible bool,
    FOREIGN KEY (user_ref) REFERENCES User(id),
    FOREIGN KEY (groupe_ref) REFERENCES Group(id)
)

CREATE TABLE Event
(
    id INT NOT NULL,
    ref char(36) NOT NULL,
    group_ref char(36) NOT NULL,
    title VARCHAR(255),
    description VARCHAR(500),
    start_date DateTime,
    end_date DateTime,
    PRIMARY KEY(id),
    FOREIGN KEY (group_ref) REFERENCES Group(id)
)

CREATE TABLE GroupMemberShip
(
    user_ref char(36) NOT NULL,
    group_ref char(36) NOT NULL,
    is_admin bool NOT NULL,
    is_accepted bool NOT NULL,
    FOREIGN KEY (user_ref) REFERENCES User(id),
    FOREIGN KEY (group_ref) REFERENCES Group(id)
)

CREATE TABLE GroupInviteLink
(
    id INT NOT NULL,
    group_ref char(36) NOT NULL,
    hash VARCHAR(255),
    expires DateTime,
    PRIMARY KEY(id),
    FOREIGN KEY (group_ref) REFERENCES Group(id)
)

CREATE TABLE BubbleOwnerShip
(
    id INT NOT NULL,
    event_ref char(36) NOT NULL,
    bubble_type ENUM('TravelBubble', 'LocationBubble', 'AlimentationBubble', 'SurveyBubble','CheckBoxBubble', 'PlanningBubble', 'FreeBubble'),
    bubble_id INT,
    creator_ref char(36),
    PRIMARY KEY(id),
    FOREIGN KEY (event_ref) REFERENCES Event(id)
)

CREATE TABLE TravelBubble
(
    id INT PRIMARY KEY NOT NULL,
)
CREATE TABLE TravelProposal
(
    id INT NOT NULL,
    bubble_id INT,
    driver_ref char(36),
    capacity INT,
    pass_by_cities VARCHAR(500),
    PRIMARY KEY(id),
    FOREIGN KEY (bubble_id) REFERENCES TravelBubble(id),
    FOREIGN KEY (driver_ref) REFERENCES User(ref)
)

CREATE TABLE TravelRequest
(
    id INT NOT NULL,
    creator_ref char(36),
     bubble_id INT,
    capacity INT,
    attached_to_proposal bool,
    FOREIGN KEY (bubble_id) REFERENCES TravelBubble(id),
    FOREIGN KEY (creator_ref) REFERENCES User(ref),
    PRIMARY KEY(id)
)

CREATE TABLE AttachedRequest
(
    proposal_id INT,
	request_id INT,
	FOREIGN KEY (proposal_id) REFERENCES TravelProposal(id),
	FOREIGN KEY (request_id) REFERENCES TravelRequest(id)
)

CREATE TABLE LocationBubble
(
    id INT PRIMARY KEY NOT NULL,
    coord VARCHAR(255)
)

CREATE TABLE AlimentationBubble
(
    id INT PRIMARY KEY NOT NULL
)

CREATE TABLE AlimentationEntry
(
    id INT NOT NULL,
    bubble_id INT,
	name VARCHAR(64),
	total_requested INT,
	total_current INT,
	type ENUM('Food', 'Drink'),
	FOREIGN KEY (bubble_id) REFERENCES AlimentationBubble(id),
	PRIMARY KEY(id)
)

CREATE TABLE AlimentationBringing
(
    entry_id INT,
	user_ref char(36),
	quantity INT,
	FOREIGN KEY (entry_id) REFERENCES AlimentationEntry(id)
)

CREATE TABLE SurveyBubble 
(
    id INT,
	title VARCHAR(500)
)

CREATE TABLE SurveyResponse
(
    id NOT NULL,
	survey_id INT,
	content VARCHAR(500),
	PRIMARY KEY(id),
	FOREIGN KEY (survey_id) REFERENCES SurveyBubble(id)
)

CREATE TABLE UsersSurveyResponses
(
    user_ref char(36),
	surveyresponse_id INT,
	FOREIGN KEY (surveyresponse_id) REFERENCES SurveyResponse(id),
	FOREIGN KEY (user_ref) REFERENCES User(ref)
)

CREATE TABLE CheckBoxBubble
(
   id INT,
   title VARCHAR(500)
)

CREATE TABLE CheckBoxResponse
(
   id NOT NULL,
   bubble_id INT,
   content VARCHAR(500),
   PRIMARY KEY(id),
   FOREIGN KEY (bubble_id) REFERENCES CheckBoxBubble(id)
)

CREATE TABLE UsersCheckBoxResponses
(
    user_ref char(36),
	checkboxresponse_id INT,
	FOREIGN KEY (user_ref) REFERENCES User(ref),
	FOREIGN KEY (checkboxresponse_id) REFERENCES CheckBoxResponse(id)
)

CREATE TABLE PlanningBubble
(
   id NOT NULL,
   PRIMARY KEY(id)
)

CREATE TABLE PlanningEntry
(
    id NOT NULL,
    bubble_id INT
	creator_ref UUID,
	start DateTime,
	end DateTime,
	content VARCHAR(500),
	PRIMARY KEY(id),
	FOREIGN KEY (bubble_id) REFERENCES CheckBoxBubble(id),
	FOREIGN KEY (creator_ref) REFERENCES User(ref)
)

CREATE TABLE FreeBubble
(
    id NOT NULL,
	title VARCHAR(100),
	content VARCHAR(1000),
	PRIMARY KEY(id)
)
