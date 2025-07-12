CREATE TABLE gender_violence_reports (
                                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                         report_date DATE NOT NULL,
                                         gender VARCHAR(10) NOT NULL,
                                         district VARCHAR(50) NOT NULL,
                                         age INTEGER NOT NULL,

                                         violence_type VARCHAR(30) NOT NULL, -- enum
                                         location VARCHAR(30) NOT NULL,      -- enum
                                         time_of_day VARCHAR(20) NOT NULL,   -- enum
                                         social_status VARCHAR(30) NOT NULL, -- enum
                                         aggressor_relation VARCHAR(30) NOT NULL, -- enum

                                         case_description TEXT,
                                         measures_taken_by VARCHAR(30), -- enum

                                         submitted_by UUID REFERENCES users(id),
                                         status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                                         created_at TIMESTAMP DEFAULT now(),
                                         updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE gender_violence_report_actions (
                                                report_id UUID REFERENCES gender_violence_reports(id) ON DELETE CASCADE,
                                                action VARCHAR(30) NOT NULL,

                                                PRIMARY KEY (report_id, action)
);