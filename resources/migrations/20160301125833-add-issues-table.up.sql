CREATE TABLE issues
(id SERIAL,
 cid text NOT NULL,
 done boolean DEFAULT false,
 due_date date NOT NULL,
 title text NOT NULL);
