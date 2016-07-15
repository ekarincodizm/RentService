package com.sabuymlm.model.systemTest;
   
import com.sabuymlm.model.admin.Company;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Embeddable 
public class TestPlanKey implements Serializable {
     
    @NotNull(message = "กำหนด Plan Index"  ) 
    @Column(name = "plan_id", columnDefinition = "int" , nullable = false ) 
    private Integer no ;   
    
    @NotNull(message = "กำหนดบริษัทผู้ใช้งาน"  ) 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id"  ,nullable = false )
    private Company company;     

    public TestPlanKey(){}
    public TestPlanKey(Integer no , Company company){
        this.company = company ;
        this.no = no ;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }  

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.no != null ? this.no.hashCode() : 0);
        hash = 97 * hash + (this.company != null ? this.company.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TestPlanKey other = (TestPlanKey) obj;
        if (this.no != other.no && (this.no == null || !this.no.equals(other.no))) {
            return false;
        }
        if (this.company != other.company && (this.company == null || !this.company.equals(other.company))) {
            return false;
        }
        return true;
    } 

    @Override
    public String toString() {
        return "TestPlanKey{" + "no=" + no + '}';
    } 
}
