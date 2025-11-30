package synergy.demoservlet;

import catalog.ProductCatalog;
import model.Product;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        // Проверка аутентификации
        if (username == null) {
            response.sendRedirect("login");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Получение корзины из сессии
        @SuppressWarnings("unchecked")
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ConcurrentHashMap<>();
        }

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Корзина покупок</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
        out.println(".cart-item { border: 1px solid #ddd; padding: 15px; margin: 10px 0; border-radius: 5px; }");
        out.println(".empty-cart { color: #666; font-style: italic; }");
        out.println(".total { font-size: 1.2em; font-weight: bold; margin: 20px 0; }");
        out.println(".nav-links { margin: 20px 0; }");
        out.println(".remove-btn { background: #f44336; color: white; padding: 5px 10px; border: none; cursor: pointer; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<h1>Корзина покупок</h1>");
        out.println("<p>Пользователь: <strong>" + username + "</strong></p>");

        out.println("<div class='nav-links'>");
        out.println("<a href='catalog'>Продолжить покупки</a> | ");
        out.println("<a href='welcome'>На главную</a> | ");
        out.println("<a href='logout'>Выйти</a>");
        out.println("</div>");

        if (cart.isEmpty()) {
            out.println("<div class='empty-cart'>");
            out.println("<p>Ваша корзина пуста.</p>");
            out.println("</div>");
        } else {
            double total = 0;
            out.println("<h2>Товары в корзине:</h2>");

            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                String productId = entry.getKey();
                int quantity = entry.getValue();

                Product product = ProductCatalog.getInstance().getProduct(productId);
                if (product != null) {
                    double itemTotal = product.getPrice() * quantity;
                    total += itemTotal;

                    out.println("<div class='cart-item'>");
                    out.println("<h3>" + product.getName() + "</h3>");
                    out.println("<p><strong>Цена за шт.:</strong> $" + product.getPrice() + "</p>");
                    out.println("<p><strong>Количество:</strong> " + quantity + "</p>");
                    out.println("<p><strong>Общая стоимость:</strong> $" + String.format("%.2f", itemTotal) + "</p>");

                    out.println("<form method='post' action='remove-from-cart' style='display: inline;'>");
                    out.println("<input type='hidden' name='productId' value='" + product.getId() + "'>");
                    out.println("<input type='submit' class='remove-btn' value='Удалить из корзины'>");
                    out.println("</form>");

                    out.println("</div>");
                }
            }

            out.println("<div class='total'>");
            out.println("<p>Общая сумма: $" + String.format("%.2f", total) + "</p>");
            out.println("</div>");
        }

        out.println("</body></html>");
    }
}