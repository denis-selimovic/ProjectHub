CREATE TABLE IF NOT EXISTS "tasks" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    user_id uuid,
    user_first_name VARCHAR (50),
    user_last_name VARCHAR (50),
    project_id uuid NOT NULL,
    project_name VARCHAR (50),
    priority_id uuid NOT NULL,
    status_id uuid NOT NULL,
    type_id uuid NOT NULL,
    created_at timestamptz,
    updated_at timestamptz,
    CONSTRAINT pkey_tasks PRIMARY KEY (id),
    CONSTRAINT fkey_task_priorities FOREIGN KEY (priority_id) REFERENCES priorities(id),
    CONSTRAINT fkey_task_statuses FOREIGN KEY (status_id) REFERENCES statuses(id),
    CONSTRAINT fkey_task_types FOREIGN KEY (type_id) REFERENCES types(id)
);