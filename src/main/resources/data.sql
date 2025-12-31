INSERT INTO punishment (id, type, details)
VALUES ('5f8c8b2e-1a6f-4c3a-9f2d-2b8d5e7a9a01', 'SHAME_POST', 'Post on social media if missed'),
       ('a2b3c4d5-6e7f-4812-9abc-0def12345678', 'BAD_CHARITY_DONATION', 'Donate $5 to a bad charity');

INSERT INTO habit (id, name, description, date_created, punishment_id)
VALUES ('d1e2f3a4-5b6c-4d7e-8f90-1234567890ab', 'Workout', 'Workout once a week', CURRENT_DATE,
        '5f8c8b2e-1a6f-4c3a-9f2d-2b8d5e7a9a01'),
       ('e2f3a4b5-6c7d-4e8f-9a01-234567890abc', 'Clean Apartment', 'Clean the apartment every night', CURRENT_DATE,
        'a2b3c4d5-6e7f-4812-9abc-0def12345678'),
       ('f3a4b5c6-7d8e-4f90-1a23-34567890abcd', 'Call Parents', 'Call parents once a week', CURRENT_DATE,
        '5f8c8b2e-1a6f-4c3a-9f2d-2b8d5e7a9a01');