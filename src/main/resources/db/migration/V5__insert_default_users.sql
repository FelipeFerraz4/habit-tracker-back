INSERT INTO users (
    user_name,
    password,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    enabled
) VALUES
      ('admin@example.com', '{pbkdf2}73d8c2a38f5eda7dcb7bec22d18d35d0145ef3d4fb9138c8cc7665e9c9e4d5037c860c0f3bbe25fe2ef1', true, true, true, true),
      ('user@example.com', '{pbkdf2}4255475edf87575e290af1ee5c4d047bbf0ffd0fce8a6db259589e2a1316e28625e6b45cb435e2680dc6244c24237b84', true, true, true, true),
      ('manager@example.com', '{pbkdf2}650ea25db9a69601e4f73397fde86b6af988dccfd2e77baa3ad0c72619871985bd53710b90069cd5ae6b72f4599cc231', true, true, true, true),
      ('guest@example.com', '{pbkdf2}1cf2676da3ef81216c651e2875a702e91e7962c2e3545c8444d368ee6ad5b7afef5f2fd5cfd7d3b9e31e7eeb1fb944b0', true, true, true, true);
