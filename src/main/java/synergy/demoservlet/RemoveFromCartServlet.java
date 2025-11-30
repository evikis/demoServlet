package synergy.demoservlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        // Проверка аутентификации
        if (username == null) {
            response.sendRedirect("login");
            return;
        }

        String productId = request.getParameter("productId");

        if (productId != null) {
            @SuppressWarnings("unchecked")
            Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
            if (cart != null) {
                cart.remove(productId);
            }
        }

        // Перенаправление обратно в корзину
        response.sendRedirect("cart");
    }
}