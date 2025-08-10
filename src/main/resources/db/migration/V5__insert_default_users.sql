INSERT INTO users (
    user_name,
    password,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    enabled
) VALUES
      ('admin@example.com', '{pbkdf2}+Ckrucus49xGNPOHmjKlZcOI1rFLCdgx5eD9z2iQxdk=', true, true, true, true),
      ('user@example.com', '{pbkdf2}rG84kU28SqLoNf6T85pyrX9prDas8Lvjcra1JAMaqTU=', true, true, true, true),
      ('manager@example.com', '{pbkdf2}K9pYfsn+G4lStV4glcvYV3Nl4baiIhBoBLMTYzJfrwM=', true, true, true, true),
      ('guest@example.com', '{pbkdf2}6C72HVm9Ka+Zf7zcVZdfGX0xMVBsRJXnFIoecsDb67g=', true, true, true, true);
