package synergy.demoservlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        if (username == null) {
            response.sendRedirect("login");
            return;
        }

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Профиль пользователя</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
        out.println(".profile-info { background: #f5f5f5; padding: 20px; border-radius: 5px; }");
        out.println(".logout-form { margin-top: 20px; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<h1>Профиль пользователя</h1>");
        out.println("<div class='profile-info'>");
        out.println("<h2>Добро пожаловать, " + username + "!</h2>");
        out.println("<h3>Информация о сессии:</h3>");
        out.println("<p><strong>ID сессии:</strong> " + session.getId() + "</p>");
        out.println("<p><strong>Время создания:</strong> " + new Date(session.getCreationTime()) + "</p>");
        out.println("<p><strong>Последний доступ:</strong> " + new Date(session.getLastAccessedTime()) + "</p>");
        out.println("<p><strong>Макс. время неактивности:</strong> " + session.getMaxInactiveInterval() + " секунд</p>");
        out.println("</div>");

        out.println("<div class='logout-form'>");
        out.println("<form action='logout' method='post'>");
        out.println("<input type='submit' value='Выйти из системы'>");
        out.println("</form>");
        out.println("</div>");

        out.println("<p><a href='welcome'>Вернуться на главную</a></p>");
        out.println("</body></html>");
    }
}