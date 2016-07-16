package com.sabuymlm.model.admin;

import com.sabuymlm.utils.FileEntry;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "company")
@Entity
public class Company implements Serializable {

    @Id 
    @Column(name = "company_id", columnDefinition = "int", nullable = false)
    private Integer id;
    @Column(name = "company_name", length = 100)
    private String companyName;
    @Column(name = "company_nameEng", length = 100)
    private String companyNameEng;
    @Column(name = "bill_addr", length = 500)
    private String addr;
    @Column(name = "phone", length = 200)
    private String phone;
    @Column(name = "mobile", length = 200)
    private String mobile;
    @Column(name = "fax", length = 200)
    private String fax;
    @Column(name = "post", length = 5)
    private String post;
    @Column(name = "taxNumber", length = 20)
    private String taxNumber;
    @Column(name = "updateDate", columnDefinition = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_id", referencedColumnName = "user_id")
    private User updateUser;

    @Column(name = "lat")
    private Double lat;
    @Column(name = "lng")
    private Double lng;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private Country country;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", referencedColumnName = "province_id")
    private Province province;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", referencedColumnName = "district_id")
    private District district;
    @Column(name = "sub_district", length = 100)
    private String subDistrict;

    @Column(name = "upload_path", length = 500)
    private String uploadPath;

    @Column(name = "disc_tax")
    private Double discTax;
    @Column(name = "vat")
    private Double vat;

    @Column(name = "logo_path", length = 100)
    private String logo;
    @Column(name = "contact", length = 255)
    private String contact;
    
    @Column(name = "remark_invoice", length = 500)
    private String remarkInvoice;
    
    @Column(name = "max_mlm_member" , columnDefinition = "int")
    private Integer maxMlmMember; 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaxMlmMember() {
        return maxMlmMember;
    }

    public void setMaxMlmMember(Integer maxMlmMember) {
        this.maxMlmMember = maxMlmMember;
    }

    public String getRemarkInvoice() {
        return remarkInvoice;
    }

    public void setRemarkInvoice(String remarkInvoice) {
        this.remarkInvoice = remarkInvoice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNameEng() {
        return companyNameEng;
    }

    public void setCompanyNameEng(String companyNameEng) {
        this.companyNameEng = companyNameEng;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
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

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public Double getDiscTax() {
        return discTax;
    }

    public void setDiscTax(Double discTax) {
        this.discTax = discTax;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLogoPath() {
        return uploadPath + "/" + FileEntry.ADMIN_LOGO_FOLDER;
    }

    public String getWebPath() {
        if (id != null) {
            return uploadPath + "/" + FileEntry.WEB_FOLDER + "/" + id;
        } else {
            return null;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Company other = (Company) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
