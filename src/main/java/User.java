import java.util.ArrayList;
import java.util.List;

public class User {
    static int idCount = 0;
    int id;
    String name;
    String password;
    String phone;
    String type;
    String status;

    private String getName() {
        return name;
    };

    private int getId() {
        return id;
    }

    private String getPhone() {
        return phone;
    }

    private int setIdCount() {
        idCount+=1;
        return idCount;
    }

    public User(String Name, String Password, String Phone, String Type, String Status) {
        name = Name;
        setPassword(Password);
        phone = Phone;
        id = setIdCount();
        type = Type;
        status = Status;
    }


    private void setPassword(String password) {
        this.password = password;
    }

    public User getUserId(String Phone, List<User> Users) {

        return null;
    }


}
