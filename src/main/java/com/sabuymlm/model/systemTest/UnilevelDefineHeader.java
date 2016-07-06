package com.sabuymlm.model.systemTest;
  
import com.sabuymlm.model.admin.*;  
import java.io.Serializable;  
import java.util.List;
import javax.persistence.*;    
import org.springframework.transaction.annotation.Transactional;

@Table(name = "unilevel_define_header", schema = "TestSystem" )
@Entity
@Transactional(readOnly = true)
public class UnilevelDefineHeader implements Serializable {
 
    @Id
    @Column(name = "level_index", columnDefinition = "int" )
    private Integer levelIndex;  

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;   
     
    @OneToMany(mappedBy = "id.levelIndex",  fetch = FetchType.LAZY)
    private List<UnilevelDefine> items;

    public String getStyle(){
        return "color:blue;";  
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

    public List<UnilevelDefine> getItems() {
        return items;
    }

    public void setItems(List<UnilevelDefine> items) {
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
        final UnilevelDefineHeader other = (UnilevelDefineHeader) obj;
        if (this.levelIndex != other.levelIndex && (this.levelIndex == null || !this.levelIndex.equals(other.levelIndex))) {
            return false;
        }
        if (this.company != other.company && (this.company == null || !this.company.equals(other.company))) {
            return false;
        }
        return true;
    }  
 
}
