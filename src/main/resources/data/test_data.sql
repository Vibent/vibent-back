-- User
INSERT INTO user (id, ref, email, first_name, last_name, phone_number, deleted, profile_pic_location,
                                  password, account_non_expired, account_non_locked, credentials_non_expired,
                                  last_password_reset, enabled)
VALUES (1, '6dd8e18a-9f8b-412c-8d20-7b93039ee583', 'cr.in.france@gmail.com', 'Conor', 'Ryan', '+33673804354', 0,
        'https://gravatar.com/avatar/11933c4bb091d5e4fc447d302512dd84.jpg?s=128&d=retro',
        '$2a$10$bfyg6zikIO5gtzK0pVAJDulKBuvBz5Km6hpPp4FMUU8VVH9HY3MiC', 1, 1, 1, '2019-04-14 15:57:28', 1),
       (2, '7dd8e18a-9f8b-412c-8d20-7b93039ee583', 'frank.spizza@gmail.com', 'Frank', 'Spizza', '+33673804355', 0,
        'https://gravatar.com/avatar/12933c4bb091d5e4fc447d302512dd84.jpg?s=128&d=retro',
        '$2a$10$bfyg6zikIO5gtzK0pVAJDulKBuvBz5Km6hpPp4FMUU8VVH9HY3MiC', 1, 1, 1, '2019-04-14 15:57:28', 1),
       (3, '8dd8e18a-9f8b-412c-8d20-7b93039ee583', 'thomas.malik@gmail.com', 'Thomas', 'Malik', '+33673504355', 0,
        'https://gravatar.com/avatar/12933c4bb09177e4f8847d302512dd84.jpg?s=128&d=retro',
        '$2a$10$bfyg6zikIO5gtzK0pVAJDulKBuvBz5Km6hpPp4FMUU8VVH9HY3MiC', 1, 1, 1, '2019-04-14 15:57:28', 1),
       (4, '9dd8e18a-9f8b-412c-8d20-7b93039ee583', 'kevin.felin@gmail.com', 'Kevin', 'Felin', '+33643804355', 0,
        'https://gravatar.com/avatar/12933c4bb09166e4fc447d302512dd84.jpg?s=128&d=retro',
        '$2a$10$bfyg6zikIO5gtzK0pVAJDulKBuvBz5Km6hpPp4FMUU8VVH9HY3MiC', 1, 1, 1, '2019-04-14 15:57:28', 1),
       (5, '1dd8e18a-9f8b-412c-8d20-7b93039ee583', 'sophie.catere@gmail.com', 'Sophie', 'Catere', '+33573804355', 0,
        'https://gravatar.com/avatar/12933c4bb09555e4fc447d302512dd84.jpg?s=128&d=retro',
        '$2a$10$bfyg6zikIO5gtzK0pVAJDulKBuvBz5Km6hpPp4FMUU8VVH9HY3MiC', 1, 1, 1, '2019-04-14 15:57:28', 1),
       (6, '0dd8e18a-9f8b-412c-8d20-7b93039ee583', 'lea.blanc@gmail.com', 'Lea', 'Blanc', '+33773804355', 0,
        'https://gravatar.com/avatar/12933c4bb441d5e4fc447d302512dd84.jpg?s=128&d=retro',
        '$2a$10$bfyg6zikIO5gtzK0pVAJDulKBuvBz5Km6hpPp4FMUU8VVH9HY3MiC', 1, 1, 1, '2019-04-14 15:57:28', 1),
       (7, '2dd8e18a-9f8b-412c-8d20-7b93039ee583', 'marga.zaragon@gmail.com', 'Marga', 'Zaragon', '+33773847155', 0,
        'https://gravatar.com/avatar/12933c4bb44621e4fc447d302512dd84.jpg?s=128&d=retro',
        '$2a$10$bfyg6zikIO5gtzK0pVAJDulKBuvBz5Km6hpPp4FMUU8VVH9HY3MiC', 1, 1, 1, '2019-04-14 15:57:28', 1);

-- Group
INSERT INTO group_t (id, ref, name, description, image_path, has_default_admin, deleted)
VALUES (1, 'd53e3a2e-4035-4ee2-a8cb-853d424c0197', 'INSA buddies',
        'A group to share activities between friends in our class', null, 1, 0);

-- Group Membership
INSERT INTO membership (id, user_id, group_id, admin, deleted, date)
VALUES (1, 1, 1, 1, 0, '2019-04-14 13:58:11'),
       (2, 2, 1, 1, 0, '2019-04-14 13:58:11'),
       (3, 3, 1, 1, 0, '2019-04-14 13:58:11'),
       (4, 4, 1, 1, 0, '2019-04-14 13:58:11'),
       (5, 5, 1, 1, 0, '2019-04-14 13:58:11'),
       (6, 6, 1, 1, 0, '2019-04-14 13:58:11'),
       (7, 7, 1, 1, 0, '2019-04-14 13:58:11');

-- Event
INSERT INTO event (id, ref, group_id, title, description, start_date, end_date, deleted, standalone)
VALUES (1, '7783962b-c7f1-4825-89e4-4e54052fa011', 1, 'Weekend at sea',
        'Let''s all go the beach ! Activities include swimming, sunbathing, barbecues, fishing, etc !',
        '2021-04-28 08:00:00', null, 0, 0);

