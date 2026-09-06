package com.team7.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.team7.dao.UserDAO;
import com.team7.model.User;

@WebServlet("/AddTeacherServlet")
public class AddTeacherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute("user");

        // Only a logged-in admin may create teacher accounts
        if (admin == null || !admin.getRole().equalsIgnoreCase("ADMIN")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String subject = request.getParameter("subject");

        if (username == null || username.isBlank()
                || password == null || password.isBlank()
                || subject == null || subject.isBlank()) {

            response.getWriter().println("<h2>All fields are required. Please go back and try again.</h2>");
            return;
        }

        UserDAO dao = new UserDAO();
        boolean success = dao.register(username, password, "TEACHER", subject);

        if (success) {
            response.sendRedirect("admin/addTeacher.jsp?added=true");
        } else {
            response.getWriter().println("<h2>Failed - username may already be taken.</h2>");
        }
    }
}
