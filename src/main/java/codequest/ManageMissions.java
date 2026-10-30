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

@WebServlet("/ManageMissions")
public class ManageMissions extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adminId") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Manage Missions - CodeQuest</title>");

        out.println("<style>");
        out.println("*{box-sizing:border-box}");
        out.println("body{margin:0;padding:25px;font-family:Arial,sans-serif;background:#10111f;color:white}");
        out.println(".container{max-width:1100px;margin:25px auto;background:#1c1d32;padding:25px;border-radius:15px;overflow-x:auto}");
        out.println("h1{color:#a78bfa}");
        out.println("table{width:100%;border-collapse:collapse;margin-top:20px}");
        out.println("th,td{padding:12px;text-align:left;border-bottom:1px solid #393953}");
        out.println("th{background:#292a43;color:#c4b5fd}");
        out.println(".button{display:inline-block;padding:11px 17px;background:#8b5cf6;color:white;text-decoration:none;border-radius:8px;margin:8px 5px 8px 0}");
        out.println(".button:hover{background:#6d28d9}");
        out.println(".message{color:#86efac}");
        out.println("</style></head><body>");

        out.println("<div class='container'>");
        out.println("<h1>Manage Missions</h1>");
        out.println("<p>View the programming questions stored in your database.</p>");

        if ("success".equals(request.getParameter("added"))) {
            out.println("<p class='message'>Mission added successfully!</p>");
        }

        out.println("<a class='button' href='add-mission.jsp'>+ Add Mission</a>");
        out.println("<a class='button' href='AdminDash'>Back to Dashboard</a>");

        out.println("<table>");
        out.println("<tr><th>ID</th><th>Title</th><th>Question</th>");
        out.println("<th>Correct Option</th><th>XP Reward</th></tr>");

        String sql = "SELECT id, title, question, correct_answer, xp_reward "
                   + "FROM missions ORDER BY id DESC";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + escape(rs.getString("title")) + "</td>");
                out.println("<td>" + escape(rs.getString("question")) + "</td>");
                out.println("<td>Option " + rs.getInt("correct_answer") + "</td>");
                out.println("<td>" + rs.getInt("xp_reward") + "</td>");
                out.println("</tr>");
            }

            if (!found) {
                out.println("<tr><td colspan='5'>No missions added yet.</td></tr>");
            }

        } catch (Exception e) {
            throw new ServletException("Unable to load missions.", e);
        }

        out.println("</table></div></body></html>");
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