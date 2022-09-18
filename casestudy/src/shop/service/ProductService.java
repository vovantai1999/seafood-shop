package shop.service;

import shop.model.Product;
import shop.utils.CSVUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ProductService implements IProductService {

    public final static String PATH = "data/products";

    private static ProductService instance;

    private ProductService() {
    }

    public static ProductService getInstance() {
        if (instance == null) instance = new ProductService();
        return instance;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        List<String> records = CSVUtils.read(PATH);
        for (String record : records) {
            products.add(Product.parse(record));
        }
        return products;
    }

    @Override
    public void add(Product newProduct) {
        List<Product> products = findAll();
        newProduct.setCreatedAt(Instant.now());
        boolean isExists = false;
        for (Product product : products) {
            if (product.getTitle().equals(newProduct.getTitle())) {
                product.setPrice(newProduct.getPrice());
                product.setQuantity(product.getQuantity() + newProduct.getQuantity());
                product.setUpdatedAt(Instant.now());
                isExists = true;
            }
        }
        if (!isExists) products.add(newProduct);
        CSVUtils.write(PATH, products);
    }

    @Override
    public void update(Product newProduct) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getId() == newProduct.getId()) {
                String title = newProduct.getTitle();
                if (title != null && !title.isEmpty()) product.setTitle(newProduct.getTitle());

                Double quantity = newProduct.getQuantity();
                if (quantity >= 0) product.setQuantity(quantity);

                Double price = newProduct.getPrice();
                if (price > 0) product.setPrice(price);

                product.setUpdatedAt(Instant.now());
                CSVUtils.write(PATH, products);
                break;
            }
        }

    }

    @Override
    public Product findById(long id) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getId() == id) return product;
        }
        return null;
    }

    @Override
    public Product findByTitle(String title) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getTitle().toLowerCase().equals(title.toLowerCase())) return product;
        }
        return null;
    }

    @Override
    public boolean exist(long id) {
        return findById(id) != null;
    }

    @Override
    public boolean existByName(String name) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getTitle().equals(name)) return true;
        }
        return false;
    }

    @Override
    public boolean existsById(long id) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getId() == id) return true;
        }
        return false;
    }

    @Override
    public void deleteById(long id) {
        List<Product> products = findAll();
        products.removeIf(new Predicate<Product>() {
            @Override
            public boolean test(Product product) {
                return product.getId() == id;
            }
        });
        CSVUtils.write(PATH, products);
    }

    @Override
    public List<Product> findAllOrderByPriceASC() {
        List<Product> products = new ArrayList<>(findAll());
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                double result = o1.getPrice() - o2.getPrice();
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            }
        });

        return products;
    }

    @Override
    public List<Product> findAllOrderByPriceDESC() {
        List<Product> products = new ArrayList<>(findAll());
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                double result = o2.getPrice() - o1.getPrice();
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            }
        });
        return products;
    }

    @Override
    public Product getProductById(long productId) {
        for (Product product : getProducts()) {
            if (product.getId() == productId) ;
            return product;
        }
        return new Product();
    }
    @Override
    public List<Product> getProducts() {
        List<Product> newProducts = new ArrayList<>();
        List<String> records = CSVUtils.read(PATH);
        for (String record : records) {
            newProducts.add(Product.parse(record));
        }
        return newProducts;
    }
}
