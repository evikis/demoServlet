package catalog;

import model.Product;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ProductCatalog {
    private static ProductCatalog instance;
    private Map<String, Product> products;

    private ProductCatalog() {
        products = new ConcurrentHashMap<>();
        // Добавляем тестовые товары
        addProduct(new Product("1", "Ноутбук", "Мощный игровой ноутбук", 999.99, 10));
        addProduct(new Product("2", "Смартфон", "Флагманский смартфон", 699.99, 15));
        addProduct(new Product("3", "Наушники", "Беспроводные наушники", 199.99, 20));
    }

    public static synchronized ProductCatalog getInstance() {
        if (instance == null) {
            instance = new ProductCatalog();
        }
        return instance;
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProduct(String id) {
        return products.get(id);
    }

    public Map<String, Product> getAllProducts() {
        return new ConcurrentHashMap<>(products);
    }

    public boolean removeProduct(String id) {
        return products.remove(id) != null;
    }
}