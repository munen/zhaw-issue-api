CREATE TABLE issues
(id SERIAL,
 cid text NOT NULL,
 done boolean DEFAULT false,
 due_date timestamp NOT NULL,
 title text NOT NULL,
 project_id integer REFERENCES projects(id));
