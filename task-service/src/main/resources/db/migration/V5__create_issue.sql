CREATE TABLE IF NOT EXISTS "issues" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    project_id uuid NOT NULL,
    priority_id uuid NOT NULL,
    CONSTRAINT pkey_issues PRIMARY KEY (id),
    CONSTRAINT fkey_issue_priorities FOREIGN KEY (priority_id) REFERENCES priorities(id)
);