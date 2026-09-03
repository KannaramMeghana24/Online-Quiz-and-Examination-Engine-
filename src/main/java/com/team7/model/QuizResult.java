package com.team7.model;

import java.sql.Timestamp;

public class QuizResult {

    private int resultId;
    private int userId;
    private int score;
    private int totalQuestions;
    private Timestamp completedAt;

    public QuizResult() {
    }

    public QuizResult(int resultId, int userId, int score,
                      int totalQuestions, Timestamp completedAt) {

        this.resultId = resultId;
        this.userId = userId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.completedAt = completedAt;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }
}