CREATE TABLE user
(
    id INT NOT NULL AUTO_INCREMENT,
    ref CHAR(36) NOT NULL UNIQUE,
    first_name VARCHAR(64),
    last_name VARCHAR(64),
    name VARCHAR(64),
    email VARCHAR(320),
    image_path VARCHAR(500),
    pays VARCHAR(255),
    password VARCHAR(255),
    salt VARCHAR(64),
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id),
    INDEX(ref)
);

CREATE TABLE group_t
(
    id INT NOT NULL AUTO_INCREMENT,
    ref CHAR(36) NOT NULL,
    name VARCHAR(64),
    image_path VARCHAR(255),
    has_default_admin BOOLEAN,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id),
    INDEX(ref)
);

CREATE TABLE event_participation
(
    id INT NOT NULL AUTO_INCREMENT,
    user_ref CHAR(36),
    group_ref CHAR(36),
    answer ENUM('Yes', 'No', 'Maybe', 'Unanswered'),
    is_visible BOOLEAN,
    PRIMARY KEY(id),
    INDEX(user_ref),
    INDEX(group_ref)
);

CREATE TABLE event
(
    id INT NOT NULL AUTO_INCREMENT,
    ref CHAR(36) NOT NULL,
    group_ref CHAR(36) NOT NULL,
    title VARCHAR(255),
    description VARCHAR(500),
    start_date DATETIME,
    end_date DATETIME,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id),
    INDEX(ref)
);

CREATE TABLE group_membership
(
    id INT NOT NULL AUTO_INCREMENT,
    user_ref CHAR(36) NOT NULL REFERENCES user(ref),
    group_ref CHAR(36) NOT NULL REFERENCES group_t(ref),
    is_admin BOOLEAN NOT NULL,
    is_accepted BOOLEAN NOT NULL,
    PRIMARY KEY(id),
    INDEX(group_ref),
    INDEX(user_ref)

);

CREATE TABLE group_invite_link
(
    id INT NOT NULL AUTO_INCREMENT,
    group_ref CHAR(36) NOT NULL,
    hash VARCHAR(255),
    expires DATETIME,
    PRIMARY KEY(id),
    INDEX (group_ref)
);

CREATE TABLE bubble_ownership
(
    id INT NOT NULL AUTO_INCREMENT,
    event_ref CHAR(36) NOT NULL,
    bubble_type ENUM('TravelBubble', 'LocationBubble', 'AlimentationBubble', 'SurveyBubble','CheckBoxBubble', 'PlanningBubble', 'FreeBubble'),
    bubble_id INT,
    creator_ref CHAR(36),
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id),
    INDEX(event_ref)
);

CREATE TABLE travel_bubble
(
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id)
);

CREATE TABLE travel_proposal
(
    id INT NOT NULL AUTO_INCREMENT,
    bubble_id INT,
    driver_ref CHAR(36),
    capacity INT,
    pass_by_cities VARCHAR(500),
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id),
    INDEX(driver_ref)
);

CREATE TABLE travel_request
(
    id INT NOT NULL AUTO_INCREMENT,
    creator_ref CHAR(36),
    bubble_id INT,
    capacity INT,
    attached_to_proposal BOOLEAN,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id),
    INDEX(creator_ref)
);

CREATE TABLE attached_request
(
    id INT NOT NULL AUTO_INCREMENT,
    proposal_id INT,
	  request_id INT,
    PRIMARY KEY(id)
);

CREATE TABLE location_bubble
(
    id INT NOT NULL AUTO_INCREMENT,
    coord VARCHAR(255),
    PRIMARY KEY(id)
);

CREATE TABLE alimentation_bubble
(
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id)
);

CREATE TABLE alimentation_entry
(
    id INT NOT NULL AUTO_INCREMENT,
    bubble_id INT,
    name VARCHAR(64),
    total_requested INT,
    total_current INT,
    type ENUM('Food', 'Drink'),
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id)
);

CREATE TABLE alimentation_bring
(
    id INT NOT NULL AUTO_INCREMENT,
    entry_id INT,
    user_ref CHAR(36),
    quantity INT,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id),
    INDEX (user_ref)
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
    bubble_id INT,
    creator_ref CHAR(36),
    content VARCHAR(500),
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id),
    INDEX (creator_ref)
);

CREATE TABLE users_survey_responses
(
    id INT NOT NULL AUTO_INCREMENT,
    user_ref CHAR(36),
    survey_response_id INT,
    PRIMARY KEY(id),
    INDEX(user_ref)
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
   bubble_id INT,
   content VARCHAR(500),
   is_deleted BOOLEAN DEFAULT FALSE,
   PRIMARY KEY(id)
);

CREATE TABLE users_checkbox_responses
(
    id INT NOT NULL AUTO_INCREMENT,
    user_ref CHAR(36) REFERENCES user(ref),
    checkbox_response_id INT REFERENCES checkbox_response(id),
    PRIMARY KEY(id),
    INDEX (user_ref)
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
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id),
    INDEX (creator_ref)
);

CREATE TABLE free_bubble
(
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100),
    content VARCHAR(1000),
    PRIMARY KEY(id)
);

ALTER TABLE user
ADD UNIQUE KEY user_ref (ref);

ALTER TABLE group_t
ADD UNIQUE KEY group_ref (ref);

