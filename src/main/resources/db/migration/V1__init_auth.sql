CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE role (
                      id INTEGER PRIMARY KEY,
                      name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       name VARCHAR(255),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role_id INTEGER REFERENCES role(id),
                       is_admin BOOLEAN DEFAULT FALSE,
                       created_at TIMESTAMP DEFAULT now(),
                       updated_at TIMESTAMP DEFAULT now()
);

INSERT INTO role (id, name) VALUES (1, 'analyst'), (2, 'partner');

INSERT INTO users (id, name, email, password, is_admin)
VALUES (
           uuid_generate_v4(),
           'Admin',
           'admin@example.com',
           '$2a$10$r/4R5IseWBv0dQrXhR3VHeYc/jZdxXG50lmU6LNHOdtTEKRxTiuYO', --password: admin123, AdminPasswordHashTest
           true
       );

