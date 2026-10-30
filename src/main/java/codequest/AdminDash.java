package codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AdminDash")
public class AdminDash extends HttpServlet {
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
        out.println("<title>Admin Dashboard - CodeQuest</title>");

        out.println("<style>");
        out.println("*{box-sizing:border-box}");
        out.println("body{margin:0;font-family:Arial,sans-serif;background:#10111f;color:white}");
        out.println("header{background:#1c1d32;padding:20px 8%;display:flex;justify-content:space-between;align-items:center;flex-wrap:wrap}");
        out.println("header h2{color:#a78bfa;margin:0}");
        out.println(".container{max-width:950px;margin:40px auto;padding:20px}");
        out.println(".welcome{background:#1c1d32;padding:30px;border-radius:15px}");
        out.println("h1{color:#a78bfa}");
        out.println(".cards{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:20px;margin-top:25px}");
        out.println(".card{background:#1c1d32;padding:25px;border:1px solid #393953;border-radius:12px}");
        out.println(".card h3{color:#c4b5fd}");
        out.println(".button{display:inline-block;background:#8b5cf6;color:white;text-decoration:none;padding:12px 18px;border-radius:8px;margin-top:10px}");
        out.println(".button:hover{background:#6d28d9}");
        out.println("a{color:#c4b5fd;text-decoration:none}");
        out.println("</style></head><body>");

        out.println("<header>");
        out.println("<h2>CodeQuest Admin</h2>");
        out.println("<a href='AdminLogout'>Logout</a>");
        out.println("</header>");

        out.println("<div class='container'>");
        out.println("<div class='welcome'>");
        out.println("<h1>Welcome, "
                + escape((String) session.getAttribute("adminName")) + "!</h1>");
        out.println("<p>Manage programming missions for CodeQuest students.</p>");
        out.println("</div>");

        out.println("<div class='cards'>");

        out.println("<div class='card'>");
        out.println("<h3>Create Mission</h3>");
        out.println("<p>Add a new programming question with four options and an XP reward.</p>");
        out.println("<a class='button' href='add-mission.jsp'>Add Mission</a>");
        out.println("</div>");

        out.println("<div class='card'>");
        out.println("<h3>View Missions</h3>");
        out.println("<p>View the missions stored in your database.</p>");
        out.println("<a class='button' href='ManageMissions'>View Missions</a>");
        out.println("</div>");

        out.println("<div class='card'>");
        out.println("<h3>Student Leaderboard</h3>");
        out.println("<p>See student levels and experience points.</p>");
        out.println("<a class='button' href='Board'>Leaderboard</a>");
        out.println("</div>");

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