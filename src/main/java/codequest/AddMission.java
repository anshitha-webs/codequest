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

@WebServlet("/AddMission")
public class AddMission extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adminId") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String question = request.getParameter("question");
        String option1 = request.getParameter("option1");
        String option2 = request.getParameter("option2");
        String option3 = request.getParameter("option3");
        String option4 = request.getParameter("option4");
        String correct = request.getParameter("correctAnswer");
        String xpValue = request.getParameter("xp");

        if (isEmpty(title) || isEmpty(question)
                || isEmpty(option1) || isEmpty(option2)
                || isEmpty(option3) || isEmpty(option4)
                || isEmpty(correct) || isEmpty(xpValue)) {

            response.sendRedirect("add-mission.jsp?error=empty");
            return;
        }

        int correctAnswer;
        int xp;

        try {
            correctAnswer = Integer.parseInt(correct);
            xp = Integer.parseInt(xpValue);
        } catch (NumberFormatException e) {
            response.sendRedirect("add-mission.jsp?error=invalid");
            return;
        }

        if (correctAnswer < 1 || correctAnswer > 4 || xp < 1 || xp > 1000) {
            response.sendRedirect("add-mission.jsp?error=invalid");
            return;
        }

        String sql = "INSERT INTO missions "
                + "(title, question, option1, option2, option3, option4, "
                + "correct_answer, xp_reward) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title.trim());
            ps.setString(2, question.trim());
            ps.setString(3, option1.trim());
            ps.setString(4, option2.trim());
            ps.setString(5, option3.trim());
            ps.setString(6, option4.trim());
            ps.setInt(7, correctAnswer);
            ps.setInt(8, xp);

            ps.executeUpdate();

            response.sendRedirect("ManageMissions?added=success");

        } catch (Exception e) {
            throw new ServletException("Unable to save the mission.", e);
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}