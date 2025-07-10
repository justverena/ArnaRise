
CREATE TABLE pending_gender_violence_reports (
                                                 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                                 report_date DATE NOT NULL,
                                                 gender VARCHAR(10) NOT NULL,
                                                 district VARCHAR(50) NOT NULL,
                                                 age INTEGER NOT NULL,

                                                 violence_type VARCHAR(30) NOT NULL,
                                                 location VARCHAR(30) NOT NULL,
                                                 time_of_day VARCHAR(20) NOT NULL,
                                                 social_status VARCHAR(30) NOT NULL,
                                                 aggressor_relation VARCHAR(30) NOT NULL,

                                                 case_description TEXT,

                                                 submitted_by UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,

                                                 status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                                                 rejection_reason TEXT,

                                                 created_at TIMESTAMP DEFAULT now(),
                                                 updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE pending_gender_violence_report_actions (
                                                        report_id UUID REFERENCES pending_gender_violence_reports(id) ON DELETE CASCADE,
                                                        action VARCHAR(30) NOT NULL,

                                                        PRIMARY KEY (report_id, action)
);