package com.sabuymlm.model.systemTest;
  
import com.sabuymlm.model.admin.*;  
import java.io.Serializable;  
import javax.persistence.*;  
import javax.validation.constraints.NotNull; 
import org.hibernate.validator.constraints.NotEmpty;

@Table(name = "member_position", schema = "TestSystem" )
@Entity 
public class Position extends CommonEntity implements Serializable {

    @Id
    @TableGenerator(name = "PositionGen", allocationSize = 1, initialValue = 1, table = "SEQUENCE_TABLE", pkColumnValue = "Position", valueColumnName = "SEQ_COUNT", pkColumnName = "SEQ_NAME")
    @GeneratedValue(generator = "PositionGen", strategy = GenerationType.TABLE)
    @Column(name = "position_id", columnDefinition = "int", nullable = false)
    private Integer id;
    @NotEmpty(message = "ชื่อตำแหน่ง(ห้ามเป็นค่าว่าง)" )
    @Column(name = "position_name", length = 100, nullable = false)
    private String name;
 
    @NotNull(message = "กรอกคะแนนสะสม(TOP-UP PV)"  ) 
    @Column(name = "topup_pv", columnDefinition = "float", nullable = false)
    private Float topupPv;  
    
    @Column(name = "sponsor_or_and_state", columnDefinition = "varchar(5)" )
    private String sponsorOrAndState; 
    @Column(name = "sponsor_count", columnDefinition = "int" )
    private Integer sponsorCount; 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_position_id", referencedColumnName = "position_id")
    private Position memberPosition ;  
    @Column(name = "is_fix_sponsor_level", columnDefinition = "varchar(1)" )
    private String isFixSponsorLevel;
    @Column(name = "sponsor_level", columnDefinition = "int" )
    private Integer sponsorLevel;   
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;   

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

    public Float getTopupPv() {
        return topupPv;
    }

    public void setTopupPv(Float topupPv) {
        this.topupPv = topupPv;
    }

    public String getSponsorOrAndState() {
        return sponsorOrAndState;
    }

    public void setSponsorOrAndState(String sponsorOrAndState) {
        this.sponsorOrAndState = sponsorOrAndState;
    }

    public Integer getSponsorCount() {
        return sponsorCount;
    }

    public void setSponsorCount(Integer sponsorCount) {
        this.sponsorCount = sponsorCount;
    }

    public Position getMemberPosition() {
        return memberPosition;
    }

    public void setMemberPosition(Position memberPosition) {
        this.memberPosition = memberPosition;
    }

    public String getIsFixSponsorLevel() {
        return isFixSponsorLevel;
    }

    public void setIsFixSponsorLevel(String isFixSponsorLevel) {
        this.isFixSponsorLevel = isFixSponsorLevel;
    }

    public Integer getSponsorLevel() {
        return sponsorLevel;
    }

    public void setSponsorLevel(Integer sponsorLevel) {
        this.sponsorLevel = sponsorLevel;
    } 

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }  
    
    public String getStyle(){
        if(memberPosition != null && memberPosition instanceof Position ) {
            return "color:blue;"; 
        }else {
            return "color:green;";
        }
    } 

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Position other = (Position) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
 
}