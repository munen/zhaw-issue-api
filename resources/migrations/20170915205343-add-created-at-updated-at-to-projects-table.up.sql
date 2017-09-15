ALTER TABLE projects ADD created_at timestamp DEFAULT NOW();
ALTER TABLE projects ADD updated_at timestamp DEFAULT NOW();
