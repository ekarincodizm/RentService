package com.sabuymlm.model.systemTest;
    
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Embeddable 
public class BinaryMultiWsDetailKey implements Serializable {
     
    private BinaryMultiWsDefKey masterId ; 
    
    @NotNull(message = "เลือกผังโครงสร้าง"  ) 
    @Column(name = "level_no", columnDefinition = "int", nullable = false)
    private Integer levelNo; 

    public BinaryMultiWsDetailKey(){}
    public BinaryMultiWsDetailKey(BinaryMultiWsDefKey masterId , Integer levelNo){
        this.masterId = masterId ;
        this.levelNo = levelNo ;
    } 

    public BinaryMultiWsDefKey getMasterId() {
        return masterId;
    }

    public void setMasterId(BinaryMultiWsDefKey masterId) {
        this.masterId = masterId;
    }

    public Integer getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(Integer levelNo) {
        this.levelNo = levelNo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.masterId != null ? this.masterId.hashCode() : 0);
        hash = 41 * hash + (this.levelNo != null ? this.levelNo.hashCode() : 0);
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
        final BinaryMultiWsDetailKey other = (BinaryMultiWsDetailKey) obj;
        if (this.masterId != other.masterId && (this.masterId == null || !this.masterId.equals(other.masterId))) {
            return false;
        }
        if (this.levelNo != other.levelNo && (this.levelNo == null || !this.levelNo.equals(other.levelNo))) {
            return false;
        }
        return true;
    }
    
    
}
