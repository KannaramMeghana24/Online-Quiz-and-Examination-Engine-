package com.team7.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.team7.dao.ResultDAO;
import com.team7.model.Question;
import com.team7.model.QuizSession;
import com.team7.model.User;

@WebServlet("/SubmitQuizServlet")
public class SubmitQuizServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");

        if (user == null || !user.getRole().equalsIgnoreCase("STUDENT")) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (quizSession == null) {
            // Already submitted / session lost - nothing to grade
            response.sendRedirect("student/home.jsp");
            return;
        }

        // Server-side timestamp check: reject a submission that arrives
        // well past the allotted time, so a client can't fake extra time
        // by editing/pausing the JavaScript timer.
        if (quizSession.isExpired()) {

            session.removeAttribute("quizSession");
            response.getWriter().println(
                "<h2>Time expired. Your submission was not accepted.</h2>"
                + "<a href='student/home.jsp'>Back to Home</a>");
            return;
        }

        // Grade against the answer key that was pulled at quiz-start time
        // (kept in session), not a fresh DB query, so we're scoring exactly
        // what the student was shown.
        int score = 0;
        int total = quizSession.getQuestions().size();

        for (Question q : quizSession.getQuestions()) {

            String submitted = request.getParameter("q_" + q.getQuestionId());

            if (submitted != null && submitted.equalsIgnoreCase(q.getCorrectOption())) {
                score++;
            }
        }

        ResultDAO resultDAO = new ResultDAO();
        resultDAO.saveResult(user.getUserId(), quizSession.getSubject(), score, total);

        // Quiz attempt is over - clear it so refreshing/back-button can't resubmit
        session.removeAttribute("quizSession");

        session.setAttribute("lastScore", score);
        session.setAttribute("lastTotal", total);
        session.setAttribute("lastSubject", quizSession.getSubject());

        response.sendRedirect("student/result.jsp");
    }
}
