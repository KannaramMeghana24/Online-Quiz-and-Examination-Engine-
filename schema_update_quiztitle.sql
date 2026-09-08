-- ============================================================
-- Incremental update #2: run this ONCE. Does not drop/recreate
-- anything - existing data (including quiz_answers from update #1)
-- is untouched.
--
-- Adds quiz_title so a teacher's batch of 15 questions can be
-- named (e.g. "Quiz 1", "Photosynthesis Test"), and so a student's
-- result row remembers which named quiz they took.
-- ============================================================

ALTER TABLE questions     ADD quiz_title VARCHAR2(100);
ALTER TABLE quiz_results  ADD quiz_title VARCHAR2(100);

COMMIT;
