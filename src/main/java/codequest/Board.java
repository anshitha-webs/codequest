
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

@WebServlet("/Board")
public class Board extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check whether the student is logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Leaderboard - CodeQuest</title>");

            out.println("<style>");
            out.println("* { box-sizing: border-box; }");
            out.println("body { margin: 0; padding: 25px; background: #10111f; color: white; font-family: Arial, sans-serif; }");
            out.println(".container { max-width: 850px; margin: 30px auto; background: #1c1d32; padding: 25px; border-radius: 15px; }");
            out.println("h1 { color: #a78bfa; text-align: center; }");
            out.println("p { text-align: center; color: #ddd; }");
            out.println(".table-container { overflow-x: auto; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-top: 25px; }");
            out.println("th, td { padding: 14px; text-align: left; border-bottom: 1px solid #393953; }");
            out.println("th { background: #292a43; color: #c4b5fd; }");
            out.println("tr:hover { background: #292a43; }");
            out.println(".back { display: inline-block; margin-top: 20px; background: #8b5cf6; color: white; padding: 11px 18px; border-radius: 8px; text-decoration: none; }");
            out.println(".back:hover { background: #7c3aed; }");
            out.println("</style>");

            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");

            out.println("<h1>🏆 CodeQuest Leaderboard</h1>");
            out.println("<p>Keep learning, earn XP, and climb the rankings!</p>");

            out.println("<div class='table-container'>");
            out.println("<table>");
            out.println("<tr><th>Rank</th><th>Student</th><th>Level</th><th>XP</th></tr>");

            String sql =
                    "SELECT username, xp, level FROM users " +
                    "ORDER BY xp DESC, username ASC";

            try (Connection con = DB.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                int rank = 1;
                boolean found = false;

                while (rs.next()) {
                    found = true;

                    out.println("<tr>");
                    out.println("<td>" + rank + "</td>");
                    out.println("<td>" + escape(rs.getString("username")) + "</td>");
                    out.println("<td>" + rs.getInt("level") + "</td>");
                    out.println("<td>" + rs.getInt("xp") + "</td>");
                    out.println("</tr>");

                    rank++;
                }

                if (!found) {
                    out.println(
                        "<tr><td colspan='4'>No students registered yet.</td></tr>"
                    );
                }

            } catch (Exception e) {
                throw new ServletException(
                    "Unable to load leaderboard. Check DB.java and the users table.",
                    e
                );
            }

            out.println("</table>");
            out.println("</div>");

            out.println("<a class='back' href='" +
                    request.getContextPath() +
                    "/Dash'>Back to Dashboard</a>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    private String escape(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
