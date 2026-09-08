-- ============================================================
-- Online Quiz & Examination Engine - Database Schema (Oracle)
-- Simplified 3-role model: ADMIN / TEACHER / STUDENT
-- A teacher owns exactly one subject; a student's quiz result
-- records the subject it was taken in, so a teacher can filter
-- results directly without needing a separate quizzes table.
-- ============================================================

-- Drop order matters because of the FK on quiz_results
DROP TABLE quiz_results;
DROP TABLE questions;
DROP TABLE users;

-- ------------------------------------------------------------
-- USERS
-- role: 'ADMIN' | 'TEACHER' | 'STUDENT'
-- subject: only populated for TEACHER rows (the subject they own)
-- ------------------------------------------------------------
CREATE TABLE users (
    user_id     NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username    VARCHAR2(50)  NOT NULL UNIQUE,
    password    VARCHAR2(255) NOT NULL,
    role        VARCHAR2(10)  NOT NULL,
    subject     VARCHAR2(50),
    CONSTRAINT chk_users_role CHECK (role IN ('ADMIN','TEACHER','STUDENT'))
);

-- ------------------------------------------------------------
-- QUESTIONS (flat pool, tagged by subject)
-- ------------------------------------------------------------
CREATE TABLE questions (
    question_id     NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    subject         VARCHAR2(50),
    question_text   CLOB          NOT NULL,
    option_a        VARCHAR2(255) NOT NULL,
    option_b        VARCHAR2(255) NOT NULL,
    option_c        VARCHAR2(255) NOT NULL,
    option_d        VARCHAR2(255) NOT NULL,
    correct_option  CHAR(1)       NOT NULL,
    CONSTRAINT chk_questions_correct CHECK (correct_option IN ('A','B','C','D'))
);

-- ------------------------------------------------------------
-- QUIZ_RESULTS
-- subject is stored here too, so a teacher's "view my students'
-- marks" query is a single filter: WHERE subject = ?
-- ------------------------------------------------------------
CREATE TABLE quiz_results (
    result_id        NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id          NUMBER NOT NULL,
    subject          VARCHAR2(50),
    score            NUMBER NOT NULL,
    total_questions  NUMBER NOT NULL,
    completed_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_quizresults_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- ------------------------------------------------------------
-- Seed data (test accounts — passwords are plaintext for now,
-- matching the existing UserDAO.login() implementation)
-- ------------------------------------------------------------
INSERT INTO users (username, password, role, subject) VALUES ('admin1',   'admin123',   'ADMIN',   NULL);
INSERT INTO users (username, password, role, subject) VALUES ('teacher1', 'teach123',   'TEACHER', 'Mathematics');
INSERT INTO users (username, password, role, subject) VALUES ('teacher2', 'teach123',   'TEACHER', 'Science');
INSERT INTO users (username, password, role, subject) VALUES ('student1', 'student123', 'STUDENT', NULL);
INSERT INTO users (username, password, role, subject) VALUES ('student2', 'student123', 'STUDENT', NULL);

INSERT INTO questions (subject, question_text, option_a, option_b, option_c, option_d, correct_option)
VALUES ('Mathematics', '2 + 2 = ?', '3', '4', '5', '6', 'B');

INSERT INTO questions (subject, question_text, option_a, option_b, option_c, option_d, correct_option)
VALUES ('Mathematics', 'Square root of 81?', '7', '8', '9', '10', 'C');

INSERT INTO questions (subject, question_text, option_a, option_b, option_c, option_d, correct_option)
VALUES ('Science', 'Water''s chemical formula?', 'CO2', 'H2O', 'O2', 'NaCl', 'B');

COMMIT;
