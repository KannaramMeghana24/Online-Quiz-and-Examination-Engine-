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

@jakarta.servlet.annotation.WebServlet("/LoginServlet")
public class LoginServlet extends jakarta.servlet.http.HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");

        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();

        User user = dao.login(username, password);

        if(user != null){

            HttpSession session = request.getSession();

            session.setAttribute("user", user);

            if(user.getRole().equalsIgnoreCase("ADMIN")){

                response.sendRedirect("admin/dashboard.jsp");

            }

            else if(user.getRole().equalsIgnoreCase("TEACHER")){

                response.sendRedirect("teacher/dashboard.jsp");

            }

            else{

                response.sendRedirect("student/home.jsp");

            }

        }

        else{

            response.getWriter().println("<h2>Invalid Username or Password</h2>");

        }

    }

}
