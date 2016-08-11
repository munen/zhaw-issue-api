ALTER TABLE projects ADD client_id text NOT NULL;
UPDATE projects SET client_id = 'none';
