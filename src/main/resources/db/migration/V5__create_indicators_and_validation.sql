CREATE TABLE indicator (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255),
                           description TEXT
);

CREATE TABLE indicator_report (
                                  indicator_id INTEGER REFERENCES indicator(id),
                                  report_id INTEGER REFERENCES report(id),
                                  PRIMARY KEY(indicator_id, report_id)
);

CREATE TABLE validation_result (
                                   id SERIAL PRIMARY KEY,
                                   report_id INTEGER REFERENCES report(id),
                                   is_valid BOOLEAN,
                                   errors TEXT
);
