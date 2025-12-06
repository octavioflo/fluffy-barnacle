INSERT INTO punishment (type, details) VALUES
    ('SHAME_POST', 'Post on social media if missed'),
    ('BAD_CHARITY_DONATION', 'Donate $5 to a bad charity');

INSERT INTO habit (name, description, date_created, punishment_id)
VALUES
    ('Workout', 'Workout once a week', CURRENT_DATE, 1),
    ('Clean Apartment', 'Clean the apartment every night', CURRENT_DATE, 2),
    ('Call Parents', 'Call parents once a week', CURRENT_DATE, 1);
