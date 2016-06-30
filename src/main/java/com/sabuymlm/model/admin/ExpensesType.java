package com.sabuymlm.model.admin;

import java.io.Serializable; 
import java.util.Date;
import javax.persistence.*; 
import org.hibernate.validator.constraints.NotEmpty;

@Table(name = "expenses_type")
@Entity   
public class ExpensesType implements Serializable {

    @Id
    @TableGenerator(name = "ExpensesTypeGen", allocationSize = 1, initialValue = 1, table = "SEQUENCE_TABLE", pkColumnValue = "ExpensesType" ,valueColumnName="SEQ_COUNT",pkColumnName="SEQ_NAME")
    @GeneratedValue(generator = "ExpensesTypeGen",strategy= GenerationType.TABLE)
    @Column(name = "type_id", columnDefinition ="int" , nullable=false)
    private Integer id;
    @NotEmpty(message = "กรอกชื่อประเภท"  ) 
    @Column(name = "type_name" , length=100 , nullable = false )
    private String name; 
    @NotEmpty(message = "เลือกประเภท(รับ/จ่าย)"  ) 
    @Column(name = "expenses_income_type" , length=1 , nullable = false )
    private String expensesIncomeType; 
    @Column(name = "type_engname" , length=100)
    private String engname;  
    @Column(name = "description" , length=500 )
    private String description;    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date" , columnDefinition = "datetime" , nullable = false )
    private Date createDate;   
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "create_id" , referencedColumnName = "user_id" )
    private User createUser;   
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date" , columnDefinition = "datetime" )
    private Date updateDate ; 
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "update_id" , referencedColumnName = "user_id"  )
    private User updateUser;    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "company_id" , referencedColumnName = "company_id"  )
    private Company company;   

    public String getExpensesIncomeType() {
        return expensesIncomeType;
    }

    public void setExpensesIncomeType(String expensesIncomeType) {
        this.expensesIncomeType = expensesIncomeType;
    }

    public ExpensesType() {
    }

    public ExpensesType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public String getEngname() {
        return engname;
    }

    public void setEngname(String engname) {
        this.engname = engname;
    }
    
    public void setDescription(String description) {
        this.description = description;
    } 

    public String getDescription() {
        return description;
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
    
    public String getStyle(){
        if(expensesIncomeType != null && expensesIncomeType.equals("Y")) {
            return "background-color:#CCFFDD;"; 
        }else if(expensesIncomeType != null && expensesIncomeType.equals("N")) {
            return "background-color:#FFFFFF;color:red;";
        }else {
            return "";
        }
    }
    
    public String getStyleCombo(){
        if(expensesIncomeType != null && expensesIncomeType.equals("Y")) {
            return "color:green;"; 
        }else if(expensesIncomeType != null && expensesIncomeType.equals("N")) {
            return "color:red;";
        }else {
            return "";
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
        int hash = 7;
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
        final ExpensesType other = (ExpensesType) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }  

    @Override
    public String toString() {
        return "ExpensesType{" + "id=" + id + ", name=" + name + ", engname=" + engname + ", description=" + description + '}';
    } 
    
}
