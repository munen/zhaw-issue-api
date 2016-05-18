ALTER TABLE issues ADD priority varchar(1);
ALTER TABLE issues ALTER COLUMN priority SET DEFAULT '1';
