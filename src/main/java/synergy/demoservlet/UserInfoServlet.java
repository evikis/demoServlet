package synergy.demoservlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class UserInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Информация о пользователе</title></head><body>");

        if (username != null) {
            out.println("<p><strong>Имя пользователя: </strong>" + username + "</p>");
            out.println("<p><strong>Время создания сессии: </strong>" + new Date(session.getCreationTime()) + "</p>");
            out.println("<p><strong>ID сессии: </strong>" + session.getId() + "</p>" );
            out.println("<br><a href='profile'>Перейти в профиль</a>");
        } else {
            out.println("<p>Пользователь не авторизован. <a href='login'>Войти</a></p>");
        }
        out.println("<br><a href='welcome'>На главную</a>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}