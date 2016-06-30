package com.sabuymlm.model.event;

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.*;
import com.sabuymlm.model.customer.Customer;
import com.sabuymlm.utils.DateUtils;
import com.sabuymlm.utils.FileEntry;
import com.sabuymlm.utils.Format;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;  
import javax.validation.constraints.NotNull; 
import org.hibernate.validator.constraints.NotEmpty;

@Table(name = "rent_event")
@Entity 
public class RentEvent implements Serializable {

    @Id
    @TableGenerator(name = "RentEventGen", allocationSize = 1, initialValue = 1, table = "SEQUENCE_TABLE", pkColumnValue = "RentEvent", valueColumnName = "SEQ_COUNT", pkColumnName = "SEQ_NAME")
    @GeneratedValue(generator = "RentEventGen", strategy = GenerationType.TABLE)
    @Column(name = "rent_id", columnDefinition = "int", nullable = false)
    private Integer id;
    @NotEmpty(message = "รหัสบิลใบเสร็จ(ห้ามเป็นค่าว่าง)" )
    @Column(name = "rent_code", length = 25, nullable = false)
    private String code;
    @Column(name = "remark", length = 500, nullable = true)
    private String remark;
    @Column(name = "total_amount", columnDefinition = "float", nullable = false)
    private Float totalAmount;
    @Column(name = "net_amount", columnDefinition = "float", nullable = false)
    private Float netAmount;
    @Column(name = "disc_amount", columnDefinition = "float", nullable = false)
    private Float discAmount;
    @NotNull(message = "เลือกวันที่กำหนดจ่ายค่าเช่า(ห้ามเป็นค่าว่าง)" )
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rent_date", columnDefinition = "datetime", nullable = false)
    private Date rentDate;
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

