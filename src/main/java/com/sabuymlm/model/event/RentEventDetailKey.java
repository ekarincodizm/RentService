package com.sabuymlm.model.event;
 
import java.io.Serializable;
import javax.persistence.*;

@Embeddable 
public class RentEventDetailKey implements Serializable {
    
    @Column(name = "list_no" )
    private Integer no;
    
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "rent_id" )
    private RentEvent rentEvent;

    public RentEventDetailKey(){}
    public RentEventDetailKey(Integer no , RentEvent rentEvent){
        this.no = no ;
        this.rentEvent = rentEvent ;
    }
    
    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public RentEvent getRentEvent() {
        return rentEvent;
    }

    public void setRentEvent(RentEvent rentEvent) {
        this.rentEvent = rentEvent;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.no != null ? this.no.hashCode() : 0);
        hash = 19 * hash + (this.rentEvent != null ? this.rentEvent.hashCode() : 0);
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
        final RentEventDetailKey other = (RentEventDetailKey) obj;
        if (this.no != other.no && (this.no == null || !this.no.equals(other.no))) {
            return false;
        }
        if (this.rentEvent != other.rentEvent && (this.rentEvent == null || !this.rentEvent.equals(other.rentEvent))) {
            return false;
        }
        return true;
    }
     
}
