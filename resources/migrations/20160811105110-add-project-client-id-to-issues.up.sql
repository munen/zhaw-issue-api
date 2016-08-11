ALTER TABLE issues ADD project_client_id text NOT NULL;
UPDATE issues SET project_client_id = 'none';
