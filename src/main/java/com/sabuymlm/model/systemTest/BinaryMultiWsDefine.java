package com.sabuymlm.model.systemTest;
  
import com.sabuymlm.model.admin.*;  
import java.io.Serializable;   
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;  
import javax.validation.constraints.NotNull;  

@Table(name = "binary_multi_define", schema = "TestSystem" )
@Entity 
public class BinaryMultiWsDefine extends CommonEntity implements Serializable {
 
    @EmbeddedId
    private BinaryMultiWsDefKey id ;   
    
    @NotNull(message = "เลือกผังโครงสร้าง"  ) 
    @Column(name = "power_charts", columnDefinition = "int", nullable = false)
    private Integer powerCharts;
    
    @NotNull(message = "กรอก MAX คอมฯ/รอบ"  ) 
    @Column(name = "max_comm", columnDefinition = "float", nullable = false)
    private Float maxComm;  
    
    @NotNull(message = "เลือกรอบคำนวณ"  ) 
    @Column(name = "circle_unit", columnDefinition = "int", nullable = false)
    private Integer circleUnit; 
    
    @OneToMany(mappedBy = "id.masterId",  fetch = FetchType.LAZY , cascade = {CascadeType.ALL} , orphanRemoval = true )
    private List<BinaryMultiWsDetail> items = new ArrayList<BinaryMultiWsDetail>();   

    public String getCircleUnitStr(){
        return circleUnit + "" ;
    }
    
    public BinaryMultiWsDefKey getId() {
        return id;
    }

    public void setId(BinaryMultiWsDefKey id) {
        this.id = id;
    }   

    public Integer getPowerCharts() {
        return powerCharts;
    }

    public void setPowerCharts(Integer powerCharts) {
        this.powerCharts = powerCharts;
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

    public List<BinaryMultiWsDetail> getItems() {
        return items;
    }

    public void setItems(List<BinaryMultiWsDetail> items) {
        this.items = items;
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
        final BinaryMultiWsDefine other = (BinaryMultiWsDefine) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    } 
 
    public BinaryMultiWsDetail findKeyId(BinaryMultiWsDetailKey key){
        BinaryMultiWsDetail ret = null ;
        for( BinaryMultiWsDetail detail : items ){
            if(detail.getId().equals(key)){
                ret =  detail ;
                break;
            }
        }
        return ret ;
    }

    @Override
    public String toString() {
        return "BinaryMultiWsDefine{" + "id=" + id + '}';
    }
    
}
