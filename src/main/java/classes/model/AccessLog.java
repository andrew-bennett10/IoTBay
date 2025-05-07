package classes.model;

import java.io.Serializable;
import java.util.Date;

public class AccessLog implements Serializable, User {
    private Integer user_id;
    private Integer accessLogId;
    private Date login;
    private Date logout;
    private Boolean isStaff;

    public AccessLog() {
    }

    public AccessLog(Integer user_id, Date login, Date logout) {
        this.user_id = user_id;
        this.login = login;
        this.logout = logout;


        if (user instanceof Staff) {
            this.isStaff = true;
        } else if (user instanceof Customer) {
            this.isStaff = false;
        }
    }

    public Integer getUserId() {
        return this.user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public Date getLogin() {
        return this.login;
    }

    public void setLogin(Date login) {
        this.login = login != null ? login : new Date();
    }

    public Date getLogout() {
        return this.logout;
    }

    public void setLogout(Date logout) {
        this.logout = logout != null ? logout : new Date();
    }

    public Boolean getIsStaff() {
        return this.isStaff;
    }
    public void setIsStaff(Boolean isStaff) {
        this.isStaff = isStaff;
    }
    public Integer getAccessLogId() {
        return this.accessLogId;
    }
    public void setAccessLogId(Integer accessLogId) {
        this.accessLogId = accessLogId;
    }

}
