package com.sabuymlm.model.systemTest;

import com.sabuymlm.utils.Format;
import com.sabuymlm.vm.systemTest.LabelValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List; 
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "test_plan_header", schema = "TestSystem")
@Entity
public class TestPlanHeader extends CommonEntity implements Serializable {

    @Id
    @Column(name = "company_id", columnDefinition = "int", nullable = false)
    private Integer companyId;

    @Column(name = "chart_power", columnDefinition = "int")
    private Integer chartPower;
    @Column(name = "chart_sponsor_power", columnDefinition = "int")
    private Integer chartSponsorPower;
    @Column(name = "chart_level", columnDefinition = "int")
    private Integer chartLevel;

    @NotNull(message = "ตำแหน่งที่ต้องการทดสอบ")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", referencedColumnName = "position_id")
    private Position position;

    @Column(name = "advance_total", columnDefinition = "int")
    private Integer advanceTotal;
    @NotNull(message = "ตำแหน่งที่ (Advance)")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advance_position_id", referencedColumnName = "position_id")
    private Position advancePosition;

    @Column(name = "mobile_pcent", columnDefinition = "float")
    private Float mobilePcent;
    @Column(name = "mobile_pcent_from", columnDefinition = "varchar(5)")
    private String mobilePcentFrom;

    @Column(name = "allsale_pcent", columnDefinition = "float")
    private Float allsalePcent;
    @Column(name = "allsale_pcent_from", columnDefinition = "varchar(5)")
    private String allsalePcentFrom;
    @Column(name = "other_pcent", columnDefinition = "float")
    private Float otherPcent;
    @Column(name = "other_pcent_from", columnDefinition = "varchar(5)")
    private String otherPcentFrom;

    @Column(name = "regis_baht", columnDefinition = "float")
    private Float regisBaht;
    @Column(name = "pv_per_baht", columnDefinition = "float")
    private Float pvPerBaht;
    @Column(name = "cost_baht", columnDefinition = "float")
    private Float costBaht;
    @Column(name = "office_rent_baht", columnDefinition = "float")
    private Float officeRentBaht;
    @Column(name = "employee_baht", columnDefinition = "float")
    private Float employeeBaht;
    @Column(name = "meeting_baht", columnDefinition = "float")
    private Float meetingBaht;
    @Column(name = "other_baht", columnDefinition = "float")
    private Float otherBaht;

    @OneToMany(mappedBy = "id.company", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<TestPlan> items = new ArrayList<TestPlan>();

    public TestPlanHeader() {
        this.advanceTotal = 0;
        this.allsalePcent = 0f;
        this.allsalePcentFrom = "PV";
        this.mobilePcent = 0f;
        this.mobilePcentFrom = "PV";
        this.otherPcent = 0f;
        this.otherPcentFrom = "PV";

        this.chartLevel = 2;
        this.chartPower = 2;
        this.chartSponsorPower = 2;

        this.regisBaht = 0f;
        this.pvPerBaht = 1f;
        this.costBaht = 0f;
        this.officeRentBaht = 0f;
        this.employeeBaht = 0f;
        this.otherBaht = 0f;
        this.meetingBaht = 0f;
    }

    public List<TestPlan> getItems() {
        return items;
    }

    public void setItems(List<TestPlan> items) {
        this.items = items;
    } 
    
    public Integer getChartPower() {
        return chartPower;
    }
    
    public String getChartPowerStr() {
        return chartPower + "" ;
    }

    public void setChartPower(Integer chartPower) {
        this.chartPower = chartPower;
    }

    public Integer getChartSponsorPower() {
        return chartSponsorPower;
    }
    public String getChartSponsorPowerStr() {
        return chartSponsorPower + "";
    }

    public void setChartSponsorPower(Integer chartSponsorPower) {
        this.chartSponsorPower = chartSponsorPower;
    }

    public Integer getChartLevel() {
        return chartLevel;
    } 

    public void setChartLevel(Integer chartLevel) {
        this.chartLevel = chartLevel;
    } 
    
    public void setChartLevel(LabelValue labelValue) { 
         this.chartLevel = labelValue.getValue().intValue() ;
    }
    
    public LabelValue getChartLevelValue() { 
        if(chartPower == null || chartLevel == null ) {
            return null ; 
        }else {
            return new LabelValue(Format.formatNumber("#,##0 'รหัส'", Math.pow(chartPower, chartLevel) ) ,chartLevel); 
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Integer getAdvanceTotal() {
        return advanceTotal;
    }

    public void setAdvanceTotal(Integer advanceTotal) {
        this.advanceTotal = advanceTotal;
    }

    public Position getAdvancePosition() {
        return advancePosition;
    }

    public void setAdvancePosition(Position advancePosition) {
        this.advancePosition = advancePosition;
    }

    public Float getMobilePcent() {
        return mobilePcent;
    }

    public void setMobilePcent(Float mobilePcent) {
        this.mobilePcent = mobilePcent;
    }

    public String getMobilePcentFrom() {
        return mobilePcentFrom;
    }

    public void setMobilePcentFrom(String mobilePcentFrom) {
        this.mobilePcentFrom = mobilePcentFrom;
    }

    public Float getAllsalePcent() {
        return allsalePcent;
    }

    public void setAllsalePcent(Float allsalePcent) {
        this.allsalePcent = allsalePcent;
    }

    public String getAllsalePcentFrom() {
        return allsalePcentFrom;
    }

    public void setAllsalePcentFrom(String allsalePcentFrom) {
        this.allsalePcentFrom = allsalePcentFrom;
    }

    public Float getOtherPcent() {
        return otherPcent;
    }

    public void setOtherPcent(Float otherPcent) {
        this.otherPcent = otherPcent;
    }

    public String getOtherPcentFrom() {
        return otherPcentFrom;
    }

    public void setOtherPcentFrom(String otherPcentFrom) {
        this.otherPcentFrom = otherPcentFrom;
    }

    public Float getRegisBaht() {
        return regisBaht;
    }

    public void setRegisBaht(Float regisBaht) {
        this.regisBaht = regisBaht;
    }

    public Float getPvPerBaht() {
        return pvPerBaht;
    }

    public void setPvPerBaht(Float pvPerBaht) {
        this.pvPerBaht = pvPerBaht;
    }

    public Float getCostBaht() {
        return costBaht;
    }

    public void setCostBaht(Float costBaht) {
        this.costBaht = costBaht;
    }

    public Float getOfficeRentBaht() {
        return officeRentBaht;
    }

    public void setOfficeRentBaht(Float officeRentBaht) {
        this.officeRentBaht = officeRentBaht;
    }

    public Float getEmployeeBaht() {
        return employeeBaht;
    }

    public void setEmployeeBaht(Float employeeBaht) {
        this.employeeBaht = employeeBaht;
    }

    public Float getMeetingBaht() {
        return meetingBaht;
    }

    public void setMeetingBaht(Float meetingBaht) {
        this.meetingBaht = meetingBaht;
    }

    public Float getOtherBaht() {
        return otherBaht;
    }

    public void setOtherBaht(Float otherBaht) {
        this.otherBaht = otherBaht;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.companyId != null ? this.companyId.hashCode() : 0);
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
        final TestPlanHeader other = (TestPlanHeader) obj;
        if (this.companyId != other.companyId && (this.companyId == null || !this.companyId.equals(other.companyId))) {
            return false;
        }
        return true;
    }

}
