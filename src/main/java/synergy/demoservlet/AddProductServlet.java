package synergy.demoservlet;

import catalog.ProductCatalog;
import model.Product;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;

        // Проверка прав администратора
        if (!"admin".equals(userRole)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен. Требуются права администратора.");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Добавить товар</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
        out.println("form { max-width: 500px; }");
        out.println("input, textarea { width: 100%; padding: 8px; margin: 5px 0; }");
        out.println("input[type='submit'] { background: #008CBA; color: white; padding: 10px; border: none; cursor: pointer; }");
        out.println(".nav-links { margin: 20px 0; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<h1>Добавить новый товар</h1>");

        out.println("<div class='nav-links'>");
        out.println("<a href='catalog'>Вернуться в каталог</a> | ");
        out.println("<a href='welcome'>На главную</a>");
        out.println("</div>");

        out.println("<form method='post' action='add-product'>");
        out.println("Название товара: <input type='text' name='name' required><br>");
        out.println("Описание: <textarea name='description' rows='4' required></textarea><br>");
        out.println("Цена: <input type='number' name='price' step='0.01' required><br>");
        out.println("Количество: <input type='number' name='quantity' required><br>");
        out.println("<input type='submit' value='Добавить товар'>");
        out.println("</form>");

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;

        // Проверка прав администратора
        if (!"admin".equals(userRole)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен. Требуются права администратора.");
            return;
        }

        // Получение данных из формы
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        // Генерация ID (в реальном приложении лучше использовать базу данных)
        String id = String.valueOf(System.currentTimeMillis());

        // Создание и добавление товара
        Product product = new Product(id, name, description, price, quantity);
        ProductCatalog.getInstance().addProduct(product);

        // Перенаправление обратно в каталог
        response.sendRedirect("catalog");
    }
}