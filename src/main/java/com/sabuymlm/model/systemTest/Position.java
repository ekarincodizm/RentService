package com.sabuymlm.model.systemTest;
  
import com.sabuymlm.model.admin.*;  
import com.sabuymlm.model.test.GenMember;
import java.io.Serializable;  
import java.util.ArrayList;
import java.util.List;
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
     
    @Column(name = "mta_baht", columnDefinition = "float", nullable = true)
    private Float mtaBaht;  
    @Column(name = "auto_pcent", columnDefinition = "float", nullable = true)
    private Float autoPcent;  
    @Column(name = "auto_max", columnDefinition = "float", nullable = true)
    private Float autoMax;  
    
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
    
    @OneToMany(mappedBy = "id.position", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<SponsorDefine> sponsorDefs  ;
    @OneToMany(mappedBy = "id.position", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<BinaryWsDefine> binaryWsDefines  ;
    @OneToMany(mappedBy = "id.position", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<BinaryBalanceDefine> binaryWsBlDefines  ;
    @OneToMany(mappedBy = "id.position", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<BinaryMultiWsDefine> binaryMultiWsDefines  ;
    @OneToMany(mappedBy = "id.position", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<MatchingDefine> matchingDefines  ;
    @OneToMany(mappedBy = "id.position", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<XSponsorDefineHeader> xSponsorDefineHeaders  ; 
    @OneToMany(mappedBy = "id.position", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<InvestmentDefine> investmentDefines  ; 
    @OneToMany(mappedBy = "id.xposition", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<XSponsorDefine> xSponsorDefines  ; 
    @OneToMany(mappedBy = "id.position", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<UnilevelDefine> unilevelDefines  ;
    
    @OneToOne(mappedBy = "position", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE} , orphanRemoval = true)
    private TestPlanHeader testPlanHeader  ;
    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE} , orphanRemoval = true)
    private List<GenMember> genMembers ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    } 

    public List<InvestmentDefine> getInvestmentDefines() {
        return investmentDefines;
    }

    public void setInvestmentDefines(List<InvestmentDefine> investmentDefines) {
        this.investmentDefines = investmentDefines;
    }

    public List<GenMember> getGenMembers() {
        return genMembers;
    }

    public void setGenMembers(List<GenMember> genMembers) {
        this.genMembers = genMembers;
    }  

    public TestPlanHeader getTestPlanHeader() {
        return testPlanHeader;
    }

    public void setTestPlanHeader(TestPlanHeader testPlanHeader) {
        this.testPlanHeader = testPlanHeader;
    }

    public List<XSponsorDefine> getxSponsorDefines() {
        return xSponsorDefines;
    }

    public void setxSponsorDefines(List<XSponsorDefine> xSponsorDefines) {
        this.xSponsorDefines = xSponsorDefines;
    }

    public List<UnilevelDefine> getUnilevelDefines() {
        return unilevelDefines;
    }

    public void setUnilevelDefines(List<UnilevelDefine> unilevelDefines) {
        this.unilevelDefines = unilevelDefines;
    }

    public List<BinaryMultiWsDefine> getBinaryMultiWsDefines() {
        return binaryMultiWsDefines;
    }

    public void setBinaryMultiWsDefines(List<BinaryMultiWsDefine> binaryMultiWsDefines) {
        this.binaryMultiWsDefines = binaryMultiWsDefines;
    }

    public List<MatchingDefine> getMatchingDefines() {
        return matchingDefines;
    }

    public void setMatchingDefines(List<MatchingDefine> matchingDefines) {
        this.matchingDefines = matchingDefines;
    } 

    public List<XSponsorDefineHeader> getxSponsorDefineHeaders() {
        return xSponsorDefineHeaders;
    }

    public void setxSponsorDefineHeaders(List<XSponsorDefineHeader> xSponsorDefineHeaders) {
        this.xSponsorDefineHeaders = xSponsorDefineHeaders;
    } 

    public List<BinaryBalanceDefine> getBinaryWsBlDefines() {
        return binaryWsBlDefines;
    }

    public void setBinaryWsBlDefines(List<BinaryBalanceDefine> binaryWsBlDefines) {
        this.binaryWsBlDefines = binaryWsBlDefines;
    }

    public List<BinaryWsDefine> getBinaryWsDefines() {
        return binaryWsDefines;
    }

    public void setBinaryWsDefines(List<BinaryWsDefine> binaryWsDefines) {
        this.binaryWsDefines = binaryWsDefines;
    }

    public List<SponsorDefine> getSponsorDefs() {
        return sponsorDefs;
    }

    public void setSponsorDefs(List<SponsorDefine> sponsorDefs) {
        this.sponsorDefs = sponsorDefs;
    }

    public Float getMtaBaht() {
        return mtaBaht;
    }

    public void setMtaBaht(Float mtaBaht) {
        this.mtaBaht = mtaBaht;
    }

    public Float getAutoPcent() {
        return autoPcent;
    }

    public void setAutoPcent(Float autoPcent) {
        this.autoPcent = autoPcent;
    }

    public Float getAutoMax() {
        return autoMax;
    }

    public void setAutoMax(Float autoMax) {
        this.autoMax = autoMax;
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
