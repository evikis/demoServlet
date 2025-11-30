package synergy.demoservlet;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private Map<String, String> users;
    @Override
    public void init() throws ServletException {
        users = new HashMap<>();
        users.put("user1", "password1");
        users.put("user2", "password2");
        users.put("admin", "adminpass");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Вход</title></head><body>");
        out.println("<h2>Войдите в систему</h2>");
        out.println("<form method='post' action='login'>");
        out.println("Имя пользователя: <input type='text' name='username' required><br>");
        out.println("Пароль: <input type='password' name='password' required><br>");
        out.println("<input type='submit' value='Войти'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Проверка учётных данных
        if (users.containsKey(username) && users.get(username).equals(password)) {
            // Создание сессии
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            // Создание cookie с именем пользователя
            Cookie userCookie = new Cookie("username", username);
            userCookie.setMaxAge(60 * 60 * 24); // 1 день
            resp.addCookie(userCookie);

            // Перенаправление на WelcomeServlet
            resp.sendRedirect("welcome");
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html><body>");
            out.println("<p>Неверное имя пользователя или пароль. <a href='login'>Попробовать снова</a></p>");
            out.println("</body></html>");
        }
    }

    public void destroy() {
    }
}
