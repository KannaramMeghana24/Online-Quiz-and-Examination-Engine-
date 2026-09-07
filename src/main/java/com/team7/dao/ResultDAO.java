package com.team7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.team7.model.QuestionReview;
import com.team7.model.QuizResult;
import com.team7.util.DBConnection;

public class ResultDAO {

    /**
     * Used by SubmitQuizServlet to log a completed attempt.
     * Returns the generated result_id (needed so each individual answer can
     * be linked to this attempt via saveAnswer), or -1 on failure.
     */
    public int saveResult(int userId, String subject, int score, int totalQuestions) {

        int resultId = -1;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO quiz_results (user_id, subject, score, total_questions) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, new String[] {"result_id"});
            ps.setInt(1, userId);
            ps.setString(2, subject);
            ps.setInt(3, score);
            ps.setInt(4, totalQuestions);

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                resultId = keys.getInt(1);
            }
            keys.close();

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultId;
    }

    /**
     * Used by SubmitQuizServlet - logs one question's answer against a
     * completed attempt, so the student can review it later.
     * selectedOption may be null if the student left that question blank.
     */
    public boolean saveAnswer(int resultId, int questionId, String selectedOption) {

        boolean success = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO quiz_answers (result_id, question_id, selected_option) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, resultId);
            ps.setInt(2, questionId);

            if (selectedOption == null) {
                ps.setNull(3, java.sql.Types.CHAR);
            } else {
                ps.setString(3, selectedOption);
            }

            success = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Used by student/review.jsp - every question in a past attempt, with
     * the student's chosen answer and the correct answer side by side.
     */
    public List<QuestionReview> getReviewForResult(int resultId) {

        List<QuestionReview> reviews = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT q.question_text, q.option_a, q.option_b, q.option_c, q.option_d, "
                       + "q.correct_option, a.selected_option "
                       + "FROM quiz_answers a "
                       + "JOIN questions q ON a.question_id = q.question_id "
                       + "WHERE a.result_id = ? "
                       + "ORDER BY a.answer_id";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, resultId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                QuestionReview r = new QuestionReview();
                r.setQuestionText(rs.getString("question_text"));
                r.setOptionA(rs.getString("option_a"));
                r.setOptionB(rs.getString("option_b"));
                r.setOptionC(rs.getString("option_c"));
                r.setOptionD(rs.getString("option_d"));
                r.setCorrectOption(rs.getString("correct_option"));
                r.setSelectedOption(rs.getString("selected_option"));

                reviews.add(r);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }

    /**
     * Used by teacher/dashboard.jsp - every student's result for the
     * teacher's subject, most recent first, joined with username so
     * the JSP doesn't need a second lookup per row.
     */
    public List<QuizResult> getResultsBySubject(String subject) {

        List<QuizResult> results = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT r.result_id, r.user_id, r.subject, r.score, "
                       + "r.total_questions, r.completed_at, u.username "
                       + "FROM quiz_results r "
                       + "JOIN users u ON r.user_id = u.user_id "
                       + "WHERE r.subject = ? "
                       + "ORDER BY r.completed_at DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, subject);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                QuizResult qr = new QuizResult();
                qr.setResultId(rs.getInt("result_id"));
                qr.setUserId(rs.getInt("user_id"));
                qr.setSubject(rs.getString("subject"));
                qr.setScore(rs.getInt("score"));
                qr.setTotalQuestions(rs.getInt("total_questions"));
                qr.setCompletedAt(rs.getTimestamp("completed_at"));
                qr.setStudentUsername(rs.getString("username"));

                results.add(qr);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * Used by student/history.jsp - a single student's own past attempts.
     */
    public List<QuizResult> getResultsByUser(int userId) {

        List<QuizResult> results = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM quiz_results WHERE user_id = ? ORDER BY completed_at DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                QuizResult qr = new QuizResult();
                qr.setResultId(rs.getInt("result_id"));
                qr.setUserId(rs.getInt("user_id"));
                qr.setSubject(rs.getString("subject"));
                qr.setScore(rs.getInt("score"));
                qr.setTotalQuestions(rs.getInt("total_questions"));
                qr.setCompletedAt(rs.getTimestamp("completed_at"));

                results.add(qr);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
}
