package ca.ualberta.smaccr.giftcarder;

/**
 * Created by Carin on 10/25/2015.
 */
public class User {
    private String username;
    private String city;
    private String phone;
    private String email;
    private Inventory inv;
    private UserList friends;

    public User() {
        this.inv = new Inventory();
        this.friends = new UserList();
    }


    public void addUsername(String username) {
        this.username = username;
    }

    public void addCity(String city) {
        this.city = city;
    }

    public void addPhone(String phone) {
        this.phone = phone;
    }

    public void addEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Inventory getInv() {
        return this.inv;
    }

    public void setInv(Inventory inventory) {this.inv = inventory;}
}
