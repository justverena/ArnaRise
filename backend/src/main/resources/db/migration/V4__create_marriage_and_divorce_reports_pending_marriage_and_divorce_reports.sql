CREATE TABLE pending_marriage_and_divorce_reports
(
    id                                 UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    report_year                        INTEGER      NOT NULL,
    district                           VARCHAR(50)  NOT NULL,
    marriage_count                     INTEGER      NOT NULL,
    divorce_count                      INTEGER      NOT NULL,
    ratio_divorce_to_marriage_percent NUMERIC(5, 2) GENERATED ALWAYS AS
        ((divorce_count::numeric / NULLIF(marriage_count, 0)) * 100) STORED,
    average_age                        NUMERIC(4, 1),
    source                             VARCHAR(100) NOT NULL,
    submitted_by                       UUID         NOT NULL REFERENCES users (id) ON DELETE CASCADE,

    status                             VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    rejection_reason                   TEXT,

    created_at                         TIMESTAMP             DEFAULT now(),
    updated_at                         TIMESTAMP             DEFAULT now()
);


CREATE TABLE marriage_and_divorce_reports
(
    id                                 UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    report_year                        INTEGER      NOT NULL,
    district                           VARCHAR(50)  NOT NULL,
    marriage_count                     INTEGER      NOT NULL,
    divorce_count                      INTEGER      NOT NULL,
    ratio_divorce_to_marriage_percent NUMERIC(5, 2) GENERATED ALWAYS AS
        ((divorce_count::numeric / NULLIF(marriage_count, 0)) * 100) STORED,
    average_age                        NUMERIC(4, 1),
    source                             VARCHAR(100) NOT NULL,
    submitted_by                       UUID         NOT NULL REFERENCES users (id) ON DELETE CASCADE,

    status                             VARCHAR(20)  NOT NULL DEFAULT 'PENDING',

    created_at                         TIMESTAMP             DEFAULT now(),
    updated_at                         TIMESTAMP             DEFAULT now()
);

