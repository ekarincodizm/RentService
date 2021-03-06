package com.sabuymlm.model.systemTest;
  
import com.sabuymlm.model.admin.*;  
import java.io.Serializable;   
import javax.persistence.*;  
import javax.validation.constraints.NotNull; 
import org.hibernate.validator.constraints.NotEmpty;

@Table(name = "sponsor_define", schema = "TestSystem" )
@Entity 
public class SponsorDefine  extends CommonEntity  implements Serializable {
 
    @EmbeddedId
    private SponsorDefKey id ;  
    
    @NotEmpty(message = "ชื่อโบนัส(ห้ามเป็นค่าว่าง)" )
    @Column(name = "sponsor_name", length = 100, nullable = false)
    private String name;
 
    @NotNull(message = "กรอก%จ่าย"  ) 
    @Column(name = "pcent", columnDefinition = "float", nullable = false)
    private Float pcent;    
    @NotNull(message = "กรอก%จ่าย(Pro)"  ) 
    @Column(name = "pcent_pro", columnDefinition = "float", nullable = false)
    private Float pcentPro;      

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;     

    
    
    public SponsorDefKey getId() {
        return id;
    }

    public void setId(SponsorDefKey id) {
        this.id = id;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPcent() {
        return pcent;
    }

    public void setPcent(Float pcent) {
        this.pcent = pcent;
    }

    public Float getPcentPro() {
        return pcentPro;
    }

    public void setPcentPro(Float pcentPro) {
        this.pcentPro = pcentPro;
    }  

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        final SponsorDefine other = (SponsorDefine) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
 
 
}
