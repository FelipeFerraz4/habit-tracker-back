CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE permissions (
     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     description VARCHAR(255) NOT NULL
);
