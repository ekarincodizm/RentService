package com.sabuymlm.model.admin;

import java.io.Serializable;
import javax.persistence.*;
import org.zkoss.bind.annotation.NotifyChange;

@Table(name = "district")
@Entity
public class District implements Serializable {

   @Id
    @TableGenerator(name = "DistrictGen", allocationSize = 1, initialValue = 1, table = "SEQUENCE_TABLE", pkColumnValue = "District" ,valueColumnName="SEQ_COUNT",pkColumnName="SEQ_NAME")
    @GeneratedValue(generator = "DistrictGen",strategy= GenerationType.TABLE)
    @Column(name = "district_id", nullable=false)  
    private Integer id;
    @Column(name = "district_name" , length=100 )
    private String name;
    @Column(name = "district_engname" , length=100 )
    private String engname;
    @Column(name = "post" ,length=10 )
    private String post; 
    @Column(name = "lat" )
    private Double lat; 
    @Column(name = "lng" )
    private Double lng;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private Country country;
    
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "province_id", referencedColumnName = "province_id")
    private Province province;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Province getProvince() {
        return province;
    }
 
    @NotifyChange(".")
    public void setProvince(Province province) {
        this.province = province;
    }

    public String getEngname() {
        return engname;
    }

    public void setEngname(String engname) {
        this.engname = engname;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    } 

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final District other = (District) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public String toString() {
        return "District[id=" + id + " , " + name + "]";
    }
}
