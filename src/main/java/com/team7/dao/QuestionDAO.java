package com.team7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.team7.model.Question;
import com.team7.util.DBConnection;

public class QuestionDAO {

    /**
     * Used by teacher/addQuestion.jsp -> AddQuestionServlet.
     */
    public boolean addQuestion(Question q) {

        boolean success = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO questions "
                       + "(subject, question_text, option_a, option_b, option_c, option_d, correct_option) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, q.getSubject());
            ps.setString(2, q.getQuestionText());
            ps.setString(3, q.getOptionA());
            ps.setString(4, q.getOptionB());
            ps.setString(5, q.getOptionC());
            ps.setString(6, q.getOptionD());
            ps.setString(7, q.getCorrectOption());

            success = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Used by teacher/viewQuestions.jsp - list all questions for this teacher's subject.
     */
    public List<Question> getQuestionsBySubject(String subject) {

        List<Question> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM questions WHERE subject = ? ORDER BY question_id DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, subject);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Used by StartQuizServlet - pull N random questions for the subject
     * the student is about to be quizzed on. Oracle uses DBMS_RANDOM
     * instead of MySQL's RAND().
     */
    public List<Question> getRandomQuestions(String subject, int n) {

        List<Question> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM ("
                       + "  SELECT * FROM questions WHERE subject = ? "
                       + "  ORDER BY DBMS_RANDOM.VALUE"
                       + ") WHERE ROWNUM <= ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, subject);
            ps.setInt(2, n);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Used by student/home.jsp - only show subjects that actually have
     * questions in the bank, so a student can't start an empty quiz.
     */
    public List<String> getDistinctSubjects() {

        List<String> subjects = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT DISTINCT subject FROM questions WHERE subject IS NOT NULL ORDER BY subject";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                subjects.add(rs.getString("subject"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return subjects;
    }

    public boolean deleteQuestion(int questionId) {

        boolean success = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM questions WHERE question_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, questionId);

            success = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    private Question mapRow(ResultSet rs) throws Exception {

        Question q = new Question();
        q.setQuestionId(rs.getInt("question_id"));
        q.setSubject(rs.getString("subject"));
        q.setQuestionText(rs.getString("question_text"));
        q.setOptionA(rs.getString("option_a"));
        q.setOptionB(rs.getString("option_b"));
        q.setOptionC(rs.getString("option_c"));
        q.setOptionD(rs.getString("option_d"));
        q.setCorrectOption(rs.getString("correct_option"));

        return q;
    }
}
