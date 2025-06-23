CREATE TABLE template (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255),
                          fields JSONB,
                          created_by INTEGER REFERENCES analyst(user_id)
);

CREATE TABLE report (
                        id SERIAL PRIMARY KEY,
                        status VARCHAR(50),
                        created_by INTEGER REFERENCES "user"(id),
                        submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        fields JSONB,
                        template_id INTEGER REFERENCES template(id)
);
