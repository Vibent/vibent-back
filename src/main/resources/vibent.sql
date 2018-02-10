CREATE TABLE user
(
    id INT NOT NULL AUTO_INCREMENT,
    ref CHAR(36) NOT NULL,
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

CREATE TABLE group_t
(
    id INT NOT NULL AUTO_INCREMENT,
    ref CHAR(36) NOT NULL,
    name VARCHAR(64),
    image_path VARCHAR(255),
    has_default_admin BOOLEAN,
    PRIMARY KEY(id)
);

CREATE TABLE event_participation
(
    user_ref CHAR(36) REFERENCES user(ref),
    group_ref CHAR(36) REFERENCES group_t(ref),
    answer ENUM('Yes', 'No', 'Maybe', 'Unanswered'),
    isVisible BOOLEAN
);

CREATE TABLE event
(
    id INT NOT NULL AUTO_INCREMENT,
    ref CHAR(36) NOT NULL,
    group_ref CHAR(36) NOT NULL REFERENCES group_t(ref),
    title VARCHAR(255),
    description VARCHAR(500),
    start_date DATETIME,
    end_date DATETIME,
    PRIMARY KEY(id)
);

CREATE TABLE group_membership
(
    user_ref CHAR(36) NOT NULL REFERENCES user(ref),
    group_ref CHAR(36) NOT NULL REFERENCES group_t(ref),
    is_admin BOOLEAN NOT NULL,
    is_accepted BOOLEAN NOT NULL
);

CREATE TABLE group_invite_link
(
    id INT NOT NULL AUTO_INCREMENT,
    group_ref CHAR(36) NOT NULL REFERENCES group_t(ref),
    hash VARCHAR(255),
    expires DATETIME,
    PRIMARY KEY(id)
);

CREATE TABLE bubble_ownership
(
    id INT NOT NULL AUTO_INCREMENT,
    event_ref CHAR(36) NOT NULL REFERENCES event(id),
    bubble_type ENUM('TravelBubble', 'LocationBubble', 'AlimentationBubble', 'SurveyBubble','CheckBoxBubble', 'PlanningBubble', 'FreeBubble'),
    bubble_id INT,
    creator_ref CHAR(36),
    PRIMARY KEY(id)
);

CREATE TABLE travel_bubble
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT
);

CREATE TABLE travel_proposal
(
    id INT NOT NULL AUTO_INCREMENT,
    bubble_id INT REFERENCES travel_bubble(id),
    driver_ref CHAR(36) REFERENCES user(ref),
    capacity INT,
    pass_by_cities VARCHAR(500),
    PRIMARY KEY(id)
);

CREATE TABLE travel_request
(
    id INT NOT NULL AUTO_INCREMENT,
    creator_ref CHAR(36) REFERENCES user(ref),
    bubble_id INT REFERENCES travel_bubble(id),
    capacity INT,
    attached_to_proposal BOOLEAN,
    PRIMARY KEY(id)
);

CREATE TABLE attached_request
(
    proposal_id INT REFERENCES travel_proposal(id),
	  request_id INT REFERENCES travel_request(id)
);

CREATE TABLE location_bubble
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    coord VARCHAR(255)
);

CREATE TABLE alimentation_bubble
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT
);

CREATE TABLE alimentation_entry
(
    id INT NOT NULL AUTO_INCREMENT,
    bubble_id INT REFERENCES alimentation_bubble(id),
    name VARCHAR(64),
    total_requested INT,
    total_current INT,
    type ENUM('Food', 'Drink'),
    PRIMARY KEY(id)
);

CREATE TABLE alimentation_bringing
(
    entry_id INT REFERENCES alimentation_entry(id),
    user_ref CHAR(36),
    quantity INT
);

CREATE TABLE survey_bubble
(
    id INT NOT NULL AUTO_INCREMENT,
	  title VARCHAR(500),
    PRIMARY KEY (id)
);

CREATE TABLE survey_response
(
    id INT NOT NULL AUTO_INCREMENT,
    survey_id INT REFERENCES survey_bubble(id),
    content VARCHAR(500),
    PRIMARY KEY(id)
);

CREATE TABLE users_survey_responses
(
    user_ref CHAR(36) REFERENCES user(ref),
    surveyresponse_id INT REFERENCES survey_response(id)
);

CREATE TABLE checkbox_bubble
(
   id INT AUTO_INCREMENT,
   title VARCHAR(500),
  PRIMARY KEY (id)
);

CREATE TABLE checkbox_response
(
   id INT NOT NULL AUTO_INCREMENT,
   bubble_id INT REFERENCES checkbox_bubble(id),
   content VARCHAR(500),
   PRIMARY KEY(id)
);

CREATE TABLE users_checkbox_responses
(
    user_ref CHAR(36) REFERENCES user(ref),
    checkboxresponse_id INT REFERENCES checkbox_response(id)
);

CREATE TABLE planning_bubble
(
     id INT NOT NULL AUTO_INCREMENT,
     PRIMARY KEY(id)
);

CREATE TABLE planning_entry
(
    id INT NOT NULL AUTO_INCREMENT,
    bubble_id INT REFERENCES checkbox_bubble(id),
    creator_ref CHAR(36) REFERENCES user(ref),
    start DATETIME,
    end DATETIME,
    content VARCHAR(500),
    PRIMARY KEY(id)
);

CREATE TABLE free_bubble
(
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100),
    content VARCHAR(1000),
    PRIMARY KEY(id)
);
