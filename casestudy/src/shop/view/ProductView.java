package shop.view;

import shop.model.Product;
import shop.service.IProductService;
import shop.service.ProductService;
import shop.utils.AppUtils;
import shop.utils.InstanUtils;

import java.util.List;
import java.util.Scanner;

public class ProductView {
    private IProductService productService;
    private Scanner scanner = new Scanner(System.in);
    private InstanUtils InstantUtils;

    public ProductView() {
        productService = ProductService.getInstance();
    }
    public void add() {
        do {
            long id = System.currentTimeMillis() / 1000;
            String title = inputTitle(InputOption.ADD);
            double price = inputPrice(InputOption.ADD);
            double quantity = inputQuantity(InputOption.ADD);
            Product product = new Product(id, title, price, quantity);
            productService.add(product);
            System.out.println("Bạn đã thêm sản phẩm thành công\n");

        } while (AppUtils.isRetry(InputOption.ADD));
    }
    public void update() {
        boolean isRetry;
        do {
            showProducts(InputOption.UPDATE);
            long id = inputId(InputOption.UPDATE);
            System.out.println("|------------------------ UPDATE--------------------|");
            System.out.println("|                   1. Sửa tên sản phẩm             |");
            System.out.println("|                   2. Sửa giá sản phẩm             |");
            System.out.println("|                   3. Sửa Số Lượng sản phẩm        |");
            System.out.println("|                   4. Quay lại                     |");
            System.out.println("|                   0. Thoát                        |");
            System.out.println("|---------------------------------------------------|");
            System.out.println("Chọn chức năng: ");
            int option = AppUtils.retryChoose(0, 4);
            Product newProduct = new Product();
            newProduct.setId(id);
            switch (option) {
                case 1:
                    String title = inputTitle(InputOption.UPDATE);
                    newProduct.setTitle(title);
                    productService.update(newProduct);
                    System.out.println("Tên sản phẩm đã cập nhật thành công");
                    break;
                case 2:
                    double price = inputPrice(InputOption.UPDATE);
                    newProduct.setPrice(price);
                    productService.update(newProduct);
                    System.out.println("Giá sản phẩm đã cập nhật thành công");
                    break;
                case 3:
                    double quantity = inputQuantity(InputOption.UPDATE);
                    newProduct.setQuantity(quantity);
                    productService.update(newProduct);
                    System.out.println("Số lượng sản phẩm đã cập nhật thành công");
                    break;
                case 0:
                    AppUtils.exit();
                    break;
            }
            isRetry = option != 4 && AppUtils.isRetry(InputOption.UPDATE);
        } while (isRetry);
    }
    public void remove() {
        showProducts(InputOption.DELETE);
        long id;
        while (!productService.exist(id = inputId(InputOption.DELETE))) {
            System.out.println("Không tìm thấy sản phẩm cần xóa");
            System.out.println("Nhấn 'y' để thêm tiếp \t|\t 'q' để quay lại \t|\t 'e' để thoát chương trình");
            System.out.print(" ⭆ ");
            String option = scanner.nextLine();
            switch (option) {
                case "y":
                    break;
                case "q":
                    return;
                case "e":
                    AppUtils.exit();
                    break;
                default:
                    System.out.println("Chọn chức năng không đúng! Vui lòng chọn lại");
                    break;
            }
        }

        System.out.println("|------------------------ REMOVE MENU--------------------|");
        System.out.println("|                   1. Xác nhận xóa sản phẩm             |");
        System.out.println("|                   2. Quay lại                          |");
        System.out.println("|--------------------------------------------------------|");

        int option = AppUtils.retryChoose(0, 2);
        if (option == 1) {
            productService.deleteById(id);
            System.out.println("Đã xoá sản phẩm thành công!");
            AppUtils.isRetry(InputOption.DELETE);
        }
        if (option == 0) AppUtils.exit();
    }
    public void showProducts(InputOption inputOption) {
        System.out.println("=========================================================DANH SÁCH =========================================================");
        System.out.printf("%-15s %-30s %-25s %-20s %-20s %-20s\n", "Id", "Tên sản phẩm", "Giá sản phẩm", "Số lượng", "Ngày tạo", "Ngày cập nhật");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        List<Product> products = productService.findAll();
        for (Product product : products) {
            System.out.printf("%-15d %-30s %-25s %-20s %-20s %-20s\n", product.getId(),
                    product.getTitle(), AppUtils.doubleToVND(product.getPrice()),
                    AppUtils.doubleToKg(product.getQuantity()),
                    InstantUtils.instantToString(product.getCreatedAt()),
                    product.getUpdatedAt() == null ? "" : InstantUtils.instantToString(product.getUpdatedAt()));
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        }
        if (inputOption == InputOption.SHOW) AppUtils.isRetry(InputOption.SHOW);
    }

