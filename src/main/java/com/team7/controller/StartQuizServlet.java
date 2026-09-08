package com.team7.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.team7.dao.QuestionDAO;
import com.team7.model.Question;
import com.team7.model.QuizSession;
import com.team7.model.User;

@WebServlet("/StartQuizServlet")
public class StartQuizServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int QUESTIONS_PER_QUIZ = 5;
    private static final int DURATION_SECONDS = 300; // 5 minutes

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.getRole().equalsIgnoreCase("STUDENT")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String subject = request.getParameter("subject");

        if (subject == null || subject.isBlank()) {
            response.sendRedirect("student/home.jsp?error=noSubject");
            return;
        }

        QuestionDAO dao = new QuestionDAO();
        List<Question> questions = dao.getRandomQuestions(subject, QUESTIONS_PER_QUIZ);

        if (questions.isEmpty()) {
            response.sendRedirect("student/home.jsp?error=noQuestions");
            return;
        }

        QuizSession quizSession = new QuizSession(subject, questions, DURATION_SECONDS);
        session.setAttribute("quizSession", quizSession);

        response.sendRedirect("student/quiz.jsp");
    }
}
