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

     * Used by teacher/addQuestion.jsp -> AddQuestionServlet (one call per

     * question in the 15-question batch).

     */

    public boolean addQuestion(Question q) {
 
        boolean success = false;
 
        try {
 
            Connection con = DBConnection.getConnection();
 
            String sql = "INSERT INTO questions "

                       + "(subject, quiz_title, question_text, option_a, option_b, option_c, option_d, correct_option) "

                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
 
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, q.getSubject());

            ps.setString(2, q.getQuizTitle());

            ps.setString(3, q.getQuestionText());

            ps.setString(4, q.getOptionA());

            ps.setString(5, q.getOptionB());

            ps.setString(6, q.getOptionC());

            ps.setString(7, q.getOptionD());

            ps.setString(8, q.getCorrectOption());
 
            success = ps.executeUpdate() > 0;
 
            ps.close();

            con.close();
 
        } catch (Exception e) {

            e.printStackTrace();

        }
 
        return success;

    }
 
    /**

     * Used by EditQuestionServlet - overwrite an existing question's content.

     * subject/quiz_title are NOT editable here (they define which batch a

     * question belongs to); only the question content itself can change.

     */

    public boolean updateQuestion(Question q) {
 
        boolean success = false;
 
        try {
 
            Connection con = DBConnection.getConnection();
 
            String sql = "UPDATE questions SET question_text=?, option_a=?, option_b=?, "

                       + "option_c=?, option_d=?, correct_option=? WHERE question_id=?";
 
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, q.getQuestionText());

            ps.setString(2, q.getOptionA());

            ps.setString(3, q.getOptionB());

            ps.setString(4, q.getOptionC());

            ps.setString(5, q.getOptionD());

            ps.setString(6, q.getCorrectOption());

            ps.setInt(7, q.getQuestionId());
 
            success = ps.executeUpdate() > 0;
 
            ps.close();

            con.close();
 
        } catch (Exception e) {

            e.printStackTrace();

        }
 
        return success;

    }
 
    /**

     * Used by teacher/editQuestion.jsp to load one question into the edit form.

     */

    public Question getQuestionById(int questionId) {
 
        Question q = null;
 
        try {
 
            Connection con = DBConnection.getConnection();
 
            String sql = "SELECT * FROM questions WHERE question_id = ?";
 
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, questionId);
 
            ResultSet rs = ps.executeQuery();
 
            if (rs.next()) {

                q = mapRow(rs);

            }
 
            rs.close();

            ps.close();

            con.close();
 
        } catch (Exception e) {

            e.printStackTrace();

        }
 
        return q;

    }
 
    /**

     * Used by teacher/viewQuestions.jsp - every question the teacher has

     * uploaded under one named quiz (their subject + a specific quiz_title).

     */

    public List<Question> getQuestionsByQuizTitle(String subject, String quizTitle) {
 
        List<Question> list = new ArrayList<>();
 
        try {
 
            Connection con = DBConnection.getConnection();
 
            String sql = "SELECT * FROM questions WHERE subject = ? AND quiz_title = ? ORDER BY question_id";
 
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, subject);

            ps.setString(2, quizTitle);
 
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

     * Used by teacher/viewQuestions.jsp - every distinct quiz title this

     * teacher has created under their subject, so they can pick which one

     * to view/edit.

     */

    public List<String> getQuizTitlesBySubject(String subject) {
 
        List<String> titles = new ArrayList<>();
 
        try {
 
            Connection con = DBConnection.getConnection();
 
            String sql = "SELECT DISTINCT quiz_title FROM questions "

                       + "WHERE subject = ? AND quiz_title IS NOT NULL ORDER BY quiz_title";
 
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, subject);
 
            ResultSet rs = ps.executeQuery();
 
            while (rs.next()) {

                titles.add(rs.getString("quiz_title"));

            }
 
            rs.close();

            ps.close();

            con.close();
 
        } catch (Exception e) {

            e.printStackTrace();

        }
 
        return titles;

    }
 
    /**

     * Used by student/home.jsp - every (subject, quiz_title) pair that has

     * questions, so a student can pick a specific named quiz to take.

     */

    public List<String[]> getAvailableQuizzes() {
 
        List<String[]> quizzes = new ArrayList<>();
 
        try {
 
            Connection con = DBConnection.getConnection();
 
            String sql = "SELECT DISTINCT subject, quiz_title FROM questions "

                       + "WHERE quiz_title IS NOT NULL ORDER BY subject, quiz_title";
 
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
 
            while (rs.next()) {

                quizzes.add(new String[] { rs.getString("subject"), rs.getString("quiz_title") });

            }
 
            rs.close();

            ps.close();

            con.close();
 
        } catch (Exception e) {

            e.printStackTrace();

        }
 
        return quizzes;

    }
 
    /**

     * Used by StartQuizServlet - pull N random questions from one specific

     * named quiz (subject + quiz_title), not the whole subject's pool.

     * Oracle uses DBMS_RANDOM instead of MySQL's RAND().

     */

    public List<Question> getRandomQuestions(String subject, String quizTitle, int n) {
 
        List<Question> list = new ArrayList<>();
 
        try {
 
            Connection con = DBConnection.getConnection();
 
            String sql = "SELECT * FROM ("

                       + "  SELECT * FROM questions WHERE subject = ? AND quiz_title = ? "

                       + "  ORDER BY DBMS_RANDOM.VALUE"

                       + ") WHERE ROWNUM <= ?";
 
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, subject);

            ps.setString(2, quizTitle);

            ps.setInt(3, n);
 
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

        q.setQuizTitle(rs.getString("quiz_title"));

        q.setQuestionText(rs.getString("question_text"));

        q.setOptionA(rs.getString("option_a"));

        q.setOptionB(rs.getString("option_b"));

        q.setOptionC(rs.getString("option_c"));

        q.setOptionD(rs.getString("option_d"));

        q.setCorrectOption(rs.getString("correct_option"));
 
        return q;

    }

}
 