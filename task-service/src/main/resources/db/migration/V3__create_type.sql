CREATE TABLE IF NOT EXISTS "types" (
   id uuid NOT NULL DEFAULT gen_random_uuid(),
   type VARCHAR(100) NOT NULL UNIQUE,
   CONSTRAINT pkey_types PRIMARY KEY (id)
);