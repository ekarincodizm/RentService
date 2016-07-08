package com.sabuymlm.model.systemTest;
   
import com.sabuymlm.model.admin.Company;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Embeddable 
public class BinaryBalanceDefKey implements Serializable {
     
    @NotNull(message = "กำหนดตำแหน่ง" ) 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", referencedColumnName = "position_id")
    private Position position ;   
    
    @NotNull(message = "กำหนดบริษัทผู้ใช้งาน"  ) 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;     

    public BinaryBalanceDefKey(){}
    public BinaryBalanceDefKey(Position position , Company company){
        this.company = company ;
        this.position = position ;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }  

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.position != null ? this.position.hashCode() : 0);
        hash = 23 * hash + (this.company != null ? this.company.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BinaryBalanceDefKey other = (BinaryBalanceDefKey) obj;
        if (this.position != other.position && (this.position == null || !this.position.equals(other.position))) {
            return false;
        }
        if (this.company != other.company && (this.company == null || !this.company.equals(other.company))) {
            return false;
        }
        return true;
    } 
     
}
