package synergy.demoservlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String username = null;
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies){
                if ("username".equals(cookie.getName())){
                    username = cookie.getValue();
                    break;
                }
            }
        }

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Добро пожаловать!</title></head><body>");
        if (username != null) {
            out.println("<h2>Добро пожаловать, " +  username + "!</h2>");
            out.println("<a href='profile'>Перейти в профиль</a><br>");
        } else {
            out.println("<h2>Добро пожаловать!</h2>");
        }
        out.println("<a href='user'>Посмотреть информацию о пользователе</a>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}