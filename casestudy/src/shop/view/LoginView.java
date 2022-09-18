package shop.view;

import shop.model.Role;
import shop.model.User;
import shop.service.IUserService;
import shop.service.UserService;
import shop.utils.AppUtils;

import java.util.Scanner;

public class LoginView {
    private final IUserService userService;
    private final Scanner scanner = new Scanner(System.in);
    User user = new User();

    public LoginView() {
        userService = UserService.getInstance();
    }

    public void login() {
        boolean isRetry = false;
            System.out.println("|---------------------------------------------------------|");
            System.out.println("|                                                         |");
            System.out.println("|                     ĐĂNG NHẬP HỆ THỐNG                  |");
            System.out.println("|                                                         |");
            System.out.println("|---------------------------------------------------------|");


        do {
            System.out.println("Username");
            String username = AppUtils.retryString();
            System.out.println("Mật khẩu");
            String password = AppUtils.retryString();
            user = userService.login(username, password);
            if (user == null) {
                System.out.println("Tài khoản không hợp lệ! ");
                isRetry = isRetry();
                login();
            }
            if (user.getRole()== Role.ADMIN){
                System.out.println("Đã đăng nhập với quyền Admin thành công!");
                System.out.println("CHÀO MỪNG ĐÃ ĐẾN VỚI CỬA HÀNG HẢI SẢN  \n");
                MainLauncher.menuOption();
            }
            else {
                System.out.println("Bạn đã đăng nhập với quyền User thành công!");
                System.out.println("CHÀO MỪNG ĐÃ ĐẾN VỚI CỬA HÀNG HẢI SẢN \n");
                MemberView.launch();
            }
        } while (isRetry);
    }

    private boolean isRetry() {
        do {
            try {
                System.out.println("|----------------------------Sale Menu --------------------------|");
                System.out.println("|               1.Nhập 'y' để tiếp tục đăng nhập                 |");
                System.out.println("|               2.Nhập 'n' để thoát khỏi chương trình            |");
                System.out.println("|----------------------------------------------------------------| ");
                System.out.print(" ⭆ ");
                String option = scanner.nextLine();
                switch (option) {
                    case "y":
                        return true;
                    case "n":
                        AppUtils.exit();
                        break;
                    default:
                        System.out.println("Chọn chức năng không đúng! Vui lòng chọn lại");
                        break;
                }

            } catch (Exception ex) {
                System.out.println("Nhập sai! vui lòng nhập lại");
                ex.printStackTrace();
            }
        } while (true);
    }
}

