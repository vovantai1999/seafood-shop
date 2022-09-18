package shop.view;

import shop.utils.AppUtils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainLauncher {
    public static void launch() {
        LoginView loginView = new LoginView();
        loginView.login();
    }

    public static void menuOption() {
        do {
            mainMenu();
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("\nChọn chức năng ");
                System.out.print("⭆ ");
                int number = scanner.nextInt();
                switch (number) {
                    case 1:
                        UserViewLauncher.launch();
                        break;
                    case 2:
                        ProductViewLauncher.run();
                        break;
                    case 3:
                        OrderViewLauncher.run();
                        break;
                    case 0:
                        AppUtils.exit();
                    default:
                        System.out.println("Chọn chức năng không đúng! Vui lòng chọn lại");
                        menuOption();
                }

            } catch (InputMismatchException io) {
                System.out.println("Nhập sai! Vui lòng nhập lại");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }


    public static void mainMenu() {

        System.out.println("|------------------------ MAIN MENU-----------------------|");
        System.out.println("|                  1. Quản lý người dùng                  |");
        System.out.println("|                  2. Quản lý sản phẩm                    |");
        System.out.println("|                  3. Quản lý đơn đặt hàng                 |");
        System.out.println("|                  0. Thoát                               |");
        System.out.println("|---------------------------------------------------------|");
    }

    public static void userMenu() {

        System.out.println("|------------------------ USER MANGEMENT-----------------------|");
        System.out.println("|                  1. Thêm người dùng                          |");
        System.out.println("|                  2. Sửa thông tin người dùng                 |");
        System.out.println("|                  3. Hiển thị danh sách người dùng            |");
        System.out.println("|                  4. Xóa người dùng                           |");
        System.out.println("|                  5. Quay lại                                 |");
        System.out.println("|                  0. Thoát                                    |");
        System.out.println("|--------------------------------------------------------------|");
    }

    public static void productMenu() {

        System.out.println("|------------------------ QUẢN LÝ SẢN PHẨM --------------------|");
        System.out.println("|                     1. Thêm Sản Phẩm                         |");
        System.out.println("|                     2. Sửa Sản Phẩm                          |");
        System.out.println("|                     3. Hiển thị sản phẩm                     |");
        System.out.println("|                     4. Xóa sản phẩm                          |");
        System.out.println("|                     5. Tìm Kiếm Sản Phẩm                     |");
        System.out.println("|                     6. Sắp Xếp sản phẩm                      |");
        System.out.println("|                     7. Quay lại                              |");
        System.out.println("|--------------------------------------------------------------|");
    }

    public static void orderMenu() {

        System.out.println("|------------------------ ORDER MENU--------------------|");
        System.out.println("|                     1. Tạo order                      |");
        System.out.println("|                     2. Xem danh sách order            |");
        System.out.println("|                     3. Quay lại                       |");
        System.out.println("|                     0. Thoát                          |");
        System.out.println("|-------------------------------------------------------|");
    }
}

