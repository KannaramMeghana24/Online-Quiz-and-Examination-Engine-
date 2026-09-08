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
 
        String quizTitle = request.getParameter("quizTitle");
 
        if (quizTitle == null || quizTitle.isBlank()) {

            response.getWriter().println("<h2>Quiz title is required. Please go back and enter one.</h2>");

            return;

        }
 
        int total;

        try {

            total = Integer.parseInt(request.getParameter("totalQuestions"));

        } catch (NumberFormatException e) {

            total = 15; // fallback matching the JSP's fixed batch size

        }
 
        Question[] batch = new Question[total];
 
        // First pass: validate every question in the batch is fully filled

        // before writing anything to the DB, so a partial batch never gets

        // saved (the JSP's disabled-submit already prevents this client-side,

        // this is the server-side backstop).

        for (int i = 1; i <= total; i++) {
 
            String questionText = request.getParameter("questionText_" + i);

            String optionA = request.getParameter("optionA_" + i);

            String optionB = request.getParameter("optionB_" + i);

            String optionC = request.getParameter("optionC_" + i);

            String optionD = request.getParameter("optionD_" + i);

            String correctOption = request.getParameter("correctOption_" + i);
 
            if (isBlank(questionText) || isBlank(optionA) || isBlank(optionB)

                    || isBlank(optionC) || isBlank(optionD) || isBlank(correctOption)) {
 
                response.getWriter().println(

                    "<h2>Question " + i + " is incomplete. Please go back and fill in every question.</h2>");

                return;

            }
 
            Question q = new Question();

            q.setSubject(user.getSubject());

            q.setQuizTitle(quizTitle);

            q.setQuestionText(questionText);

            q.setOptionA(optionA);

            q.setOptionB(optionB);

            q.setOptionC(optionC);

            q.setOptionD(optionD);

            q.setCorrectOption(correctOption.toUpperCase());
 
            batch[i - 1] = q;

        }
 
        // Second pass: everything validated, now actually insert

        QuestionDAO dao = new QuestionDAO();

        int savedCount = 0;
 
        for (Question q : batch) {

            if (dao.addQuestion(q)) {

                savedCount++;

            }

        }
 
        if (savedCount == total) {

            response.sendRedirect("teacher/addQuestion.jsp?added=true");

        } else {

            response.getWriter().println(

                "<h2>Only " + savedCount + " of " + total + " questions were saved. Please check and retry the rest.</h2>");

        }

    }
 
    private boolean isBlank(String s) {

        return s == null || s.isBlank();

    }

}
 