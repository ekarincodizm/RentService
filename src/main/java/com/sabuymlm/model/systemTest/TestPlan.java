package com.sabuymlm.model.systemTest;
   
import java.io.Serializable;  
import javax.persistence.*;   

@Table(name = "test_plan", schema = "TestSystem" )
@Entity 
public class TestPlan implements Serializable {

    @EmbeddedId
    private TestPlanKey id ;  
    
    @Column(name = "chk_pay", length = 10, nullable = false)
    private String chkPay; 
    @Column(name = "plan_name", length = 300, nullable = false)
    private String planName; 
    
    @Column(name = "pass_matching_weak_or_bonus", length = 10, nullable = false)
    private String passMatchingWeakOrBonus; 
    @Column(name = "pass_matching_strong", length = 10, nullable = false)
    private String passMatchingStrong; 
    
    @Column(name = "chk_auto", length = 10, nullable = true)
    private String chkAuto; 

    public String getPassMatchingWeakOrBonus() {
        return passMatchingWeakOrBonus;
    }

    public void setPassMatchingWeakOrBonus(String passMatchingWeakOrBonus) {
        this.passMatchingWeakOrBonus = passMatchingWeakOrBonus;
    }

    public String getPassMatchingStrong() {
        return passMatchingStrong;
    }

    public void setPassMatchingStrong(String passMatchingStrong) {
        this.passMatchingStrong = passMatchingStrong;
    }

    public String getChkAuto() {
        return chkAuto;
    }

    public void setChkAuto(String chkAuto) {
        this.chkAuto = chkAuto;
    }

    public TestPlanKey getId() {
        return id;
    }

    public void setId(TestPlanKey id) {
        this.id = id;
    }

    public String getChkPay() {
        return chkPay;
    }

    public void setChkPay(String chkPay) {
        this.chkPay = chkPay;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final TestPlan other = (TestPlan) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TestPlan{" + "id=" + id + ", chkPay=" + chkPay + '}';
    }  
}
