package com.team7.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.team7.dao.UserDAO;
import com.team7.model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

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

            else{

                response.sendRedirect("student/home.jsp");

            }

        }

        else{

            response.getWriter().println("<h2>Invalid Username or Password</h2>");

        }

    }

}