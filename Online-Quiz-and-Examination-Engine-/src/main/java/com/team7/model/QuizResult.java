package com.team7.model;

import java.sql.Timestamp;

public class QuizResult {

    private int resultId;
    private int userId;
    private String subject;
    private int score;
    private int totalQuestions;
    private Timestamp completedAt;

    // Not a DB column — populated only when ResultDAO joins with users,
    // so teacher/dashboard.jsp can display which student a row belongs to.
    private String studentUsername;

    public QuizResult() {
    }

    public QuizResult(int resultId, int userId, String subject, int score,
                      int totalQuestions, Timestamp completedAt) {

        this.resultId = resultId;
        this.userId = userId;
        this.subject = subject;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }
}
