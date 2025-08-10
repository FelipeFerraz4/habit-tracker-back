INSERT INTO users (
    user_name,
    password,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    enabled
) VALUES
      ('admin@example.com', 'c71f5a67e3363a4acf4a392c9480c399d2eba27da2936d430cd2e62e5427919877c66c32225c1f4203e4bb3d8045a405', true, true, true, true),
      ('user@example.com', '4255475edf87575e290af1ee5c4d047bbf0ffd0fce8a6db259589e2a1316e28625e6b45cb435e2680dc6244c24237b84', true, true, true, true),
      ('manager@example.com', '650ea25db9a69601e4f73397fde86b6af988dccfd2e77baa3ad0c72619871985bd53710b90069cd5ae6b72f4599cc231', true, true, true, true),
      ('guest@example.com', '1cf2676da3ef81216c651e2875a702e91e7962c2e3545c8444d368ee6ad5b7afef5f2fd5cfd7d3b9e31e7eeb1fb944b0', true, true, true, true);
