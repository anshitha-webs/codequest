package codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/Dash")
public class dashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");

        int xp = 0;
        int level = 1;

        String sql = "SELECT xp, level FROM users WHERE id = ?";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    xp = rs.getInt("xp");
                    level = rs.getInt("level");
                }
            }

        } catch (Exception e) {
            throw new ServletException("Unable to load dashboard.", e);
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>CodeQuest Dashboard</title>");

        out.println("<style>");
        out.println("*{box-sizing:border-box}");
        out.println("body{margin:0;font-family:Arial,sans-serif;background:#10111f;color:white}");
        out.println("header{background:#1c1d32;padding:20px 8%;display:flex;justify-content:space-between;align-items:center;flex-wrap:wrap}");
        out.println("header h2{color:#a78bfa;margin:0}");
        out.println("a{color:#c4b5fd;text-decoration:none}");
        out.println(".container{max-width:1000px;margin:40px auto;padding:20px}");
        out.println(".welcome{background:#1c1d32;padding:30px;border-radius:15px}");
        out.println(".cards{display:grid;grid-template-columns:repeat(auto-fit,minmax(200px,1fr));gap:20px;margin-top:25px}");
        out.println(".card{background:#1c1d32;padding:25px;border-radius:12px;border:1px solid #393953}");
        out.println(".card h3{color:#a78bfa}");
        out.println(".number{font-size:32px;font-weight:bold}");
        out.println(".button{display:inline-block;background:#8b5cf6;color:white;padding:12px 20px;border-radius:8px;margin-top:10px}");
        out.println(".button:hover{background:#6d28d9}");
        out.println("</style></head><body>");

        out.println("<header>");
        out.println("<h2>⚔ CodeQuest</h2>");
        out.println("<a href='Logout'>Logout</a>");
        out.println("</header>");

        out.println("<div class='container'>");
        out.println("<div class='welcome'>");
        out.println("<h1>Welcome, " + escape(username) + "!</h1>");
        out.println("<p>Your coding adventure starts here. Complete missions and earn XP.</p>");
        out.println("<a class='button' href='Missions'>Explore Missions</a>");
        out.println("</div>");

        out.println("<div class='cards'>");
        out.println("<div class='card'><h3>Total XP</h3>");
        out.println("<div class='number'>" + xp + "</div></div>");
        out.println("<div class='card'><h3>Current Level</h3>");
        out.println("<div class='number'>" + level + "</div></div>");
        out.println("<div class='card'><h3>Leaderboard</h3>");
        out.println("<p>Compare your progress with other students.</p>");
        out.println("<a class='button' href='Board'>View Leaderboard</a></div>");
        out.println("</div></div></body></html>");
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}