package com.team7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.team7.model.QuizResult;
import com.team7.util.DBConnection;

public class ResultDAO {

    /**
     * Used by StartQuizServlet/SubmitQuizServlet to log a completed attempt.
     */
    public boolean saveResult(int userId, String subject, int score, int totalQuestions) {

        boolean success = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO quiz_results (user_id, subject, score, total_questions) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, subject);
            ps.setInt(3, score);
            ps.setInt(4, totalQuestions);

            success = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Used by teacher/dashboard.jsp — every student's result for the
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
     * Used by student/history.jsp — a single student's own past attempts.
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
