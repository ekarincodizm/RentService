package com.sabuymlm.model.systemTest;
  
import com.sabuymlm.model.admin.*;  
import java.io.Serializable;   
import javax.persistence.*;  
import javax.validation.constraints.NotNull;  

@Table(name = "investment_define", schema = "TestSystem" )
@Entity 
public class InvestmentDefine extends CommonEntity implements Serializable {
 
    @EmbeddedId
    private InvestmentDefKey id ;  
 
    @NotNull(message = "กรอก %Rate/วัน"  ) 
    @Column(name = "rate_pcent", columnDefinition = "float", nullable = false)
    private Float ratePcent;    
    @NotNull(message = "โบนัส/วัน"  ) 
    @Column(name = "comm", columnDefinition = "float", nullable = false)
    private Float comm;    
    
    @NotNull(message = "กรอก MAX (Day)"  ) 
    @Column(name = "max_day", columnDefinition = "int", nullable = false)
    private Integer maxDay;  
    
    @NotNull(message = "กรอก MAX คอมฯ/รอบ"  ) 
    @Column(name = "max_comm", columnDefinition = "float", nullable = false)
    private Float maxComm;  
    
    @NotNull(message = "เลือกรอบคำนวณ"  ) 
    @Column(name = "circle_unit", columnDefinition = "int", nullable = false)
    private Integer circleUnit;

    public String getCircleUnitStr(){
        return circleUnit + "" ;
    }

    public InvestmentDefKey getId() {
        return id;
    }

    public void setId(InvestmentDefKey id) {
        this.id = id;
    } 

    public Float getRatePcent() {
        return ratePcent;
    }

    public void setRatePcent(Float ratePcent) {
        this.ratePcent = ratePcent;
    }

    public Float getComm() {
        return comm;
    }

    public void setComm(Float comm) {
        this.comm = comm;
    }

    public Integer getMaxDay() {
        return maxDay;
    }

    public void setMaxDay(Integer maxDay) {
        this.maxDay = maxDay;
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
        final InvestmentDefine other = (InvestmentDefine) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    } 

    @Override
    public String toString() {
        return "InvestmentDefine{" + "id=" + id + ", ratePcent=" + ratePcent + ", comm=" + comm + ", maxDay=" + maxDay + ", maxComm=" + maxComm + ", circleUnit=" + circleUnit + '}';
    }

     
 
}
