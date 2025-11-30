package synergy.demoservlet;

import catalog.ProductCatalog;
import model.Product;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Каталог товаров</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
        out.println(".product { border: 1px solid #ddd; padding: 15px; margin: 10px 0; border-radius: 5px; }");
        out.println(".admin-panel { background: #f0f8ff; padding: 15px; margin: 20px 0; }");
        out.println(".add-to-cart { background: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer; }");
        out.println(".nav-links { margin: 20px 0; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<h1>Каталог товаров</h1>");

        if (username != null) {
            out.println("<p>Добро пожаловать, <strong>" + username + "</strong>! Роль: " + userRole + "</p>");
        }

        out.println("<div class='nav-links'>");
        out.println("<a href='cart'>Корзина</a> | ");
        out.println("<a href='welcome'>На главную</a> | ");
        if ("admin".equals(userRole)) {
            out.println("<a href='add-product'>Добавить товар</a> | ");
        }
        out.println("<a href='logout'>Выйти</a>");
        out.println("</div>");

        // Панель администратора
        if ("admin".equals(userRole)) {
            out.println("<div class='admin-panel'>");
            out.println("<h3>Панель администратора</h3>");
            out.println("<p>У вас есть права на добавление новых товаров.</p>");
            out.println("<a href='add-product' style='background: #008CBA; color: white; padding: 10px; text-decoration: none;'>Добавить новый товар</a>");
            out.println("</div>");
        }

        // Отображение товаров
        Map<String, Product> products = ProductCatalog.getInstance().getAllProducts();

        if (products.isEmpty()) {
            out.println("<p>Каталог товаров пуст.</p>");
        } else {
            out.println("<h2>Доступные товары:</h2>");
            for (Product product : products.values()) {
                out.println("<div class='product'>");
                out.println("<h3>" + product.getName() + "</h3>");
                out.println("<p><strong>Описание:</strong> " + product.getDescription() + "</p>");
                out.println("<p><strong>Цена:</strong> $" + product.getPrice() + "</p>");
                out.println("<p><strong>В наличии:</strong> " + product.getQuantity() + " шт.</p>");

                if (username != null && !"admin".equals(userRole)) {
                    out.println("<form method='post' action='add-to-cart' style='display: inline;'>");
                    out.println("<input type='hidden' name='productId' value='" + product.getId() + "'>");
                    out.println("<input type='submit' class='add-to-cart' value='Добавить в корзину'>");
                    out.println("</form>");
                }

                out.println("</div>");
            }
        }

        out.println("</body></html>");
    }
}