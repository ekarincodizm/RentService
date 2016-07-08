package com.sabuymlm.model.systemTest;
  
import com.sabuymlm.model.admin.*;  
import java.io.Serializable; 
import java.util.Date; 
import javax.persistence.*;   
import javax.validation.constraints.NotNull; 

@MappedSuperclass
public class CommonEntity implements Serializable {
 
    @NotNull(message = "วันที่บันทึก(ห้ามเป็นค่าว่าง)" )
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", columnDefinition = "datetime", nullable = false)
    private Date createDate;
    @NotNull(message = "ผู้บันทึก(ห้ามเป็นค่าว่าง)" )
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_id", referencedColumnName = "user_id")
    private User createUser;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", columnDefinition = "datetime")
    private Date updateDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_id", referencedColumnName = "user_id")
    private User updateUser;

    public Date getCreateDate() { 
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    } 

    @Override
    public String toString() {
        return "CommonEntity{" + "createDate=" + createDate + ", createUser=" + createUser + ", updateDate=" + updateDate + ", updateUser=" + updateUser + '}';
    }
 
}
