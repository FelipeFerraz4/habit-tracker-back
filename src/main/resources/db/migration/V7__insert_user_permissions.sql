INSERT INTO user_permission (user_id, permission_id)
VALUES
    (
        (SELECT id FROM users WHERE user_name = 'admin@example.com'),
        (SELECT id FROM permissions WHERE description = 'ADMIN')
    ),
    (
        (SELECT id FROM users WHERE user_name = 'user@example.com'),
        (SELECT id FROM permissions WHERE description = 'COMMUM_USER')
    ),
    (
        (SELECT id FROM users WHERE user_name = 'manager@example.com'),
        (SELECT id FROM permissions WHERE description = 'MANAGER')
    ),
    (
        (SELECT id FROM users WHERE user_name = 'guest@example.com'),
        (SELECT id FROM permissions WHERE description = 'COMMUM_USER')
    );
