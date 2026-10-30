package codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/ALogin")
public class Alogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null
                || username.trim().isEmpty() || password.isEmpty()) {
            response.sendRedirect("admin-login.jsp?error=empty");
            return;
        }

        String sql = "SELECT id, username FROM admins "
                   + "WHERE username = ? AND password = ?";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("adminId", rs.getInt("id"));
                    session.setAttribute("adminName", rs.getString("username"));

                    response.sendRedirect("AdminDash");
                    return;
                }
            }

            response.sendRedirect("admin-login.jsp?error=invalid");

        } catch (Exception e) {
            throw new ServletException("Admin login failed.", e);
        }
    }
}