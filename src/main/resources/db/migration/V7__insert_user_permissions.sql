INSERT INTO user_permission (user_id, permission_id)
VALUES
    (
        (SELECT id FROM users WHERE email = 'admin@example.com'),
        (SELECT id FROM permissions WHERE description = 'ADMIN')
    ),
    (
        (SELECT id FROM users WHERE email = 'user@example.com'),
        (SELECT id FROM permissions WHERE description = 'COMMUM_USER')
    );
