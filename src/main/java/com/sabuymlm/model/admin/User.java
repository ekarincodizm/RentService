package com.sabuymlm.model.admin;
 
import java.io.Serializable; 
import java.util.Date;
import javax.persistence.*; 
import org.hibernate.validator.constraints.NotEmpty;

@Table(name="users")
@Entity  
public class User implements Serializable  { 
    @Id 
    @TableGenerator(name = "UserGen", allocationSize = 1, initialValue = 1, table = "SEQUENCE_TABLE", pkColumnValue = "User" ,valueColumnName="SEQ_COUNT",pkColumnName="SEQ_NAME")
    @GeneratedValue(generator = "UserGen",strategy= GenerationType.TABLE)
    @Column(name = "user_id" , columnDefinition = "int"  , nullable = false)
    private Integer id;
    @NotEmpty(message = "รหัสผู้ใช้(ห้ามเป็นค่าว่าง)" )
    @Column(name = "username" , length=50, nullable = false)
    private String username;
    @NotEmpty(message = "รหัสผ่าน(ห้ามเป็นค่าว่าง)" )
    @Column(name = "password" , length=50, nullable = false)
    private String password; 
    @Column(name = "password_show" , length=50, nullable = false)
    private String passwordShow; 
    @Column(name = "name" , length= 100, nullable = false)
    private String name;   
    @NotEmpty(message = "เลือกระดับผู้ใช้งาน" )
    @Column(name = "admin_level" , length= 10, nullable = false)
    private String adminLevel;   
    @Column(name = "mobile" , length= 100)
    private String mobile; 
    @NotEmpty(message = "email(ห้ามเป็นค่าว่าง)" )
    @Column(name = "email" , length= 100)
    private String email; 
    @Column(name = "company_id" , columnDefinition = "int")
    private Integer companyId; 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_date" , columnDefinition = "datetime")
    private Date lastLoginDate; 

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getPasswordShow() {
        return passwordShow;
    }

    public void setPasswordShow(String passwordShow) {
        this.passwordShow = passwordShow;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }  

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", adminLevel=" + adminLevel + ", mobile=" + mobile + '}';
    } 

    
}
