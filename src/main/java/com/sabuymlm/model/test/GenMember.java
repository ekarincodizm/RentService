package com.sabuymlm.model.test;
  
import com.sabuymlm.model.systemTest.*;
import com.sabuymlm.model.admin.*;  
import java.io.Serializable;  
import javax.persistence.*;   

@Table(name = "member_gen", schema = "Test" )
@Entity 
public class GenMember implements Serializable {

    @Id 
    @Column(name = "id", columnDefinition = "bigint", nullable = false)
    private Long id;
    
    @Column(name = "level_gen", columnDefinition = "int")
    private Integer levelGen;
    @Column(name = "upline_id", columnDefinition = "bigint" )
    private Long uplineId;
    @Column(name = "sponsor_id", columnDefinition = "bigint" )
    private Long sponsorId;
    @Column(name = "align", columnDefinition = "int" )
    private Integer align; 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", referencedColumnName = "position_id")
    private Position position ;     
    @Column(name = "topup_pv", columnDefinition = "float" )
    private Float topupPv;  
    
    @Column(name = "total_member", columnDefinition = "bigint" )
    private Long totalMember;
    @Column(name = "organization_pv", columnDefinition = "float" )
    private Float organizationPv;     
    @Column(name = "total_sponsor_count", columnDefinition = "bigint" )
    private Long totalSponsorCount;
    @Column(name = "organization_sponsor_pv", columnDefinition = "float" )
    private Float organizationSponsorPv;     
    @Column(name = "sponsor_bonus", columnDefinition = "float" )
    private Float sponsorBonus;  
    @Column(name = "sponsor_pro_bonus", columnDefinition = "float" )
    private Float sponsorProBonus;  
    @Column(name = "weak_bonus", columnDefinition = "float" )
    private Float weakBonus;  
    @Column(name = "strong_bonus", columnDefinition = "float" )
    private Float strongBonus;  
    @Column(name = "ws_bonus", columnDefinition = "float" )
    private Float wsBonus;   
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getOrganizationSponsorPv() {
        return organizationSponsorPv;
    }

    public void setOrganizationSponsorPv(Float organizationSponsorPv) {
        this.organizationSponsorPv = organizationSponsorPv;
    }

    public Integer getLevelGen() {
        return levelGen;
    }

    public void setLevelGen(Integer levelGen) {
        this.levelGen = levelGen;
    }

    public Long getUplineId() {
        return uplineId;
    }

    public void setUplineId(Long uplineId) {
        this.uplineId = uplineId;
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public Integer getAlign() {
        return align;
    }

    public void setAlign(Integer align) {
        this.align = align;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Float getTopupPv() {
        return topupPv;
    }

    public void setTopupPv(Float topupPv) {
        this.topupPv = topupPv;
    }

    public Long getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Long totalMember) {
        this.totalMember = totalMember;
    }

    public Float getOrganizationPv() {
        return organizationPv;
    }

    public void setOrganizationPv(Float organizationPv) {
        this.organizationPv = organizationPv;
    }

    public Long getTotalSponsorCount() {
        return totalSponsorCount;
    }

    public void setTotalSponsorCount(Long totalSponsorCount) {
        this.totalSponsorCount = totalSponsorCount;
    }

    public Float getSponsorBonus() {
        return sponsorBonus;
    }

    public void setSponsorBonus(Float sponsorBonus) {
        this.sponsorBonus = sponsorBonus;
    }

    public Float getSponsorProBonus() {
        return sponsorProBonus;
    }

    public void setSponsorProBonus(Float sponsorProBonus) {
        this.sponsorProBonus = sponsorProBonus;
    }

    public Float getWeakBonus() {
        return weakBonus;
    }

    public void setWeakBonus(Float weakBonus) {
        this.weakBonus = weakBonus;
    }

    public Float getStrongBonus() {
        return strongBonus;
    }

    public void setStrongBonus(Float strongBonus) {
        this.strongBonus = strongBonus;
    }

    public Float getWsBonus() {
        return wsBonus;
    }

    public void setWsBonus(Float wsBonus) {
        this.wsBonus = wsBonus;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final GenMember other = (GenMember) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    } 
 
}
