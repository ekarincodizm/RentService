package com.sabuymlm.model.systemTest;
   
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Embeddable 
public class XSponsorDefKey implements Serializable { 
     
    private XSponsorHeaderKey headerId ; 
    
    @NotNull(message = "กำหนดตำแหน่ง(ส่วนต่าง)"  ) 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "x_position_id", referencedColumnName = "position_id")
    private Position xposition ;   

    public XSponsorDefKey(){}
    public XSponsorDefKey(XSponsorHeaderKey headerId , Position xposition){
        this.headerId = headerId ;
        this.xposition = xposition ;
    } 

    public Position getXposition() {
        return xposition;
    }

    public void setXposition(Position xposition) {
        this.xposition = xposition;
    } 

    public XSponsorHeaderKey getHeaderId() {
        return headerId;
    }

    public void setHeaderId(XSponsorHeaderKey headerId) {
        this.headerId = headerId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.headerId != null ? this.headerId.hashCode() : 0);
        hash = 89 * hash + (this.xposition != null ? this.xposition.hashCode() : 0);
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
        final XSponsorDefKey other = (XSponsorDefKey) obj;
        if (this.headerId != other.headerId && (this.headerId == null || !this.headerId.equals(other.headerId))) {
            return false;
        }
        if (this.xposition != other.xposition && (this.xposition == null || !this.xposition.equals(other.xposition))) {
            return false;
        }
        return true;
    }

     
}
