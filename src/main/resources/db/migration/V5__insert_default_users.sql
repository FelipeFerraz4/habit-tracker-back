INSERT INTO users (
    user_name,
    email,
    password,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    enabled
) VALUES
      ('admin_user', 'admin@example.com', '$2a$10$6p8pM1v0UlGfFdIPqw4Q7OWL5LjzyrCZTnFiGhDbz6Oj7C3pclMZG', true, true, true, true),
      ('common_user', 'user@example.com', '$2a$10$dzrpx0L4sX8zGi6lZkxDcOhxUT6/1nGHKzQz2ZkMbdAVuyQRoQw9e', true, true, true, true);
