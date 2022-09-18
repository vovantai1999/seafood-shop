package shop.view;

import shop.model.Role;
import shop.model.User;
import shop.service.IUserService;
import shop.service.UserService;
import shop.utils.AppUtils;
import shop.utils.InstanUtils;
import shop.utils.ValidateUtils;

import java.util.List;
import java.util.Scanner;

public class UserView {
    private final IUserService userService;
    private final Scanner scanner = new Scanner(System.in);
    private InstanUtils InstantUtils;

    public UserView() {
        userService = UserService.getInstance();
    }

    public void addUser() {
        do {
            try {
                long id = System.currentTimeMillis() / 1000;
                String username = inputUsername();
                String password = inputPassword();
                String fullName = inputFullName(InputOption.ADD);
                String phone = inputPhone(InputOption.ADD);
                String address = inputAddress(InputOption.ADD);
                String email = inputEmail(InputOption.ADD);
                User user = new User(id, username, password, fullName, phone, email, address, Role.USER);
                setRole(user);
                userService.add(user);
                System.out.println("Đã thêm thành công!");
            } catch (Exception e) {
                System.out.println("Nhập sai. vui lòng nhập lại!");
                e.printStackTrace();
            }
        } while (AppUtils.isRetry(InputOption.ADD));
    }


    public void setRole(User user) {
        int option;

        System.out.println("|------------------------ SET ROLE--------------------|");
        System.out.println("|                     1. USER                         |");
        System.out.println("|                     2. ADMIN                        |");
        System.out.println("|                     3. Quay lại                     |");
        System.out.println("|                     0. Thoát                        |");
        System.out.println("|-----------------------------------------------------|");
        do {
            System.out.println("Chọn Role: ");
            option = AppUtils.retryChoose(0,3);
            switch (option) {
                case 1:
                    user.setRole(Role.USER);
                    break;
                case 2:
                    user.setRole(Role.ADMIN);
                    break;
                case 3:
                    UserViewLauncher.launch();
                    break;
                case 0:
                    AppUtils.exit();
                    break;
                default:
                    System.out.println("Nhập không đúng! Vui lòng nhập lại");
                    setRole(user);
            }
        } while (option==3);
    }

