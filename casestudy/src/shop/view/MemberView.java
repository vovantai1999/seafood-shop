package shop.view;

import shop.utils.AppUtils;

import java.util.Scanner;

public class MemberView {
    public static void launch() {
        int option;
        do {

            System.out.println("|------------------------ MEMBER MENU--------------------|");
            System.out.println("|                     1. Hiển Thị sản phẩm               |");
            System.out.println("|                     2. Tìm kiếm sản phẩm               |");
            System.out.println("|                     3. Sắp xếp sản phẩm                |");
            System.out.println("|                     4. Tạo order                       |");
            System.out.println("|                     5. Xem danh sách order             |");
            System.out.println("|                     0. Thoát                           |");
            System.out.println("|--------------------------------------------------------|");
            Scanner scanner = new Scanner(System.in);
            ProductView productView = new ProductView();
            OrderView orderView = new OrderView();
            System.out.println("\nChọn chức năng: ");
            option = AppUtils.retryChoose(0,6);
            switch (option) {
                case 1:
                    productView.showProducts(InputOption.SHOW);
                    break;
                case 2:
                    productView.findProduct();
                    break;
                case 3:
                    productView.sortByPrice();
                    break;
                case 4:
                    orderView.addOrder();
                    break;
                case 5:
                    orderView.showAllOrder();
                    break;
                case 0:
                    AppUtils.exit();
                    break;
                default:
                    System.err.println("Chọn chức năng không đúng! Vui lòng chọn lại");
                    launch();
            }
        } while (true);
    }
}


