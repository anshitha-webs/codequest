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

@WebServlet("/Answer")
public class Answer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        int missionId;
        int selectedAnswer;

        try {
            missionId = Integer.parseInt(request.getParameter("missionId"));
            selectedAnswer = Integer.parseInt(request.getParameter("answer"));

            if (selectedAnswer < 1 || selectedAnswer > 4) {
                response.sendRedirect("Missions");
                return;
            }
        } catch (Exception e) {
            response.sendRedirect("Missions");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection con = DB.getConnection()) {
            con.setAutoCommit(false);

            try {
                String checkSql =
                    "SELECT correct_answer, xp_reward FROM missions WHERE id = ?";

                int correctAnswer;
                int reward;

                try (PreparedStatement ps = con.prepareStatement(checkSql)) {
                    ps.setInt(1, missionId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            response.sendRedirect("Missions");
                            return;
                        }

                        correctAnswer = rs.getInt("correct_answer");
                        reward = rs.getInt("xp_reward");
                    }
                }

                // Prevent students from earning XP repeatedly on one mission.
                String existingSql =
                    "SELECT id FROM mission_submissions "
                    + "WHERE user_id = ? AND mission_id = ?";

                try (PreparedStatement ps = con.prepareStatement(existingSql)) {
                    ps.setInt(1, userId);
                    ps.setInt(2, missionId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            con.rollback();
                            showMessage(out,
                                "Already Attempted",
                                "You have already submitted this mission.",
                                "Missions");
                            return;
                        }
                    }
                }

                boolean correct = selectedAnswer == correctAnswer;
                int earnedXP = correct ? reward : 0;

                String insertSql =
                    "INSERT INTO mission_submissions "
                    + "(user_id, mission_id, selected_answer, is_correct, xp_earned) "
                    + "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                    ps.setInt(1, userId);
                    ps.setInt(2, missionId);
                    ps.setInt(3, selectedAnswer);
                    ps.setBoolean(4, correct);
                    ps.setInt(5, earnedXP);
                    ps.executeUpdate();
                }

                if (correct) {
                    String updateSql =
                        "UPDATE users SET xp = xp + ?, "
                        + "level = FLOOR((xp + ?) / 100) + 1 WHERE id = ?";

                    try (PreparedStatement ps = con.prepareStatement(updateSql)) {
                        ps.setInt(1, earnedXP);
                        ps.setInt(2, earnedXP);
                        ps.setInt(3, userId);
                        ps.executeUpdate();
                    }
                }

                con.commit();

                if (correct) {
                    showMessage(out, "Correct Answer!",
                        "Excellent! You earned " + earnedXP + " XP.",
                        "Dash");
                } else {
                    showMessage(out, "Incorrect Answer",
                        "No XP earned this time. Keep practising!",
                        "Missions");
                }

            } catch (Exception e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (Exception e) {
            throw new ServletException("Unable to submit your answer.", e);
        }
    }

    private void showMessage(PrintWriter out, String title,
                             String message, String destination) {
        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>" + title + " - CodeQuest</title>");
        out.println("<style>");
        out.println("body{margin:0;background:#10111f;color:white;font-family:Arial,sans-serif;display:flex;justify-content:center;align-items:center;min-height:100vh}");
        out.println(".box{background:#1c1d32;padding:35px;border-radius:15px;text-align:center;max-width:500px;width:90%}");
        out.println("h1{color:#a78bfa}");
        out.println("a{display:inline-block;margin-top:20px;padding:12px 20px;background:#8b5cf6;color:white;text-decoration:none;border-radius:8px}");
        out.println("</style></head><body><div class='box'>");
        out.println("<h1>" + title + "</h1>");
        out.println("<p>" + message + "</p>");
        out.println("<a href='" + destination + "'>Continue</a>");
        out.println("</div></body></html>");
    }
}
