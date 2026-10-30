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

@WebServlet("/Missions")
public class Missions extends HttpServlet {
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

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>CodeQuest Missions</title>");

        out.println("<style>");
        out.println("*{box-sizing:border-box}");
        out.println("body{margin:0;font-family:Arial,sans-serif;background:#10111f;color:white}");
        out.println("header{background:#1c1d32;padding:20px 8%;display:flex;justify-content:space-between;align-items:center}");
        out.println("header h2{color:#a78bfa}");
        out.println("a{color:#c4b5fd;text-decoration:none}");
        out.println(".container{max-width:900px;margin:35px auto;padding:20px}");
        out.println(".mission{background:#1c1d32;border:1px solid #393953;padding:25px;margin:20px 0;border-radius:12px}");
        out.println(".mission h2{color:#a78bfa}");
        out.println(".xp{color:#86efac;font-weight:bold}");
        out.println(".button{display:inline-block;background:#8b5cf6;color:white;padding:11px 18px;border-radius:8px;margin-top:10px}");
        out.println(".button:hover{background:#6d28d9}");
        out.println("</style></head><body>");

        out.println("<header><h2>⚔ CodeQuest</h2>");
        out.println("<a href='Dash'>Dashboard</a></header>");
        out.println("<div class='container'><h1>Available Missions</h1>");
        out.println("<p>Choose a mission and test your programming knowledge.</p>");

        String sql = "SELECT id, title, question, xp_reward FROM missions ORDER BY id";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                int id = rs.getInt("id");
                String title = escape(rs.getString("title"));
                String question = escape(rs.getString("question"));
                int xp = rs.getInt("xp_reward");

                out.println("<div class='mission'>");
                out.println("<h2>" + title + "</h2>");
                out.println("<p>" + question + "</p>");
                out.println("<p class='xp'>Reward: " + xp + " XP</p>");
                out.println("<a class='button' href='Solve?id=" + id
                        + "'>Start Mission</a>");
                out.println("</div>");
            }

            if (!found) {
                out.println("<div class='mission'>");
                out.println("<h2>No missions available yet</h2>");
                out.println("<p>The admin needs to add missions first.</p>");
                out.println("</div>");
            }

        } catch (Exception e) {
            throw new ServletException("Unable to load missions.", e);
        }

        out.println("</div></body></html>");
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