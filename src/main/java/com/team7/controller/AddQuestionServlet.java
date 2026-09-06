package com.team7.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.team7.dao.QuestionDAO;
import com.team7.model.Question;
import com.team7.model.User;

@WebServlet("/AddQuestionServlet")
public class AddQuestionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Only a logged-in teacher may add questions, and only to their own subject
        if (user == null || !user.getRole().equalsIgnoreCase("TEACHER")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String questionText = request.getParameter("questionText");
        String optionA = request.getParameter("optionA");
        String optionB = request.getParameter("optionB");
        String optionC = request.getParameter("optionC");
        String optionD = request.getParameter("optionD");
        String correctOption = request.getParameter("correctOption");

        if (questionText == null || questionText.isBlank()
                || optionA == null || optionA.isBlank()
                || optionB == null || optionB.isBlank()
                || optionC == null || optionC.isBlank()
                || optionD == null || optionD.isBlank()
                || correctOption == null || correctOption.isBlank()) {

            response.getWriter().println("<h2>All fields are required. Please go back and try again.</h2>");
            return;
        }

        Question q = new Question();
        q.setSubject(user.getSubject());
        q.setQuestionText(questionText);
        q.setOptionA(optionA);
        q.setOptionB(optionB);
        q.setOptionC(optionC);
        q.setOptionD(optionD);
        q.setCorrectOption(correctOption.toUpperCase());

        QuestionDAO dao = new QuestionDAO();
        boolean success = dao.addQuestion(q);

        if (success) {
            response.sendRedirect("teacher/addQuestion.jsp?added=true");
        } else {
            response.getWriter().println("<h2>Failed to add question.</h2>");
        }
    }
}
