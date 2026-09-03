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
}