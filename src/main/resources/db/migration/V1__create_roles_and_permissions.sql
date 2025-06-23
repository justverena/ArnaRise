CREATE TABLE role (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      permissions TEXT[]
);

CREATE TABLE role_permissions (
                                  role_id INTEGER REFERENCES role(id),
                                  permission TEXT
);
