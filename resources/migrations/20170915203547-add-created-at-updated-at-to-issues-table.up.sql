ALTER TABLE issues ADD created_at timestamp DEFAULT NOW();
ALTER TABLE issues ADD updated_at timestamp DEFAULT NOW();
