package synergy.demoservlet;

import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private Map<String, User> users;

    @Override
    public void init() throws ServletException {
        users = new HashMap<>();
        users.put("user1", new User("user1", "password1", "user"));
        users.put("user2", new User("user2", "password2", "user"));
        users.put("admin", new User("admin", "adminpass", "admin"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Вход</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
        out.println("form { max-width: 300px; }");
        out.println("input[type='text'], input[type='password'] { width: 100%; padding: 8px; margin: 5px 0; }");
        out.println("input[type='submit'] { background: #4CAF50; color: white; padding: 10px; border: none; cursor: pointer; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<h2>Войдите в систему</h2>");
        out.println("<form method='post' action='login'>");
        out.println("Имя пользователя: <input type='text' name='username' required><br>");
        out.println("Пароль: <input type='password' name='password' required><br>");
        out.println("<input type='submit' value='Войти'>");
        out.println("</form>");

        // Тестовые данные для удобства
        out.println("<div style='margin-top: 20px; background: #f5f5f5; padding: 15px;'>");
        out.println("<h3>Тестовые пользователи:</h3>");
        out.println("<p><strong>Админ:</strong> admin / adminpass</p>");
        out.println("<p><strong>Пользователь:</strong> user1 / password1</p>");
        out.println("</div>");

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            // Создание сессии
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            session.setAttribute("userRole", user.getRole());

            // Создание cookie с именем пользователя
            Cookie userCookie = new Cookie("username", username);
            userCookie.setMaxAge(60 * 60 * 24); // 1 день
            resp.addCookie(userCookie);

            // Перенаправление на каталог товаров
            resp.sendRedirect("catalog");
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html><body>");
            out.println("<p>Неверное имя пользователя или пароль. <a href='login'>Попробовать снова</a></p>");
            out.println("</body></html>");
        }
    }
}