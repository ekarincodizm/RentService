package com.sabuymlm.model.admin;

import java.io.Serializable;
import javax.persistence.*;  

@Table(name = "province")
@Entity
public class Province implements Serializable {
    @Id
    @TableGenerator(name = "ProvinceGen", allocationSize = 1, initialValue = 1, table = "SEQUENCE_TABLE", pkColumnValue = "Province" ,valueColumnName="SEQ_COUNT",pkColumnName="SEQ_NAME")
    @GeneratedValue(generator = "ProvinceGen",strategy= GenerationType.TABLE)
    @Column(name = "province_id", columnDefinition = "int", nullable=false)
    private Integer id;
    @Column(name = "province_name" , length=100 )
    private String name; 
    @Column(name = "province_engname" , length=100 )
    private String engname; 
    @Column(name = "lat"  )
    private Double lat; 
    @Column(name = "lng"  )
    private Double lng; 

    @ManyToOne(fetch= FetchType.LAZY )
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private Country country;

    public Province(){
        
    }
    public Province(Integer id, String name){
        this.id = id ;
        this.name = name ;
    } 

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
        final Province other = (Province) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    
    @Override
    public String toString() {
        return "Province[id=" + id + " , " + name + "]";
    }

}