    public void showUsers(InputOption inputOption) {
        System.out.println("================================================================DANH SÁCH NGƯỜI DÙNG===============================================================");
        System.out.printf("%-15s %-22s %-15s %-22s %-15s %-15s %-20s %-20s\n", "Id", "Tên", "Số điện thoại", "Email", "Địa chỉ", "Người dùng", "Ngày tạo", "Ngày cập nhật");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
        List<User> users = userService.findAll();
        for (User user : users) {
            System.out.printf("%-15s %-22s %-15s %-22s %-15s %-15s %-20s %-20s\n",
                    user.getId(),
                    user.getFullName(),
                    user.getPhone(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getRole(),
                    InstantUtils.instantToString(user.getCreatedAt()),
                    user.getUpdatedAt() == null ? "" : InstantUtils.instantToString(user.getUpdatedAt())
            );
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
        if (inputOption == InputOption.SHOW) AppUtils.isRetry(InputOption.SHOW);
    }

    public void updateUser() {
        boolean isRetry = false;
        do {
            try {
                showUsers(InputOption.UPDATE);
                long id = inputId(InputOption.UPDATE);

                System.out.println("|------------------------ UPDATE ---------------------------|");
                System.out.println("|                 1. Cập nhật lại tên người dùng            |");
                System.out.println("|                 2. Cập nhật lại số điện thoại             |");
                System.out.println("|                 3. Cập nhật lại email                     |");
                System.out.println("|                 4. Cập nhật lại địa chỉ                   |");
                System.out.println("|                 5. Cập nhật lại Role                      |");
                System.out.println("|                 6. Quay lại                               |");
                System.out.println("|                 0. Thoát                                  |");
                System.out.println("|-----------------------------------------------------------|");

                int option = AppUtils.retryChoose(0, 6);
                User newUser = new User();
                newUser.setId(id);
                switch (option) {
                    case 1:
                        String name = inputFullName(InputOption.UPDATE);
                        newUser.setFullName(name);
                        userService.update(newUser);
                        System.out.println("Bạn đã đổi tên thành công!");
                        break;
                    case 2:
                        String phone = inputPhone(InputOption.UPDATE);
                        newUser.setPhone(phone);
                        userService.update(newUser);
                        System.out.println("Bạn đã đổi số điện thoại thành công!");
                        break;
                    case 3:
                        String email = inputEmail(InputOption.UPDATE);
                        newUser.setEmail(email);
                        userService.update(newUser);
                        System.out.println("Bạn đã đổi email thành công!");
                        break;
                    case 4:
                        String address = inputAddress(InputOption.UPDATE);
                        newUser.setAddress(address);
                        userService.update(newUser);
                        System.out.println("Bạn đã đổi địa chỉ thành công!");
                        break;
                    case 5:
                        setRole(newUser);
                        userService.update(newUser);
                        System.out.println("Bạn đã đổi quyền thành công!");
                        break;
                    case 6:
                        break;
                    case 0:
                        AppUtils.exit();
                }
                isRetry = option != 6 && AppUtils.isRetry(InputOption.UPDATE);

            } catch (Exception e) {
                System.out.println("Nhập sai! vui lòng nhập lại");
            }
        } while (isRetry);
    }

    public void remove() {
        showUsers(InputOption.DELETE);
        long id;
        System.out.println();
        while (!userService.exist(id = inputId(InputOption.DELETE))) {
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

        System.out.println("|------------------------ REMOVE MENU --------------------|");
        System.out.println("|                 1. Xác nhận xóa người dùng này          |");
        System.out.println("|                 2. Quay lại                             |");
        System.out.println("|                 0. Thoát                                |");
        System.out.println("|---------------------------------------------------------|");

        int option = AppUtils.retryChoose(0, 2);
        if (option == 1) {
            userService.deleteById(id);
            System.out.println("Đã xoá sản phẩm thành công!");
            AppUtils.isRetry(InputOption.DELETE);
        }
        if (option == 0) AppUtils.exit();

    }


    private long inputId(InputOption option) {
        long id;
        switch (option) {
            case UPDATE:
                System.out.println("Nhập id bạn muốn sửa: ");
                break;
            case DELETE:
                System.out.println("Nhập id bạn muốn xoá: ");
                break;
        }
        boolean isRetry = false;
        do {
            id = AppUtils.retryParseLong();
            boolean exist = userService.existById(id);
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
            }
        } while (isRetry);
        return id;
    }

    public String inputUsername() {
        System.out.println("Nhập Username (8-20 kí tự viết thường không bao gồm dấu cách, kí tự đặc biệt)");
        String username;

        do {
            if (!ValidateUtils.isUsernameValid(username=AppUtils.retryString())) {
                System.out.println(username + " của bạn không đúng định dạng! Vui lòng kiểm tra và nhập lại ");
                continue;
            }
            if (userService.existsByUsername(username)) {
                System.out.println("Username này đã tồn tại. Vui lòng nhập lại");
                continue;
            }
            break;
        } while (true);

        return username;
    }

    private String inputFullName(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập họ và tên (vd: Vo Van Tai) ");
                break;
            case UPDATE:
                System.out.println("Nhập tên mà bạn muốn sửa đổi");
                break;
        }

        System.out.print(" ⭆ ");
        String fullName;
        while (!ValidateUtils.isNameValid(fullName = scanner.nextLine().trim())) {
            System.out.println("Tên " + fullName + " không đúng định dạng." + " Vui lòng nhập lại!" + " (Tên phải viết hoa chữ cái đầu và không dấu)");
            System.out.println("Nhập tên (vd: Vo Van Tai) ");
            System.out.print(" ⭆ ");
        }
        return fullName;
    }

    public String inputPhone(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập số điện thoại (vd: 0358543401): ");
                break;
            case UPDATE:
                System.out.println("Nhập số điện thoại mà bạn muốn đổi");
                break;
        }
        System.out.print(" ⭆ ");
        String phone;
        do {
            phone = scanner.nextLine();
            if (!ValidateUtils.isPhoneValid(phone)) {
                System.out.println("Số " + phone + " của bạn không đúng. Vui lòng nhập lại! " + "(Số điện thoại bao gồm 10 số và bắt đầu là số 0)");
                System.out.println("Nhập số điện thoại (vd: 0358543401)");
                System.out.print(" ⭆ ");
                continue;
            }
            if (userService.existsByPhone(phone)) {
                System.out.println("Số điện thoại này đã tồn tại! Mời bạn nhập lại!");
                System.out.print(" ⭆ ");
                continue;
            }
            break;
        } while (true);

        return phone;
    }

    private String inputEmail(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập email bạn muốn thêm: vd: name@gmail.com ");
                break;
            case UPDATE:
                System.out.println("Nhập email bạn muốn đổi: vd: name@gmail.com");
                break;
        }
        System.out.print(" ⭆ ");
        String email;
        do {
            if (!ValidateUtils.isEmailValid(email = scanner.nextLine())) {
                System.out.println("Email " + email + " của bạn không đúng định dạng! Vui lòng kiểm tra và nhập lại ");
                System.out.println("Nhập email (vd: name@gmail.com)");
                System.out.print(" ⭆ ");
                continue;
            }
            if (userService.existsByEmail(email)) {
                System.out.println("Email " + email + " của bạn đã tồn tại! vui lòng kiểm tra lại");
                System.out.println("Nhập email (vd: name@gmail.com)");
                System.out.print(" ⭆ ");
                continue;
            }
            break;
        } while (true);
        return email;
    }

    private String inputPassword() {
        System.out.println("Nhập mật khẩu( mật khẩu phải >= 8 kí tự )");
        System.out.print(" ⭆ ");
        String password;
        while (!ValidateUtils.isPasswordValid(password = scanner.nextLine())) {
            System.out.println("Mật khẩu phải >= 8 kí tự không bao gồm khoảng trắng và kí tự đặt biệt. Vui lòng nhập lại!");
            System.out.print(" ⭆ ");
        }
        return password;
    }

    private String inputAddress(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập địa chỉ (vd: Hue" +
                        ")");
                break;
            case UPDATE:
                System.out.println("Nhập địa chỉ mà bạn muốn đổi");
                break;
        }
        String address;
        while (!ValidateUtils.isAddressValid(address = scanner.nextLine().trim())) {
            System.out.println("Địa chỉ " + address + " không đúng định dạng." + " Vui lòng nhập lại!");
            System.out.println("Nhập tên (vd: Phu Gia-Hue) ");
            System.out.print(" ⭆ ");
        }
        return address;
    }
}

