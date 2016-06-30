/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.model.systemTest;

/**
 *
 * @author MY-TENG
 */
   
import com.sabuymlm.model.admin.User;
import java.io.Serializable;  
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;   
import javax.validation.constraints.NotNull;  

@Table(name = "x_sponsor_header", schema = "TestSystem" )
@Entity 
public class XSponsorDefineHeader extends CommonEntity  implements Serializable { 
    @EmbeddedId 
    private XSponsorHeaderKey id;  
   
    @NotNull(message = "ห้ามเป็นค่าว่าง"  ) 
    @Column(name = "bonus", columnDefinition = "float" )
    private Float bonus;   
     
    @OneToMany(mappedBy = "id.headerId",  fetch = FetchType.LAZY , cascade = {CascadeType.ALL})
    private List<XSponsorDefine> items = new ArrayList<XSponsorDefine>();  

    public XSponsorHeaderKey getId() {
        return id;
    }

    public void setId(XSponsorHeaderKey id) {
        this.id = id;
    }

    public Float getBonus() {
        return bonus;
    }

    public void setBonus(Float bonus) {
        this.bonus = bonus;
    } 

    public List<XSponsorDefine> getItems() {
        return items;
    }

    public void setItems(List<XSponsorDefine> items) {
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
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final XSponsorDefineHeader other = (XSponsorDefineHeader) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
     
}
