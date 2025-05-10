package classes.model;
import java.io.Serializable;

public class Customer implements Serializable, User {
    private Integer userId;
    private String email;
    private String password;
    private String fName;
    private String lName;
    private Integer age;
    private String address;
    private Boolean registered;

    private Boolean isStaff; // redundant variable I can't be bothered to remove dont touch just input whatever doesnt matter

    public Customer() {}

    public Customer(Integer userId, String email, String password, String fName, String lName, Integer age, String address, Boolean registered, Boolean isStaff) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.age = age;
        this.address = address;
        this.registered = registered;
        this.isStaff = isStaff;
    }

    public Customer(String email, String password, String fName, String lName, Integer age, String address, Boolean registered, Boolean isStaff) {
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.age = age;
        this.address = address;
        this.registered = registered;
        this.isStaff = isStaff;
    }


    public Integer getId() {
        return this.userId;
    }
    public void setId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFName() {
        return this.fName;
    }
    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return this.lName;
    }
    public void setLName(String lName) {
        this.lName = lName;
    }

    public Integer getAge() {
        return this.age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getRegistered() {
        return this.registered;
    }
    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public Boolean getIsStaff() {
        return this.isStaff;
    }
    public void setIsStaff(Boolean isStaff) {
        this.isStaff = isStaff;
    }

    public String getName() { return this.fName + " " + this.lName; }

}