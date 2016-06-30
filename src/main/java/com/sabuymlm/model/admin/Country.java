package com.sabuymlm.model.admin;

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.utils.FileEntry;
import java.io.Serializable; 
import javax.persistence.*; 

@Table(name = "country")
@Entity   
public class Country implements Serializable {

    @Id
    @TableGenerator(name = "CountryGen", allocationSize = 1, initialValue = 1, table = "SEQUENCE_TABLE", pkColumnValue = "Country" ,valueColumnName="SEQ_COUNT",pkColumnName="SEQ_NAME")
    @GeneratedValue(generator = "CountryGen",strategy= GenerationType.TABLE)
    @Column(name = "country_id", columnDefinition ="int" , nullable=false)
    private int id;
    @Column(name = "country_name" , length=100 , nullable = false )
    private String name; 
    @Column(name = "country_engname" , length=100 , nullable = false )
    private String engname; 
    @Column(name = "country_code" , length=50 , nullable = true )
    private String code; 
    @Column(name = "locale" , length=10 , nullable = true )
    private String locale;   
    @Column(name = "icon" , length=200 , nullable = true )
    private String icon;   
    
    @Column(name = "lat" )
    private Double lat; 
    @Column(name = "lng" )
    private Double lng;

    public Country() {
    }

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    
    public String getCountryPath(){
        return SecurityUtil.getUserDetails().getCompany().getUploadPath() + "/" + FileEntry.ADMIN_COUNTRY_FOLDER ;
    }
    
    public String getImageIcon(){
        if(icon ==null){
            return "" ;
        }else {
            return "/ImageServletUtil?imagePath=" + getCountryPath()+"/"+icon;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.id;
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
        final Country other = (Country) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
 
     
    @Override
    public String toString() {
        return "Country{" + "id=" + id + ", name=" + name + ", locale=" + locale + ", icon=" + icon + '}';
    }
    
}
