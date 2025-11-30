package synergy.demoservlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;

        // Проверка аутентификации и роли (админы не могут добавлять в корзину)
        if (username == null || "admin".equals(userRole)) {
            if (username == null) {
                response.sendRedirect("login");
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Администраторы не могут добавлять товары в корзину.");
            }
            return;
        }

        String productId = request.getParameter("productId");

        if (productId != null && !productId.trim().isEmpty()) {
            // Получение или создание корзины в сессии
            @SuppressWarnings("unchecked")
            Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ConcurrentHashMap<>();
                session.setAttribute("cart", cart);
            }

            // Добавление товара в корзину
            cart.put(productId, cart.getOrDefault(productId, 0) + 1);
        }

        // Перенаправление обратно в каталог
        response.sendRedirect("catalog");
    }
}