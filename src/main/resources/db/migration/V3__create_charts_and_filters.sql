CREATE TABLE chart (
                       id SERIAL PRIMARY KEY,
                       data JSONB,
                       type VARCHAR(100),
                       created_by INTEGER REFERENCES "user"(id)
);

CREATE TABLE filter (
                        id SERIAL PRIMARY KEY,
                        type VARCHAR(100),
                        value VARCHAR(255)
);

CREATE TABLE chart_filter (
                              chart_id INTEGER REFERENCES chart(id),
                              filter_id INTEGER REFERENCES filter(id),
                              PRIMARY KEY(chart_id, filter_id)
);