    @NotNull(message = "เลือกลูกค้า(ห้ามเป็นค่าว่าง)"  )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id" , nullable = false )
    private Customer customer;
    @NotEmpty(message = "เลือกสถานะใบเสร็จ"  )
    @Column(name = "rent_status", length = 10)
    private String rentStatus;
    @Column(name = "rent_close_date", columnDefinition = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rentCloseDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_close_id", referencedColumnName = "user_id")
    private User rentCloseUser;

    @Column(name = "attach_file", length = 255)
    private String attachFile;
    @NotNull(message = "กรอกจำนวนเช่าทั้งหมด"  )
    @Column(name = "rent_month" , columnDefinition = "int")
    private Integer rentMonth;
    @NotNull(message = "วันที่หมดอายุ(ห้ามเป็นค่าว่าง)"  )
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expire_date" , columnDefinition = "datetime" )
    private Date expireDate;
    
    @Column(name = "send_email" , columnDefinition = "varchar(10)" )
    private String sendEmail;
    @Column(name = "print_invoice" , columnDefinition = "varchar(10)" )
    private String printInvoice;
    @Column(name = "send_sms" , columnDefinition = "varchar(10)" )
    private String sendSms;
    @Column(name = "send_line" , columnDefinition = "varchar(10)" )
    private String sendLine;
    
    @Column(name = "re_rent_status" , columnDefinition = "varchar(10)" )
    private String reRentStatus;

    @Transient
    private int sequenceIndex = -1;

    @NotEmpty(message = "กรอกรายการด้านล่าง"  )
    @OneToMany(mappedBy = "rentEventKey.rentEvent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RentEventDetail> itemDetails;

    public RentEvent() {
        itemDetails = new ArrayList<RentEventDetail>(); 
    }

    private int findMaxSequenceIndex() {
        int no = 0;
        for (RentEventDetail item : itemDetails) {
            if(no < item.getRentEventKey().getNo() ){
                no = item.getRentEventKey().getNo();
            }
        }
        return no;
    }

    public void addToDetails(RentEventDetail dt) {
        if (sequenceIndex == -1) {
            sequenceIndex = findMaxSequenceIndex();
        }
        sequenceIndex++;
        this.itemDetails.add(dt);
        if (dt.getRentEventKey() == null) {
            dt.setRentEventKey(new RentEventDetailKey(sequenceIndex, this));
        }
    }

    public String getReRentStatus() {
        return reRentStatus;
    }

    public void setReRentStatus(String reRentStatus) {
        this.reRentStatus = reRentStatus;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getRentMonth() {
        return rentMonth;
    }

    public void setRentMonth(Integer rentMonth) {
        this.rentMonth = rentMonth;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    
    public long getRemainDate(){
        if(!(reRentStatus != null && reRentStatus.equals("true"))){
            return DateUtils.dateDiff(new Date(), this.expireDate); 
        }else {
            return 0l;
        }
    }

    public List<RentEventDetail> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<RentEventDetail> itemDetails) {
        this.itemDetails = itemDetails;
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
    
    public String getRemarkStatus() {
        if((customer != null && customer.getRentStatus() != null && customer.getRentStatus().equals("N"))){
            return getRemark() + " ลูกค้ายกเลิกเช่า " ;
        }else {
            return getRemark();
        }
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Float getNetAmount() {
        return (netAmount ==null?0f:netAmount);
    }

    public void setNetAmount(Float netAmount) {
        this.netAmount = netAmount;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getRentStatus() {
        return rentStatus;
    }
    
    public String getRentStatusName() {
        if(rentStatus != null && rentStatus.equals("CLOSE")) {
            return "จ่าย(" + Format.formatDateThai("dd/MM/yyyy", rentCloseDate) + ")"; 
        }else if(rentStatus != null && rentStatus.equals("PAUSE")){
            return rentStatus + "(" + Format.formatDateThai("dd/MM/yyyy", expireDate) + ")"; 
        }else {
            return rentStatus;
        } 
    }

    public void setRentStatus(String rentStatus) {
        this.rentStatus = rentStatus;
    }

    public Date getRentCloseDate() {
        return rentCloseDate;
    }

    public void setRentCloseDate(Date rentCloseDate) {
        this.rentCloseDate = rentCloseDate;
    }

    public User getRentCloseUser() {
        return rentCloseUser;
    }

    public void setRentCloseUser(User rentCloseUser) {
        this.rentCloseUser = rentCloseUser;
    }

    public Float getDiscAmount() {
        return (discAmount ==null?0f:discAmount);
    }

    public void setDiscAmount(Float discAmount) {
        this.discAmount = discAmount;
    }

    public String getSendEmail() {
        return (sendEmail==null?"false":sendEmail);
    }

    public void setSendEmail(String sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getPrintInvoice() {
        return printInvoice;
    }

    public void setPrintInvoice(String printInvoice) {
        this.printInvoice = printInvoice;
    }

    public String getSendSms() {
        return (sendSms==null?"false":sendSms);
    }

    public void setSendSms(String sendSms) {
        this.sendSms = sendSms;
    }

    public String getSendLine() {
        return (sendLine==null?"false":sendLine);
    }

    public void setSendLine(String sendLine) {
        this.sendLine = sendLine;
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
    
    public String getStyle(){
        if(!(reRentStatus != null && reRentStatus.equals("true"))){
            if(getRentStatus().equals("PAUSE") || (customer != null && customer.getRentStatus() != null && customer.getRentStatus().equals("N")) ) {
                return "background-color:#666666;color:white;";
            } else if( getRentStatus().equals("OPEN") || getRentStatus().equals("CLOSE") ){
                long remain = getRemainDate();
                if(remain < 0 ){
                    return "background-color:yellow;color:red;";
                }else if( getRentStatus().equals("OPEN") || remain <= getCustomer().getRentAlertDay() ){
                    if(getRentStatus().equals("OPEN") ){
                        return "background-color:#CCFFDD;";
                    }else {
                        return "color:red;";
                    }
                }else {
                    return "";
                }
            } else { 
                return "";
            }
        }else {
            return "background-color:#DDDDDD;color:blue;";
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
        final RentEvent other = (RentEvent) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RentEvent{" + "id=" + id + ", code=" + code + ", remark=" + remark + ", netAmount=" + netAmount + ", rentDate=" + rentDate + ", createDate=" + createDate + ", createUser=" + createUser + ", updateDate=" + updateDate + ", updateUser=" + updateUser + ", company=" + company + ", customer=" + customer + ", rentStatus=" + rentStatus + ", rentCloseDate=" + rentCloseDate + ", rentCloseUser=" + rentCloseUser + '}';
    }
}
