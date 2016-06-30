package com.sabuymlm.model.event;

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.*; 
import com.sabuymlm.utils.FileEntry;
import java.io.Serializable; 
import java.util.Date; 
import javax.persistence.*;  
import javax.validation.constraints.NotNull; 
import org.hibernate.validator.constraints.NotEmpty;

@Table(name = "expenses_income")
@Entity 
public class ExpensesIncome implements Serializable {

    @Id
    @TableGenerator(name = "ExpensesIncomeGen", allocationSize = 1, initialValue = 1, table = "SEQUENCE_TABLE", pkColumnValue = "ExpensesIncome", valueColumnName = "SEQ_COUNT", pkColumnName = "SEQ_NAME")
    @GeneratedValue(generator = "ExpensesIncomeGen", strategy = GenerationType.TABLE)
    @Column(name = "expenses_income_id", columnDefinition = "int", nullable = false)
    private Integer id;
    @NotEmpty(message = "รหัสอ้างอิง(ห้ามเป็นค่าว่าง)" )
    @Column(name = "expenses_income_code", length = 25, nullable = false)
    private String code;
    @Column(name = "remark", length = 500, nullable = true)
    private String remark;
    @NotEmpty(message = "เลือกประเภท"  ) 
    @Column(name = "expenses_income_type", length = 1, nullable = false)
    private String expensesIncomeType;
    @NotNull(message = "กรอกจำนวนเงิน"  ) 
    @Column(name = "total_amount", columnDefinition = "float", nullable = false)
    private Float totalAmount; 
    @NotNull(message = "เลือกวันที่อ้างอิง(ห้ามเป็นค่าว่าง)" )
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expenses_income_date", columnDefinition = "datetime", nullable = false)
    private Date expensesDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", columnDefinition = "datetime", nullable = false)
    private Date createDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_id", referencedColumnName = "user_id")
    private User createUser;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", columnDefinition = "datetime")
    private Date updateDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_id", referencedColumnName = "user_id")
    private User updateUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company; 

    @Column(name = "attach_file", length = 255)
    private String attachFile;  
    
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private ExpensesType expensesType;

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }  

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getRemark() {
        return (remark ==null?"":remark); 
    } 

    public void setRemark(String remark) {
        this.remark = remark;
    }  

    public ExpensesType getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(ExpensesType expensesType) {
        this.expensesType = expensesType;
    }

    public String getAttachPath() {
        return SecurityUtil.getUserDetails().getCompany().getUploadPath()
                + "/" + FileEntry.ADMIN_ATTACHFILE_FOLDER
                + "/" + SecurityUtil.getUserDetails().getCompany().getId();
    }

    public String getDownloadAttachFile() {
        if(attachFile != null ) {
            return getAttachPath() + "/" + attachFile;
        }else {
            return null;
        }
    }

    public String getExpensesIncomeType() {
        return expensesIncomeType;
    }

    public void setExpensesIncomeType(String expensesIncomeType) {
        this.expensesIncomeType = expensesIncomeType;
    }

    public Date getExpensesDate() {
        return expensesDate;
    }

    public void setExpensesDate(Date expensesDate) {
        this.expensesDate = expensesDate;
    }
    
    public String getStyle(){
        if(expensesIncomeType != null && expensesIncomeType.equals("Y")) {
            return "background-color:#CCFFDD;"; 
        }else {
            return "background-color:#FFFFFF;color:red;";
        }
    }
    
    public String getTypeName(){
        if(expensesIncomeType != null && expensesIncomeType.equals("Y")) {
            return "Income"; 
        }else {
            return "Expenses";
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final ExpensesIncome other = (ExpensesIncome) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
 
}
