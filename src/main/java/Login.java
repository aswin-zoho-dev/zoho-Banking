import java.util.List;

public class Login {
    String User;
    private String Password;

    public User authorization(String phone, String password, List<User> users) {
        for (int index = 0; index < users.size(); index++) {
            if (users.get(index).phone.equalsIgnoreCase(phone)) {
                if (users.get(index).password.equals(password)) {
                    return users.get(index);
                }
            }
        }
        return null;
    }
}
