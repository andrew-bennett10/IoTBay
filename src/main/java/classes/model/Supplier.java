package classes.model;
import java.io.Serializable;

public class Supplier implements Serializable {
    private Integer SupplierId;
    private String ContactName;
    private String Company;
    private String Email;
    private String SuppStatus;

    public Supplier() {}

    public Supplier(String ContactName, String Company, String Email, String SuppStatus) {
        this.SupplierId = SupplierId;
        this.ContactName = ContactName;
        this.Company = Company;
        this.Email = Email;
        this.SuppStatus = SuppStatus;
    }

    public Integer getId() {
        return this.SupplierId;
    }
    public void setId(Integer userId) {
        this.SupplierId = userId;
    }

    public String getContactName() {
        return this.ContactName;
    }
    public void setContactName(String ContactName) {
        this.ContactName = ContactName;
    }

    public String getEmail() {
        return this.Email;
    }
    public void setEmail(String email) {
        this.Email = email;
    }

    public String getCompany() {
        return this.Company;
    }
    public void setCompany(String Company) {
        this.Company = Company;
    }

    public String getSuppStatus() {
        return this.SuppStatus;
    }
    public void setSuppStatus(String SuppStatus) {
        this.SuppStatus = SuppStatus;
    }

}