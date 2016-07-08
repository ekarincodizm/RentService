package com.sabuymlm.model.systemTest;
  
import com.sabuymlm.model.admin.*;  
import java.io.Serializable;   
import javax.persistence.*;  
import javax.validation.constraints.NotNull;  

@Table(name = "binary_balance_define", schema = "TestSystem" )
@Entity 
public class BinaryBalanceDefine extends CommonEntity implements Serializable {
 
    @EmbeddedId
    private BinaryBalanceDefKey id ;  
 
    @NotNull(message = "กรอก PV(ต่อคู่)"  ) 
    @Column(name = "weak_balance", columnDefinition = "float", nullable = false)
    private Float wsBalance;    
    @NotNull(message = "กรอก โบนัส(ต่อคู่)"  ) 
    @Column(name = "weak_comm", columnDefinition = "float", nullable = false)
    private Float wkComm;     
    @NotNull(message = "กรอก จำนวนคู่(สูงสุด)"  ) 
    @Column(name = "max_unit", columnDefinition = "int", nullable = false)
    private Integer maxUnit;
    
    @NotNull(message = "กรอก MAX PV/รอบ"  ) 
    @Column(name = "max_pv", columnDefinition = "float", nullable = false)
    private Float maxPv;  
    
    @NotNull(message = "กรอก MAX คอมฯ/รอบ"  ) 
    @Column(name = "max_comm", columnDefinition = "float", nullable = false)
    private Float maxComm;  
    
    @NotNull(message = "เลือกรอบคำนวณ"  ) 
    @Column(name = "circle_unit", columnDefinition = "int", nullable = false)
    private Integer circleUnit;

    public String getCircleUnitStr(){
        return circleUnit + "" ;
    }
    
    public BinaryBalanceDefKey getId() {
        return id;
    }

    public void setId(BinaryBalanceDefKey id) {
        this.id = id;
    }  

    public Float getWsBalance() {
        return wsBalance;
    }

    public void setWsBalance(Float wsBalance) {
        this.wsBalance = wsBalance;
    }

    public Float getWkComm() {
        return wkComm;
    }

    public void setWkComm(Float wkComm) {
        this.wkComm = wkComm;
    }

    public Integer getMaxUnit() {
        return maxUnit;
    }

    public void setMaxUnit(Integer maxUnit) {
        this.maxUnit = maxUnit;
    }

    public Float getMaxPv() {
        return maxPv;
    }

    public void setMaxPv(Float maxPv) {
        this.maxPv = maxPv;
    }

    public Float getMaxComm() {
        return maxComm;
    }

    public void setMaxComm(Float maxComm) {
        this.maxComm = maxComm;
    }

    public Integer getCircleUnit() {
        return circleUnit;
    }

    public void setCircleUnit(Integer circleUnit) {
        this.circleUnit = circleUnit;
    }    
    
    public String getStyle(){
        if(getUpdateUser() != null && getUpdateUser() instanceof User ) {
            return "color:blue;"; 
        }else {
            return "color:green;";
        }
    } 

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final BinaryBalanceDefine other = (BinaryBalanceDefine) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    } 

    @Override
    public String toString() {
        return "BinaryBalanceDefine{" + "wsBalance=" + wsBalance + ", wkComm=" + wkComm + ", maxUnit=" + maxUnit + ", maxPv=" + maxPv + ", maxComm=" + maxComm + ", circleUnit=" + circleUnit + '}';
    } 
 
}
