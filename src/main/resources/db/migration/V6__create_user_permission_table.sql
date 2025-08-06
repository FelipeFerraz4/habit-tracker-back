CREATE TABLE user_permission (
    user_id UUID NOT NULL,
    permission_id UUID NOT NULL,
    PRIMARY KEY (user_id, permission_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_permission FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);
