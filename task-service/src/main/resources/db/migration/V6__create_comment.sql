CREATE TABLE IF NOT EXISTS "comments" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    text VARCHAR(255) NOT NULL,
    user_id uuid NOT NULL,
    task_id uuid NOT NULL,
    created_at timestamptz,
    updated_at timestamptz,
    CONSTRAINT pkey_comments PRIMARY KEY (id),
    CONSTRAINT fkey_comment_tasks FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);