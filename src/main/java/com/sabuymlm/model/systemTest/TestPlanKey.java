package com.sabuymlm.model.systemTest;
    
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Embeddable 
public class TestPlanKey implements Serializable {
     
    @NotNull(message = "กำหนด Plan Index"  ) 
    @Column(name = "plan_id", columnDefinition = "int" , nullable = false ) 
    private Integer no ;   
    
    @NotNull(message = "กำหนดบริษัทผู้ใช้งาน"  ) 
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "company_id", referencedColumnName = "company_id"  ,nullable = false     )
    private TestPlanHeader planHeader;     

    public TestPlanKey(){}
    public TestPlanKey(Integer no , TestPlanHeader planHeader){
        this.planHeader = planHeader ;
        this.no = no ;
    }

    public TestPlanHeader getPlanHeader() {
        return planHeader;
    }

    public void setPlanHeader(TestPlanHeader planHeader) {
        this.planHeader = planHeader;
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
        hash = 23 * hash + (this.no != null ? this.no.hashCode() : 0);
        hash = 23 * hash + (this.planHeader != null ? this.planHeader.hashCode() : 0);
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
        if (this.planHeader != other.planHeader && (this.planHeader == null || !this.planHeader.equals(other.planHeader))) {
            return false;
        }
        return true;
    }
 
}
