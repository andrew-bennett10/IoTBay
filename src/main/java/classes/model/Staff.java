package classes.model;
import java.io.Serializable;

public class Staff implements Serializable, User {
    private Integer staffId;
    private String email;
    private String password;
    private String fName;
    private String lName;
    private String role;
    private String phoneNumber;
    private Boolean isActive;

    public Staff() {}

    public Staff(Integer userId, String email, String password, String fName, String lName, String role) {
        this.staffId = userId;
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.role = role;

    }

    public Staff(String email, String password, String fName, String lName, String role) {
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.role = role;

    }

    public Staff(String email, String password, String fName, String lName, String role, String phoneNumber, Boolean isActive) {
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
    }

    public Staff(int staffId,String email, String password, String fName, String lName, String role, String phoneNumber, Boolean isActive) {
        this.staffId = staffId;
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
    }


    public Integer getId() {
        return staffId;
    }
    public void setId(Integer userId) {
        this.staffId = userId;
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

    public String getRole() {
        return this.role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getName() { return this.fName + " " + this.lName; }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }
}