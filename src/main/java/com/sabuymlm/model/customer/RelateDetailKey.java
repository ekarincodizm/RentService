package com.sabuymlm.model.customer;
  
import java.io.Serializable;
import javax.persistence.*;

@Embeddable 
public class RelateDetailKey implements Serializable {
    
    @Column(name = "list_no" )
    private Integer no;
    
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "treaty_rule_id" )
    private TreatyRule treatyRule;

    public RelateDetailKey(){}
    public RelateDetailKey(Integer no , TreatyRule treatyRule){
        this.no = no ;
        this.treatyRule = treatyRule ;
    }
    
    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public TreatyRule getTreatyRule() {
        return treatyRule;
    }

    public void setTreatyRule(TreatyRule treatyRule) {
        this.treatyRule = treatyRule;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.no != null ? this.no.hashCode() : 0);
        hash = 79 * hash + (this.treatyRule != null ? this.treatyRule.hashCode() : 0);
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
        final RelateDetailKey other = (RelateDetailKey) obj;
        if (this.no != other.no && (this.no == null || !this.no.equals(other.no))) {
            return false;
        }
        if (this.treatyRule != other.treatyRule && (this.treatyRule == null || !this.treatyRule.equals(other.treatyRule))) {
            return false;
        }
        return true;
    } 
     
}
