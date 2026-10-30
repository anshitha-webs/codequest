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

@WebServlet("/Solve")
public class Solve extends HttpServlet {
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

        int missionId;

        try {
            missionId = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect("Missions");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Solve Mission - CodeQuest</title>");

        out.println("<style>");
        out.println("*{box-sizing:border-box}");
        out.println("body{margin:0;font-family:Arial,sans-serif;background:#10111f;color:white;padding:25px}");
        out.println(".box{max-width:650px;margin:40px auto;background:#1c1d32;padding:30px;border-radius:15px}");
        out.println("h1{color:#a78bfa}");
        out.println(".option{display:block;margin:15px 0;padding:12px;background:#292a43;border-radius:8px}");
        out.println("input[type=radio]{margin-right:10px}");
        out.println("button{background:#8b5cf6;color:white;border:0;padding:12px 22px;border-radius:8px;cursor:pointer}");
        out.println("a{color:#c4b5fd;text-decoration:none}");
        out.println("</style></head><body>");

        out.println("<div class='box'>");

        String sql = "SELECT title, question, option1, option2, "
                   + "option3, option4 FROM missions WHERE id = ?";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, missionId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    out.println("<h2>Mission not found.</h2>");
                    out.println("<a href='Missions'>Back to Missions</a>");
                } else {
                    out.println("<h1>" + escape(rs.getString("title")) + "</h1>");
                    out.println("<p>" + escape(rs.getString("question")) + "</p>");

                    out.println("<form action='Answer' method='post'>");
                    out.println("<input type='hidden' name='missionId' value='"
                            + missionId + "'>");

                    for (int i = 1; i <= 4; i++) {
                        String option = rs.getString("option" + i);

                        if (option != null && !option.trim().isEmpty()) {
                            out.println("<label class='option'>");
                            out.println("<input type='radio' name='answer' value='"
                                    + i + "' required>");
                            out.println(escape(option));
                            out.println("</label>");
                        }
                    }

                    out.println("<button type='submit'>Submit Answer</button>");
                    out.println("</form>");
                }
            }

        } catch (Exception e) {
            throw new ServletException("Unable to open the mission.", e);
        }

        out.println("<p><a href='Missions'>Back to Missions</a></p>");
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