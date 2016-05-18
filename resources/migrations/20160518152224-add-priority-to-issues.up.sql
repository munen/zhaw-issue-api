CREATE TYPE priority_type as ENUM ('1', '2', '3');
ALTER TABLE issues ADD priority priority_type;
ALTER TABLE issues ALTER COLUMN priority SET DEFAULT '1';