ALTER TABLE checkbox_bubble
ADD UNIQUE KEY checkbox_bubble_id (id);

ALTER TABLE checkbox_response
ADD UNIQUE KEY checkbox_response_id(id);

ALTER TABLE event
ADD UNIQUE KEY event_id (id);
ALTER TABLE event
ADD UNIQUE KEY event_ref (ref);

ALTER TABLE travel_bubble
ADD UNIQUE KEY travel_bubble_id (id);

ALTER TABLE travel_proposal
ADD UNIQUE KEY travel_proposal_id (id);

ALTER TABLE travel_request
ADD UNIQUE KEY travel_request_id (id);

ALTER TABLE alimentation_bubble
ADD UNIQUE KEY alimentation_bubble_id (id);

ALTER TABLE alimentation_entry
ADD UNIQUE KEY alimentation_entry_id (id);

ALTER TABLE survey_bubble
ADD UNIQUE KEY survey_bubble_id(id);

ALTER TABLE survey_response
ADD UNIQUE KEY survey_response_id(id);

ALTER TABLE planning_bubble
ADD UNIQUE KEY planning_bubble_id(id);



ALTER TABLE event_participation
ADD CONSTRAINT event_participation_user__fk
FOREIGN KEY (user_ref) REFERENCES user(ref);
ALTER TABLE event_participation
ADD CONSTRAINT event_participation_group__fk
FOREIGN KEY (group_ref) REFERENCES group_t(ref);

ALTER TABLE event
ADD CONSTRAINT event_group__fk
FOREIGN KEY (group_ref)REFERENCES group_t(ref);

ALTER TABLE group_membership
ADD CONSTRAINT group_membership_user__fk
FOREIGN KEY (user_ref) REFERENCES user(ref);
ALTER TABLE group_membership
ADD CONSTRAINT group_membership_group__fk
FOREIGN KEY (group_ref) REFERENCES group_t(ref);

ALTER TABLE group_invite_link
ADD CONSTRAINT group_invite_link_group__fk
FOREIGN KEY (group_ref)REFERENCES group_t(ref);

ALTER TABLE bubble_ownership
ADD CONSTRAINT bubble_ownership_event__fk
FOREIGN KEY (event_ref)REFERENCES event(ref);
ALTER TABLE bubble_ownership
ADD CONSTRAINT bubble_ownership_user__fk
FOREIGN KEY (creator_ref)REFERENCES user(ref);

ALTER TABLE travel_proposal
ADD CONSTRAINT travel_proposal_bubble__fk
FOREIGN KEY (bubble_id) REFERENCES travel_bubble(id);
ALTER TABLE travel_proposal
ADD CONSTRAINT travel_proposal_driver__fk
FOREIGN KEY (driver_ref) REFERENCES user(ref);

ALTER TABLE travel_request
ADD CONSTRAINT travel_request_creator__fk
FOREIGN KEY (creator_ref) REFERENCES user(ref);
ALTER TABLE travel_request
ADD CONSTRAINT travel_request_bubble__fk
FOREIGN KEY (bubble_id) REFERENCES travel_bubble(id);

ALTER TABLE attached_request
ADD CONSTRAINT attached_request_proposal__fk
FOREIGN KEY (proposal_id) REFERENCES travel_proposal(id);
ALTER TABLE attached_request
ADD CONSTRAINT attached_request_request__fk
FOREIGN KEY (request_id) REFERENCES travel_request(id);

ALTER TABLE alimentation_entry
ADD CONSTRAINT alimentation_entry_bubble__fk
FOREIGN KEY (bubble_id)REFERENCES alimentation_bubble(id);

ALTER TABLE alimentation_bring
ADD CONSTRAINT alimentation_bring_alimentry__fk
FOREIGN KEY (entry_id)REFERENCES alimentation_entry(id);

ALTER TABLE survey_response
ADD CONSTRAINT survey_response_bubble__fk
FOREIGN KEY (bubble_id)REFERENCES survey_bubble(id);
ALTER TABLE survey_response
ADD CONSTRAINT survey_response_user__fk
FOREIGN KEY (creator_ref)REFERENCES user(ref);

ALTER TABLE users_survey_responses
ADD CONSTRAINT users_survey_responses_user__fk
FOREIGN KEY (user_ref)REFERENCES user(ref);
ALTER TABLE users_survey_responses
ADD CONSTRAINT users_survey_responses_bubble__fk
FOREIGN KEY (survey_response_id)REFERENCES survey_response(id);

ALTER TABLE checkbox_response
ADD CONSTRAINT checkbox_response_bubble__fk
FOREIGN KEY (bubble_id)REFERENCES checkbox_bubble(id);

ALTER TABLE users_checkbox_responses
ADD CONSTRAINT users_checkbox_responses_user__fk
FOREIGN KEY (user_ref)REFERENCES user(ref);
ALTER TABLE users_checkbox_responses
ADD CONSTRAINT users_checkbox_responses_bubble__fk
FOREIGN KEY (checkbox_response_id)REFERENCES checkbox_response(id);

ALTER TABLE planning_entry
ADD CONSTRAINT planning_entry_bubble__fk
FOREIGN KEY (bubble_id)REFERENCES planning_bubble(id);
ALTER TABLE planning_entry
ADD CONSTRAINT planning_entry_user__fk
FOREIGN KEY (creator_ref)REFERENCES user(ref);
