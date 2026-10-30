package codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/Reg")
public class Reg extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (username == null || email == null || password == null
                || username.trim().isEmpty()
                || email.trim().isEmpty()
                || password.isEmpty()) {

            response.getWriter().println(
                "<h3>Please fill in all fields.</h3>"
                + "<a href='reg.jsp'>Go Back</a>");
            return;
        }

        username = username.trim();
        email = email.trim();

        if (password.length() < 8) {
            response.getWriter().println(
                "<h3>Password must contain at least 8 characters.</h3>"
                + "<a href='reg.jsp'>Go Back</a>");
            return;
        }

        String sql = "INSERT INTO users "
                   + "(username, email, password, xp, level) "
                   + "VALUES (?, ?, ?, 0, 1)";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, hashPassword(password));

            ps.executeUpdate();

            response.getWriter().println(
                "<h2>Registration successful!</h2>"
                + "<p>Your account starts at Level 1 with 0 XP.</p>"
                + "<a href='login.jsp'>Login Now</a>");

        } catch (SQLException e) {

            if (e.getErrorCode() == 1062) {
                response.getWriter().println(
                    "<h3>Username or email already exists.</h3>"
                    + "<a href='reg.jsp'>Try Again</a>");
            } else {
                throw new ServletException(
                    "Unable to register the student.", e);
            }
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(
                password.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();

            for (byte b : hash) {
                hex.append(String.format("%02x", b & 0xff));
            }

            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                "Password hashing is unavailable.", e);
        }
    }
}