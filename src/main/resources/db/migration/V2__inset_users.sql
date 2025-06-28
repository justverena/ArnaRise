INSERT INTO users (id, name, email, password, role_id, is_admin)
VALUES (
           uuid_generate_v4(),
           'Analyst',
           'analyst@example.com',
           '$2a$10$r/4R5IseWBv0dQrXhR3VHeYc/jZdxXG50lmU6LNHOdtTEKRxTiuYO',
           1,
           false
       );
