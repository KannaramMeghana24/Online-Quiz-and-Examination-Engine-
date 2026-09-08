-- ============================================================
-- Incremental update: run this ONCE against your existing DB.
-- Does NOT drop/recreate anything - your existing users,
-- questions, and quiz_results data is untouched.
--
-- Stores each individual answer a student picked, tied to the
-- quiz_results row it belongs to, so the history page can show
-- a full review: "you answered B, correct answer was C".
-- ============================================================

CREATE TABLE quiz_answers (
    answer_id        NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    result_id        NUMBER NOT NULL,
    question_id      NUMBER NOT NULL,
    selected_option  CHAR(1),
    CONSTRAINT fk_qa_result   FOREIGN KEY (result_id)   REFERENCES quiz_results(result_id),
    CONSTRAINT fk_qa_question FOREIGN KEY (question_id) REFERENCES questions(question_id)
);

COMMIT;
