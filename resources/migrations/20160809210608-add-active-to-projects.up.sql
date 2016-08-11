ALTER TABLE projects ADD active boolean DEFAULT false;
UPDATE projects SET active = false;
