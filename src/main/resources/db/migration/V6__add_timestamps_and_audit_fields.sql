ALTER TABLE report
    ADD COLUMN created_at TIMESTAMP DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMP DEFAULT now(),
    ADD COLUMN updated_by INTEGER REFERENCES "user"(id);

ALTER TABLE template
    ADD COLUMN created_at TIMESTAMP DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMP DEFAULT now(),
    ADD COLUMN updated_by INTEGER REFERENCES "user"(id);

ALTER TABLE indicator
    ADD COLUMN created_at TIMESTAMP DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMP DEFAULT now(),
    ADD COLUMN updated_by INTEGER REFERENCES "user"(id);

ALTER TABLE chart
    ADD COLUMN created_at TIMESTAMP DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMP DEFAULT now(),
    ADD COLUMN updated_by INTEGER REFERENCES "user"(id);
