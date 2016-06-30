package com.sabuymlm.model.customer;
   
import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.admin.User; 
import com.sabuymlm.utils.FileEntry;
import java.io.Serializable; 
import java.util.Date;
import java.util.List;
import javax.persistence.*; 
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Table(name="treaty_rule")
@Entity  
public class TreatyRule implements Serializable  { 
    @Id
    @TableGenerator(name = "TreatyRuleGen", allocationSize = 1, initialValue = 1, table = "SEQUENCE_TABLE", pkColumnValue = "TreatyRule" ,valueColumnName="SEQ_COUNT",pkColumnName="SEQ_NAME")
    @GeneratedValue(generator = "TreatyRuleGen",strategy= GenerationType.TABLE)
    @Column(name = "treaty_rule_id" ,columnDefinition = "int" )
    private Integer treatyRuleId; 
    @Column(name = "name" , length=200)
    private String name; 
    @Column(name = "eng_name" , length=200)
    private String engname; 
    @Column(name = "treaty_addrs" , length= 255 ) 
    private String treatyAddrs; 
    @Column(name = "contact" , length= 255 ) 
    private String contact;    
    @Column(name = "contact_mobile" , length= 255 ) 
    private String contactMobile; 
    @Column(name = "contact_email" , length= 255 ) 
    private String contactEmail;   
      
    @Column(name = "createDate" , columnDefinition = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;   
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "create_id", referencedColumnName = "user_id")
    private User createUser ; 
    @Column(name = "updateDate" , columnDefinition = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;   
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "update_id", referencedColumnName = "user_id")
    private User updateUser ; 
    @NotNull(message = "บริษัทลูกค้า(Customer)(ห้ามเป็นค่าว่าง)" )
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer ; 
    
    @Column(name = "treaty_rule_status" , length= 10 ) 
    private String treatyRuleStatus;
    @Column(name = "treaty_close_date" , columnDefinition = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date treatyCloseDate;   
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "treaty_close_id", referencedColumnName = "user_id")
    private User treatyCloseUser ; 
    
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company ; 
    
    @Column(name = "remark" , length= 255 ) 
    private String remark; 
    @Column(name = "attach_file" , length= 255 ) 
    private String attachFile;  
    
    @Column(name="document_rule" , columnDefinition = "VARCHAR(MAX)" )
    @Lob
    private String document ;
    
    @OneToMany(mappedBy = "relateKey.treatyRule", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RelateDetail> relateDetails;

    public Integer getTreatyRuleId() {
        return treatyRuleId;
    }

    public void setTreatyRuleId(Integer treatyRuleId) {
        this.treatyRuleId = treatyRuleId;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getTreatyAddrs() {
        return treatyAddrs;
    }

    public void setTreatyAddrs(String treatyAddrs) {
        this.treatyAddrs = treatyAddrs;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getTreatyRuleStatus() {
        return treatyRuleStatus;
    }

    public void setTreatyRuleStatus(String treatyRuleStatus) {
        this.treatyRuleStatus = treatyRuleStatus;
    }

    public Date getTreatyCloseDate() {
        return treatyCloseDate;
    }

    public void setTreatyCloseDate(Date treatyCloseDate) {
        this.treatyCloseDate = treatyCloseDate;
    }

    public User getTreatyCloseUser() {
        return treatyCloseUser;
    }

    public void setTreatyCloseUser(User treatyCloseUser) {
        this.treatyCloseUser = treatyCloseUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
    
    public String getAttachPath(){
        return SecurityUtil.getUserDetails().getCompany().getUploadPath() 
                + "/" + FileEntry.ADMIN_ATTACHFILE_FOLDER 
                +"/" + SecurityUtil.getUserDetails().getCompany().getId() ;
    } 
    
    public String getDownloadAttachFile() {
        if(attachFile != null ) {
            return getAttachPath() + "/" + attachFile;
        }else {
            return null;
        }
    } 

    public List<RelateDetail> getRelateDetails() {
        return relateDetails;
    }

    public void setRelateDetails(List<RelateDetail> relateDetails) {
        this.relateDetails = relateDetails;
    } 

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.treatyRuleId != null ? this.treatyRuleId.hashCode() : 0);
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
        final TreatyRule other = (TreatyRule) obj;
        if (this.treatyRuleId != other.treatyRuleId && (this.treatyRuleId == null || !this.treatyRuleId.equals(other.treatyRuleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TreatyRule{" + "treatyRuleId=" + treatyRuleId + ", name=" + name + ", engname=" + engname + ", treatyAddrs=" + treatyAddrs + ", contact=" + contact + ", contactMobile=" + contactMobile + ", contactEmail=" + contactEmail + ", createDate=" + createDate + ", createUser=" + createUser + ", updateDate=" + updateDate + ", updateUser=" + updateUser + ", customer=" + customer + ", treatyRuleStatus=" + treatyRuleStatus + ", treatyCloseDate=" + treatyCloseDate + ", treatyCloseUser=" + treatyCloseUser + ", remark=" + remark + '}';
    } 
    
}
