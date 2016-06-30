package com.sabuymlm.model.event;
 
import java.io.Serializable; 
import javax.persistence.*;   
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
 
@Table(name = "rent_event_detail")
@Entity 
public class RentEventDetail implements Serializable {

    @EmbeddedId
    private RentEventDetailKey rentEventKey ; 
    @NotNull(message = "กรอกราคา/หน่วย"  )
    @Column(name="current_price" , nullable = false)
    private Float currentPrice ; 
    @NotNull(message = "กรอกจำนวน"  )
    @Column(name="qty" , nullable = false)
    private Integer qty ;
    @NotNull(message = "รวมมูลค่า(ห้ามเป็นค่าว่าง)"  )
    @Column(name="total_amount" , nullable = false)
    private Float totalAmount ; 
     
    @NotEmpty(message = "รหัส(ห้ามเป็นค่าว่าง)"  )
    @Column(name="service_code" , nullable = false)
    private String serviceCode ;
    @NotEmpty(message = "รายละเอียด(ห้ามเป็นค่าว่าง)"  )
    @Column(name="description" , nullable = false )
    private String description ;

    public RentEventDetail(){}
    public RentEventDetail(RentEventDetailKey rentEventKey) {
        this.rentEventKey = rentEventKey ;
    } 

    public RentEventDetailKey getRentEventKey() {
        return rentEventKey;
    }

    public void setRentEventKey(RentEventDetailKey rentEventKey) {
        this.rentEventKey = rentEventKey;
    } 

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    } 

    public Float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    } 

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.rentEventKey != null ? this.rentEventKey.hashCode() : 0);
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
        final RentEventDetail other = (RentEventDetail) obj;
        if (this.rentEventKey != other.rentEventKey && (this.rentEventKey == null || !this.rentEventKey.equals(other.rentEventKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RentEventDetail{" + "rentEventKey=" + rentEventKey + ", currentPrice=" + currentPrice + ", qty=" + qty + ", totalAmount=" + totalAmount + ", serviceCode=" + serviceCode + ", description=" + description + '}';
    } 
     
}
