package com.sabuymlm.model.customer;
    
import java.io.Serializable; 
import java.util.Date;
import javax.persistence.*; 

@Table(name="relate_detail")
@Entity 
public class RelateDetail implements Serializable  { 
    @EmbeddedId
    private RelateDetailKey relateKey ; 
    @Column(name="amount" , nullable = false)
    private Float amount ; 
    @Column(name="redue_amount" , nullable = false)
    private Float redueAmount ;   
    @Column(name="total_amount" , nullable = false)
    private Float totalAmount ;   
    @Column(name="description" , nullable = false )
    private String description ;
    @Column(name="relate_status" , nullable = false ,length = 10 )
    private String relateStatus ;
    @Column(name = "relate_date" , columnDefinition = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date relateDate;   
    @Column(name = "pay_date" , columnDefinition = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDate;

    public RelateDetailKey getRelateKey() {
        return relateKey;
    }

    public void setRelateKey(RelateDetailKey relateKey) {
        this.relateKey = relateKey;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getRedueAmount() {
        return redueAmount;
    }

    public void setRedueAmount(Float redueAmount) {
        this.redueAmount = redueAmount;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelateStatus() {
        return relateStatus;
    }

    public void setRelateStatus(String relateStatus) {
        this.relateStatus = relateStatus;
    }

    public Date getRelateDate() {
        return relateDate;
    }

    public void setRelateDate(Date relateDate) {
        this.relateDate = relateDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.relateKey != null ? this.relateKey.hashCode() : 0);
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
        final RelateDetail other = (RelateDetail) obj;
        if (this.relateKey != other.relateKey && (this.relateKey == null || !this.relateKey.equals(other.relateKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RelateDetail{" + "amount=" + amount + ", redueAmount=" + redueAmount + ", totalAmount=" + totalAmount + ", description=" + description + ", relateStatus=" + relateStatus + ", relateDate=" + relateDate + ", payDate=" + payDate + '}';
    } 
}