-- Event Participation
INSERT INTO event_participation (id, user_id, event_id, answer, is_visible, deleted)
VALUES (1, 1, 1, 'YES', 1, 0),
       (2, 2, 1, 'MAYBE', 1, 0),
       (3, 3, 1, 'YES', 1, 0),
       (4, 4, 1, 'YES', 1, 0),
       (5, 5, 1, 'YES', 1, 0),
       (6, 6, 1, 'YES', 1, 0),
       (7, 7, 1, 'NO', 1, 0);

-- Alimentation
INSERT INTO alimentation_bubble (id, event_id, user_id, deleted)
VALUES (1, 1, 1, 0);
INSERT INTO alimentation_entry (id, bubble_id, name, total_requested, type, deleted)
VALUES (1, 1, 'Cola', 5, 'DRINK', 0),
       (2, 1, 'Mountain Dew', 10, 'DRINK', 0),
       (3, 1, 'Hambugers', 12, 'FOOD', 0),
       (4, 1, 'Cheetos', 10, 'FOOD', 0),
       (5, 1, 'Saucisson', 15, 'FOOD', 0);
INSERT INTO alimentation_bring (id, entry_id, user_id, quantity, deleted)
VALUES (1, 1, 1, 1, 0),
       (2, 1, 2, 1, 0),
       (3, 2, 3, 5, 0),
       (4, 2, 1, 3, 0),
       (5, 3, 2, 3, 0),
       (6, 3, 1, 4, 0),
       (7, 4, 2, 2, 0),
       (8, 4, 3, 1, 0),
       (9, 4, 4, 2, 0);

-- Checkbox
INSERT INTO checkbox_bubble (id, title, event_id, user_id, deleted)
VALUES (1, 'Things to bring', 1, 1, 0);

INSERT INTO checkbox_option (id, bubble_id, user_id, content, deleted)
VALUES (1, 1, 1, 'Barbecue', 0),
       (2, 1, 1, 'Fishing Rod', 0),
       (3, 1, 1, 'Bait & Tackle', 0),
       (4, 1, 1, 'Speaker', 0),
       (5, 1, 1, 'Cards', 0);

INSERT INTO checkbox_answer (id, user_id, option_id, deleted)
VALUES (1, 1, 2, 0),
       (2, 1, 5, 0),
       (3, 2, 4, 0),
       (4, 3, 3, 0);

-- Free
INSERT INTO free_bubble (id, title, content, event_id, user_id, deleted)
VALUES (5, 'Kevin Birthday', 'Hey guys, it''s Kevin''s Birthday on sunday so make sure to bring presents !', 1, 1, 0);

-- Planning
INSERT INTO planning_bubble (id, title, event_id, user_id, deleted)
VALUES (1, 'Weekend planning', 1, 1, 0);

INSERT INTO planning_entry (id, start, end, content, has_time, bubble_id, user_id, deleted)
VALUES (1, '2021-04-28 08:00:00', null, 'Arrival', 1, 1, 1, 0),
       (2, '2021-04-28 09:00:00', null, 'Fishing', 1, 1, 1, 0),
       (3, '2021-04-28 12:00:00', null, 'Barbecue', 1, 1, 1, 0),
       (4, '2021-04-28 14:00:00', null, 'Free time', 1, 1, 1, 0),
       (5, '2021-04-29 13:00:00', null, 'Go home :(', 1, 1, 1, 0);

-- Survey
INSERT INTO survey_bubble (id, title, event_id, user_id, deleted)
VALUES (1, 'Particular dietary plan ?', 1, 1, 0);

INSERT INTO survey_option (id, bubble_id, user_id, content, deleted)
VALUES (1, 1, 1, 'None', 0),
       (2, 1, 1, 'Vegetarian', 0),
       (3, 1, 1, 'Flexitarian', 0),
       (4, 1, 1, 'Halal', 0);

INSERT INTO survey_answer (id, user_id, option_id, deleted)
VALUES (1, 1, 1, 0),
       (2, 2, 1, 0),
       (3, 3, 2, 0),
       (4, 4, 2, 0),
       (5, 5, 3, 0);

-- Travel
INSERT INTO travel_bubble (id, event_id, user_id, deleted)
VALUES (1, 1, 1, 0);

INSERT INTO travel_proposal (id, bubble_id, user_id, capacity, pass_by_cities, deleted)
VALUES (1, 1, 1, 3, 'af9599b85ad97dcead64bbb7bc500445', 0),
       (2, 1, 2, 2, '3ad8b02bee6e8845395d49e7e73c162f', 0),
       (3, 1, 3, 1, '3ad8b02bee6e8845395d49e7e73c162f', 0);

INSERT INTO travel_request (id, user_id, bubble_id, capacity, proposal_id, pass_by_cities, deleted)
VALUES (1, 4, null, 1, 2, '3ad8b02bee6e8845395d49e7e73c162f', 0),
       (2, 5, null, 1, 2, null, 1),
       (3, 6, 1, 1, null, '2925533', 0);