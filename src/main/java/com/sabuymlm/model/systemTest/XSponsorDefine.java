package com.sabuymlm.model.systemTest;
   
import java.io.Serializable;   
import javax.persistence.*;  
import javax.validation.constraints.NotNull;  

@Table(name = "x_sponsor_define", schema = "TestSystem" )
@Entity 
public class XSponsorDefine implements Serializable {
 
    @EmbeddedId
    private XSponsorDefKey id ;   
 
    @NotNull(message = "กรอก%จ่าย"  ) 
    @Column(name = "pcent", columnDefinition = "float", nullable = false)
    private Float pcent;    
    @NotNull(message = "กรอก%จ่าย(XDiff)"  ) 
    @Column(name = "pcent_diff", columnDefinition = "float", nullable = false)
    private Float pcentDiff;  

    public String getStyle(){
        return "color:blue;";  
    } 
    
    public XSponsorDefKey getId() {
        return id;
    }

    public void setId(XSponsorDefKey id) {
        this.id = id;
    }   

    public Float getPcent() {
        return pcent;
    }

    public void setPcent(Float pcent) {
        this.pcent = pcent;
    }

    public Float getPcentDiff() {
        return pcentDiff;
    }

    public void setPcentDiff(Float pcentDiff) {
        this.pcentDiff = pcentDiff;
    }
  

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final XSponsorDefine other = (XSponsorDefine) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    } 
 
}
