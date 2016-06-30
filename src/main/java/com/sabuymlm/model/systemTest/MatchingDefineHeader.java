package com.sabuymlm.model.systemTest;
  
import com.sabuymlm.model.admin.*;  
import java.io.Serializable;  
import java.util.List;
import javax.persistence.*;   
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.transaction.annotation.Transactional;

@Table(name = "matching_define_header", schema = "TestSystem" )
@Entity
@Transactional(readOnly = true)
public class MatchingDefineHeader implements Serializable {
 
    @Id
    @Column(name = "level_index", columnDefinition = "int" )
    private Integer levelIndex; 
    
    @NotEmpty(message = "ชื่อโบนัส(ห้ามเป็นค่าว่าง)" )
    @Column(name = "matching_name", length = 100, nullable = false)
    private String name; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;   
     
    @OneToMany(mappedBy = "id.levelIndex",  fetch = FetchType.LAZY)
    private List<MatchingDefine> items;

    public String getStyle(){
        return "color:blue;";  
    } 
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }  

    public Integer getLevelIndex() {
        return levelIndex;
    }

    public void setLevelIndex(Integer levelIndex) {
        this.levelIndex = levelIndex;
    }

    public List<MatchingDefine> getItems() {
        return items;
    }

    public void setItems(List<MatchingDefine> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.levelIndex != null ? this.levelIndex.hashCode() : 0);
        hash = 67 * hash + (this.company != null ? this.company.hashCode() : 0);
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
        final MatchingDefineHeader other = (MatchingDefineHeader) obj;
        if (this.levelIndex != other.levelIndex && (this.levelIndex == null || !this.levelIndex.equals(other.levelIndex))) {
            return false;
        }
        if (this.company != other.company && (this.company == null || !this.company.equals(other.company))) {
            return false;
        }
        return true;
    }  
 
}
