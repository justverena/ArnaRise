CREATE TABLE "user" (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255),
                        email VARCHAR(255) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        role_id INTEGER REFERENCES role(id)
);

CREATE TABLE analyst (
                         user_id INTEGER PRIMARY KEY REFERENCES "user"(id)
);

CREATE TABLE partner (
                         user_id INTEGER PRIMARY KEY REFERENCES "user"(id),
                         organization_name VARCHAR(255)
);
