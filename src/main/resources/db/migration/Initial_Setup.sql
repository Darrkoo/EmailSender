--#SET TERMINATOR ;

CREATE TABLE email_template (
                                id RAW(16) PRIMARY KEY,
                                subject VARCHAR2(255) NOT NULL,
                                body CLOB NOT NULL,
                                frequency VARCHAR2(50) NOT NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE email_schedule (
                                id RAW(16) PRIMARY KEY,
                                template_id RAW(16) REFERENCES email_template(id),
                                scheduled_time TIMESTAMP NOT NULL,
                                status VARCHAR2(50) NOT NULL
);

CREATE TABLE email_log (
                           id RAW(16) PRIMARY KEY,
                           template_id RAW(16) REFERENCES email_template(id),
                           sent_time TIMESTAMP NOT NULL,
                           status VARCHAR2(50) NOT NULL
);
