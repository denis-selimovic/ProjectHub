CREATE TABLE IF NOT EXISTS "tokens" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    type VARCHAR(50) NOT NULL,
    created_at timestamptz,
    updated_at timestamptz,
    duration INTEGER NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    valid BOOLEAN NOT NULL DEFAULT TRUE,
    user_id uuid NOT NULL,
    CONSTRAINT pkey_tokens PRIMARY KEY (id),
    CONSTRAINT fkey_token_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);