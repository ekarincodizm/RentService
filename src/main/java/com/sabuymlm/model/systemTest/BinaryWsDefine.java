package com.sabuymlm.model.systemTest;
  
import com.sabuymlm.model.admin.*;  
import java.io.Serializable;   
import javax.persistence.*;  
import javax.validation.constraints.NotNull;  

@Table(name = "binary_ws_define", schema = "TestSystem" )
@Entity 
public class BinaryWsDefine extends CommonEntity implements Serializable {
 
    @EmbeddedId
    private BinaryWsDefKey id ;  
 
    @NotNull(message = "กรอก%จ่าย(Weak Team)"  ) 
    @Column(name = "weak_pcent", columnDefinition = "float", nullable = false)
    private Float wsPcent;    
    @NotNull(message = "กรอก%จ่าย(Strong Team)"  ) 
    @Column(name = "strong_pcent", columnDefinition = "float", nullable = false)
    private Float stPcent;    
    
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
    
    public BinaryWsDefKey getId() {
        return id;
    }

    public void setId(BinaryWsDefKey id) {
        this.id = id;
    }  

    public Float getWsPcent() {
        return wsPcent;
    }

    public void setWsPcent(Float wsPcent) {
        this.wsPcent = wsPcent;
    }

    public Float getStPcent() {
        return stPcent;
    }

    public void setStPcent(Float stPcent) {
        this.stPcent = stPcent;
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
        final BinaryWsDefine other = (BinaryWsDefine) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    } 

    @Override
    public String toString() {
        return "BinaryWsDefine{" + "wsPcent=" + wsPcent + ", stPcent=" + stPcent + ", maxPv=" + maxPv + ", maxComm=" + maxComm + ", circleUnit=" + circleUnit + '}';
    }
 
}
