use vibent_back;

# ---------------------------------------------- ALIMENTATION ------------------------------------------------ #
# ROLE_USER Alimentation Bubble
SELECT CASE WHEN EXISTS(
    SELECT u.id FROM user u
    JOIN group_membership gm ON gm.user_id = u.id
    JOIN group_t g ON gm.group_id = g.id
    JOIN event e ON g.id = e.group_id
    JOIN alimentation_bubble ab ON e.id = ab.event_id
    WHERE ab.id = :targetId AND u.username = :username)
  THEN TRUE
       ELSE FALSE END AS is_authorized;

# ROLE_ADMIN Alimentation Bubble
SELECT CASE WHEN EXISTS(
    SELECT u.id FROM user u
    JOIN group_membership gm ON gm.user_id = u.id
    JOIN group_t g ON gm.group_id = g.id
    JOIN event e ON g.id = e.group_id
    JOIN alimentation_bubble ab ON e.id = ab.event_id
    WHERE ab.id = :targetId AND u.username = :username AND g.has_default_admin = TRUE)
  THEN TRUE
       ELSE CASE WHEN EXISTS(
        SELECT u.id FROM user u
        JOIN group_adminship ga ON ga.user_id = u.id
        JOIN group_t g ON ga.group_id = g.id
        JOIN event e ON g.id = e.group_id
        JOIN alimentation_bubble ab ON e.id = ab.event_id
        WHERE ab.id = :targetId AND u.username = :username
       )
      THEN TRUE ELSE FALSE END
  END AS is_authorized;

# ROLE_USER Alimentation Entry
SELECT CASE WHEN EXISTS(
    SELECT u.id FROM user u
    JOIN group_membership gm ON gm.user_id = u.id
    JOIN group_t g ON gm.group_id = g.id
    JOIN event e ON g.id = e.group_id
    JOIN alimentation_bubble ab ON e.id = ab.event_id
    JOIN alimentation_entry ae ON ab.id = ae.bubble_id
    WHERE ae.id=:targetId AND u.username=:username )
  THEN TRUE
       ELSE FALSE END AS is_authorized;

# ROLE_USER Alimentation Bring
SELECT CASE WHEN EXISTS(
    SELECT u.id FROM user u
    JOIN group_membership gm ON gm.user_id = u.id
    JOIN group_t g ON gm.group_id = g.id
    JOIN event e ON g.id = e.group_id
    JOIN alimentation_bubble ab ON e.id = ab.event_id
    JOIN alimentation_entry ae ON ab.id = ae.bubble_id
    JOIN alimentation_bring ab ON ae.id = ab.entry_id
    WHERE ab.id=:targetId AND u.username=:username )
  THEN TRUE
       ELSE FALSE END AS is_authorized;

# ---------------------------------------------- EVENT ------------------------------------------------ #
# ROLE_ADMIN EVENT (CREATE Bubble / DELETE Bubble / ADD Member / DELETE MEMBER)
SELECT CASE WHEN EXISTS(
    SELECT u.id FROM user u
    JOIN group_membership gm ON gm.user_id = u.id
    JOIN group_t g ON gm.group_id = g.id
    JOIN event e ON g.id = e.group_id
    WHERE e.ref = :eventRef AND u.username = :username AND g.has_default_admin = TRUE)
  THEN TRUE
       ELSE CASE WHEN EXISTS(
        SELECT u.id FROM user u
        JOIN group_adminship ga ON ga.user_id = u.id
        JOIN group_t g ON ga.group_id = g.id
        JOIN event e ON g.id = e.group_id
        WHERE e.ref = :eventRef AND u.username = :username
       )
      THEN TRUE ELSE FALSE END
  END AS is_authorized;
