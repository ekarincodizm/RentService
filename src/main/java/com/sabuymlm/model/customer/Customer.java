package com.sabuymlm.model.customer;
  
import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.admin.Country;
import com.sabuymlm.model.admin.District;
import com.sabuymlm.model.admin.Province;
import com.sabuymlm.model.admin.User;
import com.sabuymlm.utils.FileEntry;
import java.io.Serializable; 
import java.util.Date;
import javax.persistence.*; 

@Table(name="customer")
@Entity  
public class Customer implements Serializable  { 
    @Id
    @TableGenerator(name = "CustomerGen", allocationSize = 1, initialValue = 1, table = "SEQUENCE_TABLE", pkColumnValue = "Customer" ,valueColumnName="SEQ_COUNT",pkColumnName="SEQ_NAME")
    @GeneratedValue(generator = "CustomerGen",strategy= GenerationType.TABLE)
    @Column(name = "customer_id" ,columnDefinition = "int" )
    private Integer customerId; 
    @Column(name = "name" , length=200)
    private String name; 
    @Column(name = "eng_name" , length=200)
    private String engname; 
    @Column(name = "addrs" , length= 255 ) 
    private String addrs;   
    @Column(name = "tax_no" , length= 255 ) 
    private String taxNo;   
    @Column(name = "contact" , length= 255 ) 
    private String contact;   
    @Column(name = "tel" , length= 255 ) 
    private String tel;   
    @Column(name = "mobile" , length= 255 ) 
    private String mobile;   
    @Column(name = "email" , length= 255 ) 
    private String email;   
    @Column(name = "lat" , length= 255 ) 
    private String lat;   
    @Column(name = "lng" , length= 255 ) 
    private String lng;     
    @Column(name = "logo_path" , length= 255 ) 
    private String logo;   
    
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
    
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private Country country;
    
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "province_id", referencedColumnName = "province_id")
    private Province province;
    @ManyToOne(fetch= FetchType.LAZY) 
    @JoinColumn(name = "district_id", referencedColumnName = "district_id")
    private District district;
    @Column(name = "sub_district", length=100  )
    private String subDistrict;
    @Column(name = "post", length=10  )
    private String post;
    
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company ; 
    
    @Column(name = "vat_type" , length= 5 ) 
    private String vatType;  
    
    @Column(name = "rent_per_month" , columnDefinition ="float"  ) 
    private Float rentPerMonth; 
    @Column(name = "rent_status" , length = 1 ) 
    private String rentStatus; 
    
    @Column(name = "rent_alert_day" , columnDefinition = "int" ) 
    private Integer rentAlertDay; 
    
    @Column(name = "description" , length=500) 
    private String description; 
    @Column(name = "ref_code" , length=10) 
    private String refCode; 

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public Integer getRentAlertDay() {
        return rentAlertDay;
    }

    public void setRentAlertDay(Integer rentAlertDay) {
        this.rentAlertDay = rentAlertDay;
    }

    public Float getRentPerMonth() {
        return rentPerMonth;
    }

    public void setRentPerMonth(Float rentPerMonth) {
        this.rentPerMonth = rentPerMonth;
    }
    public String getRentStatus() {
        return rentStatus;
    }
    
    public String getRentStatusName() {
        return (rentStatus != null && rentStatus.equals("N")?"ยกเลิกเช่า":"เช่าอยู่");
    }
    
    public String getStyle(){
        if(rentStatus != null && rentStatus.equals("N")){ 
            return "background-color:#666666;color:white;";
        }else { 
            return "";
        }
    }

    public void setRentStatus(String rentStatus) {
        this.rentStatus = rentStatus;
    }
    
    public String getVatType() {
        return vatType;
    }

    public void setVatType(String vatType) {
        this.vatType = vatType;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public String getAddrs() {
        return addrs;
    }

    public void setAddrs(String addrs) {
        this.addrs = addrs;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    } 

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    public String getLogoPath(){
        return SecurityUtil.getUserDetails().getCompany().getUploadPath() + "/" + FileEntry.ADMIN_LOGO_FOLDER + "/customer" ;
    }
    
    public String getImageLogo(){
        if(logo ==null){
            return "" ;
        }else {
            return "/ImageServletUtil?imagePath=" + getLogoPath()+"/"+logo;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.customerId != null ? this.customerId.hashCode() : 0);
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
        final Customer other = (Customer) obj;
        if (this.customerId != other.customerId && (this.customerId == null || !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId + ", name=" + name + ", engname=" + engname + ", addrs=" + addrs + ", taxNo=" + taxNo + ", contact=" + contact + ", tel=" + tel + ", mobile=" + mobile + ", email=" + email + ", lat=" + lat + ", lng=" + lng + ", logoPath=" + logo + '}';
    }
    
}
