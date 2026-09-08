package com.team7.controller;

import java.io.IOException;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 
import com.team7.dao.UserDAO;
 
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    // Institute enrollment ID format: "26" followed by exactly 3 digits (e.g. 26001-26999)
    private static final String STUDENT_ID_PATTERN = "26[0-9]{3}";
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");       // "STUDENT" or "TEACHER"
        String subject = request.getParameter("subject");  // only used when role=TEACHER
 
        // Basic validation
        if (username == null || username.isBlank()
                || password == null || password.isBlank()
                || role == null || role.isBlank()) {
 
            response.getWriter().println("<h2>All fields are required. Please go back and try again.</h2>");
            return;
        }
 
        if (!role.equalsIgnoreCase("STUDENT") && !role.equalsIgnoreCase("TEACHER")) {
            response.getWriter().println("<h2>Invalid role selected.</h2>");
            return;
        }
 
        // Server-side check: the HTML "pattern" attribute on register.jsp
        // is just a UX hint and can be bypassed (disabled JS, direct POST,
        // etc), so re-validate here before writing to the DB.
        if (role.equalsIgnoreCase("STUDENT") && !username.matches(STUDENT_ID_PATTERN)) {
            response.getWriter().println(
                "<h2>Invalid enrollment number. Username must start with 26 followed by 3 digits (e.g. 26001).</h2>");
            return;
        }
 
        UserDAO dao = new UserDAO();
 
        boolean success = dao.register(username, password, role.toUpperCase(), subject);
 
        if (success) {
            response.sendRedirect("login.jsp");
        } else {
            response.getWriter().println("<h2>Registration failed. Username may already be taken.</h2>");
        }
    }
}
