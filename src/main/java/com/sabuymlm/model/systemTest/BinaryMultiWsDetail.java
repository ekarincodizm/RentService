package com.sabuymlm.model.systemTest;
    
import java.io.Serializable;    
import javax.persistence.*;  
import javax.validation.constraints.NotNull;  

@Table(name = "binary_multi_detail_define", schema = "TestSystem" )
@Entity 
public class BinaryMultiWsDetail implements Serializable {
 
    @EmbeddedId
    private BinaryMultiWsDetailKey id ;   
    
    @NotNull(message = "กรอก%จ่าย(Weak Team)"  ) 
    @Column(name = "weak_pcent", columnDefinition = "float", nullable = false)
    private Float wsPcent;    
    @NotNull(message = "กรอก%จ่าย(Strong Team)"  ) 
    @Column(name = "strong_pcent", columnDefinition = "float", nullable = false)
    private Float stPcent; 

    public BinaryMultiWsDetailKey getId() {
        return id;
    }

    public void setId(BinaryMultiWsDetailKey id) {
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
        final BinaryMultiWsDetail other = (BinaryMultiWsDetail) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }  
    
}
