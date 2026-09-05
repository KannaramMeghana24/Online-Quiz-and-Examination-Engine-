package com.team7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.team7.model.User;
import com.team7.util.DBConnection;

public class UserDAO {

    public User login(String username, String password) {

        User user = null;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE username=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return user;

    }

    /**
     * Registers a new user. "subject" is only meaningful for TEACHER
     * accounts (pass null for STUDENT/ADMIN).
     *
     * Returns true on success, false if the username already exists
     * or an error occurred.
     */
    public boolean register(String username, String password, String role, String subject) {

        if (isUsernameTaken(username)) {
            return false;
        }

        boolean success = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO users (username, password, role, subject) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            if (subject == null || subject.isBlank()) {
                ps.setNull(4, java.sql.Types.VARCHAR);
            } else {
                ps.setString(4, subject);
            }

            int rows = ps.executeUpdate();
            success = rows > 0;

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return success;
    }

    public boolean isUsernameTaken(String username) {

        boolean taken = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT user_id FROM users WHERE username=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            taken = rs.next();

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return taken;
    }
}