    private long inputId(InputOption option) {
        long id;
        switch (option) {
            case UPDATE:
                System.out.println("Nhập Id sản phẩm muốn sửa: ");
                break;
            case DELETE:
                System.out.println("Nhập Id sản phẩm muốn xóa: ");
                break;
            case FIND:
                System.out.println("Nhập Id sản phẩm muốn tìm kiếm: ");
        }
        boolean isRetry = false;
        do {
            id = AppUtils.retryParseLong();
            boolean exist = productService.exist(id);
            switch (option) {
                case UPDATE:
                    if (!exist) {
                        System.out.println("Không tìm thấy id! Vui lòng nhập lại");
                    }
                    isRetry = !exist;
                    break;
                case DELETE:
                    if (!exist) {
                        System.out.println("Không tìm thấy id! Vui lòng nhập lại");
                    }
                    isRetry = !exist;
                    break;
                case FIND:
                    if (!exist) {
                        System.out.println("Không tìm thấy id! Vui lòng nhập lại");
                    }
                    isRetry = !exist;
                    break;
            }
        } while (isRetry);
        return id;
    }

    private String inputTitle(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập tên sản phẩm muốn thêm: ");
                break;
            case UPDATE:
                System.out.println("Nhập tên sản phẩm muốn sửa: ");
                break;
            case FIND:
                System.out.println("Nhập tên sản phẩm muốn tìm kiếm: ");
                break;
        }
        String title = AppUtils.retryString();
        return title;
    }

    private double inputPrice(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập giá sản phẩm muốn thêm: ");
                break;
            case UPDATE:
                System.out.println("Nhập giá sản phẩm muốn sửa: ");
                break;
        }
        double price;
        do {
            price = AppUtils.retryParseDouble();
            if (price <= 0) {
                System.out.println("Nhập sai! Giá phải lớn hơn không (giá > 0)");
                System.out.println("Nhập vào giá");
                System.out.print("⭆ ");
                price = scanner.nextDouble();
            }
        } while (price < 0);
        return price;
    }

    private double inputQuantity(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập số lượng sản phẩm muốn thêm: ");
                break;
            case UPDATE:
                System.out.println("Nhập số lượng sản phẩm muốn sửa: ");
                break;
        }
        double quantity;
        do {
            quantity = AppUtils.retryParseDouble();
            if (quantity <= 0) {
                System.out.println("Nhập sai! Số lượng phải lớn hơn không (số lượng > 0)");
                System.out.println("Nhập vào số lượng");
                System.out.print("⭆ ");
                quantity = scanner.nextDouble();
            }
        } while (quantity < 0);
        return quantity;
    }

    public void showProductsSort(InputOption inputOption, List<Product> products) {
        System.out.println("=========================================================DANH SÁCH=========================================================");
        System.out.printf("%-15s %-30s %-25s %-20s %-20s %-20s\n", "Id", "Tên sản phẩm", "Giá sản phẩm", "Số lượng", "Ngày tạo", "Ngày cập nhật");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        for (Product product : products) {
            System.out.printf("%-15d %-30s %-25s %-20s %-20s %-20s\n", product.getId(), product.getTitle(), AppUtils.doubleToVND(product.getPrice()),
                    AppUtils.doubleToKg(product.getQuantity()), InstantUtils.instantToString(product.getCreatedAt()),
                    product.getUpdatedAt() == null ? "" : InstantUtils.instantToString(product.getUpdatedAt()));
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        }
        if (inputOption == InputOption.SHOW) AppUtils.isRetry(InputOption.SHOW);
    }

    public void findProduct() {
        int option;
        do {
//
            System.out.println("|------------------------ FIND MENU---------------------|");
            System.out.println("|                     1. Tìm kiếm theo ID               |");
            System.out.println("|                     2. Tìm kiếm theo tên              |");
            System.out.println("|                     3. Quay lại                       |");
            System.out.println("|                     0. Thoát                          |");
            System.out.println("|--------------------------------------------------------|");
            System.out.println("Chọn chức năng: ");
            option = AppUtils.retryChoose(0, 3);
            switch (option) {
                case 1:
                    System.out.println("Nhập id muốn tìm kiếm: ");
                    long id = AppUtils.retryParseLong();
                    while (!productService.exist(id)) {
                        System.out.println("Không tìm thấy sản phẩm muốn tìm kiếm");
                        System.out.println("Nhấn 'y' để tìm tiếp \t|\t 'q' để quay lại \t|\t 'e' để thoát chương trình");
                        System.out.print(" ⭆ ");
                        String choice = scanner.nextLine();
                        switch (choice) {
                            case "y":
                                findProduct();
                                break;
                            case "q":
                                return;
                            case "e":
                                AppUtils.exit();
                                break;
                            default:
                                System.out.println("Chọn chức năng không đúng! Vui lòng chọn lại");
                                break;
                        }
                    }
                    Product product = productService.findById(id);
                    System.out.println("-------------------------------------------------------DANH SÁCH --------------------------------------------------");
                    System.out.printf("%-15s %-30s %-25s %-20s %-20s %-20s\n", "Id", "Tên sản phẩm", "Giá sản phẩm", "Số lượng", "Ngày tạo", "Ngày cập nhật");
                    System.out.println(".....................................................................................................................");
                    System.out.printf("%-15d %-30s %-25s %-20s %-20s %-20s\n", product.getId(), product.getTitle(), AppUtils.doubleToVND(product.getPrice()), AppUtils.doubleToKg(product.getQuantity()), InstantUtils.instantToString(product.getCreatedAt()), product.getUpdatedAt() == null ? "" : InstantUtils.instantToString(product.getUpdatedAt()));
                    break;
                case 2:
                    String title = inputTitle(InputOption.FIND);
                    Product product1 = productService.findByTitle(title);
                    if (product1 != null) {
                        System.out.println("--------------------------------------------------------------DANH SÁCH-----------------------------------------------------------");
                        System.out.printf("%-15s %-30s %-25s %-20s %-20s %-20s\n", "Id", "Tên sản phẩm", "Giá sản phẩm", "Số lượng", "Ngày tạo", "Ngày cập nhật");
                        System.out.println("...................................................................................................................................");
                        System.out.printf("%-15d %-30s %-25s %-20s %-20s %-20s\n", product1.getId(),
                                product1.getTitle(), AppUtils.doubleToVND(product1.getPrice()),
                                AppUtils.doubleToKg(product1.getQuantity()),
                                InstantUtils.instantToString(product1.getCreatedAt()),
                                product1.getUpdatedAt() == null ? "" : InstantUtils.instantToString(product1.getUpdatedAt()));
                    } else System.out.println("Không tìm thấy sản phẩm muốn tìm kiếm!");
                    break;
                case 0:
                    AppUtils.exit();
                    break;
            }
        } while (option != 3);
    }

    public void sortByPrice() {
        int option;
        do {
            System.out.println("|------------------------ SORT MENU--------------------|");
            System.out.println("|                  1. Sắp xếp theo giá tăng dần        |");
            System.out.println("|                  2. Sắp xếp theo giá giảm dần        |");
            System.out.println("|                  3. Quay lại                         |");
            System.out.println("|                  0. Thoát                            |");
            System.out.println("|------------------------------------------------------|");
            System.out.println("Chọn chức năng: ");
            option = AppUtils.retryChoose(0, 3);
            switch (option) {
                case 1:
                    sortByPriceOrderByASC();
                    break;
                case 2:
                    sortByPriceOrderByDESC();
                    break;
                case 0:
                    AppUtils.exit();
                    break;
            }
        } while (option != 3);
    }

    public void sortByPriceOrderByASC() {
        showProductsSort(InputOption.SHOW, productService.findAllOrderByPriceASC());
    }

    public void sortByPriceOrderByDESC() {
        showProductsSort(InputOption.SHOW, productService.findAllOrderByPriceDESC());
    }

}



