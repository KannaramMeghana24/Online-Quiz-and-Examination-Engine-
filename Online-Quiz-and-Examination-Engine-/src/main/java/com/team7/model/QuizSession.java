package com.team7.model;

import java.util.List;

/**
 * Not persisted to the DB — lives only in the student's HttpSession
 * for the duration of one quiz attempt. Holds the exact questions
 * pulled (so grading checks against what was actually shown) and the
 * server-side start time (so the timer can't be tampered with from
 * the client — SubmitQuizServlet re-checks elapsed time against this).
 */
public class QuizSession {

    private String subject;
    private List<Question> questions;
    private long startTimeMillis;
    private int durationSeconds;

    public QuizSession(String subject, List<Question> questions, int durationSeconds) {
        this.subject = subject;
        this.questions = questions;
        this.durationSeconds = durationSeconds;
        this.startTimeMillis = System.currentTimeMillis();
    }

    public String getSubject() {
        return subject;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    /** Seconds remaining right now, based on server clock. */
    public int getSecondsRemaining() {
        long elapsedSeconds = (System.currentTimeMillis() - startTimeMillis) / 1000;
        int remaining = durationSeconds - (int) elapsedSeconds;
        return Math.max(remaining, 0);
    }

    /** True if the student took meaningfully longer than allowed (client tampering check). */
    public boolean isExpired() {
        long elapsedSeconds = (System.currentTimeMillis() - startTimeMillis) / 1000;
        // small grace period for network/render latency
        return elapsedSeconds > durationSeconds + 5;
    }
}
