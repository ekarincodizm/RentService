/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.systemTest.TestPlan;
import com.sabuymlm.model.systemTest.TestPlanHeader;
import com.sabuymlm.model.systemTest.TestPlanKey;
import com.sabuymlm.model.test.GenMember;
import com.sabuymlm.model.test.Ws;
import com.sabuymlm.service.SystemTestService;
import com.sabuymlm.service.TestService;
import com.sabuymlm.utils.Format;
import com.sabuymlm.utils.Pageable;
import com.sabuymlm.vm.CommonVM;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

/**
 *
 * @author MY-TENG
 */
@VariableResolver(DelegatingVariableResolver.class)
public class TestPlansVM extends AddCommonRefSponsorDefineVM<TestPlan, TestPlanHeader> implements Serializable {

    @WireVariable
    private SystemTestService systemTestService;
    @WireVariable
    private TestService testService;
    private final List<LabelValue> powerLevels = new ArrayList<LabelValue>();

    private final int pageMemberSize = 10;
    private int activeMemberPage;
    protected Page<GenMember> genMemberPage;

    private final int pageBonusMemberSize = 10;
    private int activeBonusMemberPage;
    protected Pageable<GenMember> genBonusMemberPage;

    private final int pageWsSize = 10;
    private int activeWsPage;
    protected Pageable<Ws> genWsPage = new Pageable<Ws>();

    private final int pageWsBlSize = 10;
    private int activeWsBlPage;
    protected Pageable<Ws> genWsBlPage = new Pageable<Ws>();

    private final int pageMatchingSize = 10;
    private int activeMatchingPage;
    protected Pageable<GenMember> genMatchingPage;

    private final int pageUniSize = 10;
    private int activeUniPage;
    protected Pageable<GenMember> genUniPage;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) TestPlanHeader item, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {
        pageHasDetail = false;
        positions.addAll(systemTestService.findAllPositions());
        super.initial(item, icon, headerLabel);
        setStatusEdit();
        genPowerLevels();
        loadTestData();
    }

    private void genPowerLevels() {
        powerLevels.clear();
        int max_count_member = 100000;
        if (isAdmin() && SecurityUtil.getUserDetails().getCompany().getMaxMlmMember() != null) {
            max_count_member = SecurityUtil.getUserDetails().getCompany().getMaxMlmMember();
        }
        int level = 0;
        double count_member;
        do {
            count_member = Math.pow(item.getChartPower(), level);
            LabelValue labelValue = new LabelValue(Format.formatNumber("#,##0 'รหัส'", count_member), level);
            powerLevels.add(labelValue);
            level++;
        } while (count_member < max_count_member);
        if (item.getChartLevel() != null && item.getChartLevel() > (level - 1)) {
            item.setChartLevel((level - 1));
        }

    }

    public List<LabelValue> getPowerLevels() {
        return powerLevels;
    }

    @Override
    protected void setEditItem() {
        item = systemTestService.findByTestPlanHeader(SecurityUtil.getUserDetails().getCompany().getId());
        if (item == null) {
            item = new TestPlanHeader();
        }
        item.getItems();
    }

    private void initItem() {
        item.setCompanyId(SecurityUtil.getUserDetails().getCompany().getId());
        if (!positions.isEmpty()) {
            item.setPosition(positions.get(0));
            item.setAdvancePosition(positions.get(0));
        }
    }

    @Override
    protected void setNewItem() {
        setEditItem();
        if (item.getItems().isEmpty()) {
            initItem();
            setStatusAdd();
            for (int i = 1; i <= 7; i++) {
                TestPlan itm = new TestPlan();
                TestPlanKey itemKey = new TestPlanKey();
                itemKey.setPlanHeader(item);
                itemKey.setNo(i);
                itm.setId(itemKey);
                itm.setChkPay("false");
                itm.setPassMatchingStrong("false");
                itm.setPassMatchingWeakOrBonus("false");
                switch (i) {
                    case 1:
                        itm.setPlanName("Sponsor (โบนัสแนะนำ)");
                        break;
                    case 2:
                        itm.setPlanName("X-Diff Sponsor (โบนัสแนะนำแบบส่วนต่างจ่ายหมด)");
                        break;
                    case 3:
                        itm.setPlanName("W/S(%) (โบนัสบริหารทีมแบบจ่าย อ่อน/แข็ง)");
                        break;
                    case 4:
                        itm.setPlanName("Multi W/S(%) (โบนัสบริหารทีมแบบจ่าย อ่อน/แข็ง) คำนวณมากกว่า 1 ครั้ง(ผัง 3 ขาขึ้นไป)");
                        break;
                    case 5:
                        itm.setPlanName("W/S(Balance) (โบนัสบริหารทีมแบบจ่าย จับคู่)");
                        break;
                    case 6:
                        itm.setPlanName("Matching (โบนัสแมทชิ่งตามสายแนะนำ)");
                        break;
                    case 7:
                        itm.setPlanName("Uni-level (โบนัส Uni-Level)");
                        break;
                    default:
                        break;
                }
                item.getItems().add(itm);
            }

        } else {
            setStatusEdit();
        }
    }

    @Transactional
    @Override 
    protected void saveItem() { 
        item = systemTestService.saveTestPlanHeader(item);
        systemTestService.procRunTest(); 
        loadTestData(); 
    }

    @Override
    protected void setItems() {
        if (item.getCreateUser() != null) {
            item.setUpdateDate(new Date());
            item.setUpdateUser(SecurityUtil.getUserDetails().getUser());
        } else {
            item.setCreateDate(new Date());
            item.setCreateUser(SecurityUtil.getUserDetails().getUser());
        }
    }

    @Command(value = {"onValidateTable"})
    @NotifyChange({"item", "items", "powerLevels"})
    public void validateTable(@BindingParam("item") TestPlan itm, @BindingParam("power") Integer power) {
        if (itm instanceof TestPlan) {
            TestPlan plan = item.getClassId(itm.getId().getNo()); 
            if (plan.getChkPay().equals("true")) {
                plan.setChkPay("false");
                plan.setChkAuto("false");
                plan.setPassMatchingStrong("false");
                plan.setPassMatchingWeakOrBonus("false");
            } else {
                plan.setChkPay("true");
            } 

        } else if (power != null) {
            genPowerLevels();
        }

    }

    @Override
    protected void privateValidate() {
        for (TestPlan itm : item.getItems()) {
            constraintViolations.addAll(validator.validate(itm.getId()));
            constraintViolations.addAll(validator.validate(itm));
        }
    }

    private void setSummaryPcent(Integer id, float comm, float commPro) {
        if (!genMemberPage.getContent().isEmpty() && (comm + commPro) > 0 ) {
            float totalComm = comm + commPro, pv = genMemberPage.getContent().get(0).getTopupPv(), price = 0f;
            long totalCount = genMemberPage.getTotalElements();
            price = (pv * totalCount) * item.getPvPerBaht();
            item.getClassId(id).setComm(comm);
            item.getClassId(id).setCommPro(commPro);
            item.getClassId(id).setTotalComm(totalComm);

            item.getClassId(id).setPcent(new Float(comm * 100.0 / price));
            item.getClassId(id).setPcentPro(new Float(commPro * 100.0 / price));
            item.getClassId(id).setTotalPcent(new Float(totalComm * 100.0 / price));
        }
    }

    /// ======================
    private void loadTestData() {
        setActiveMemberPage(0);
        setActiveBonusMemberPage(0);
        setActiveWsPage(0);
        setActiveWsBlPage(0);
        setActiveMatchingPage(0);
        item.clearSummaryPcent();
        searchGenMember();
        bonusGenMember(); 
        setSummaryPcent(1, genBonusMemberPage.getSum1().floatValue(), genBonusMemberPage.getSum2().floatValue());
        bonusWsGen();
        setSummaryPcent(3, genWsPage.getSum1().floatValue(), genWsPage.getSum2().floatValue());
        bonusWsBlGen();
        setSummaryPcent(5, genWsBlPage.getSum1().floatValue(), genWsBlPage.getSum2().floatValue());
        bonusMatchingGen();
        setSummaryPcent(6, genMatchingPage.getSum1().floatValue(), genMatchingPage.getSum2().floatValue());
        bonusUniLevelGen();
        setSummaryPcent(7, genUniPage.getSum1().floatValue(), genUniPage.getSum2().floatValue());
        item = systemTestService.saveTestPlanHeader(item);  
 
        BindUtils.postNotifyChange(null, null, this, "item");
        BindUtils.postNotifyChange(null, null, this, "genMemberPage");
        BindUtils.postNotifyChange(null, null, this, "genBonusMemberPage");
        BindUtils.postNotifyChange(null, null, this, "genWsPage");
        BindUtils.postNotifyChange(null, null, this, "genWsBlPage");
        BindUtils.postNotifyChange(null, null, this, "genMatchingPage");
        BindUtils.postNotifyChange(null, null, this, "genUniPage");
        BindUtils.postNotifyChange(null, null, this, "activeMemberPage");
        BindUtils.postNotifyChange(null, null, this, "activeBonusMemberPage");
        BindUtils.postNotifyChange(null, null, this, "activeWsPage");
        BindUtils.postNotifyChange(null, null, this, "activeWsBlPage");
        BindUtils.postNotifyChange(null, null, this, "activeMatchingPage");
        BindUtils.postNotifyChange(null, null, this, "activeUniPage");

    }

    @Command(value = {"searchGenMember"})
    public void searchGenMember() {
        genMemberPage = testService.findAllGenMembers(activeMemberPage, pageMemberSize);
        BindUtils.postNotifyChange(null, null, this, "genMemberPage");
    }

    public int getPageMemberSize() {
        return pageMemberSize;
    }

    public int getActiveMemberPage() {
        return activeMemberPage;
    }

    public void setActiveMemberPage(int activeMemberPage) {
        this.activeMemberPage = activeMemberPage;
    }

    public Page<GenMember> getGenMemberPage() {
        return genMemberPage;
    }

    @Command(value = {"bonusGenMember"})
    public void bonusGenMember() {
        genBonusMemberPage = testService.findAllBonusSponsorGen(activeBonusMemberPage, pageBonusMemberSize);
        BindUtils.postNotifyChange(null, null, this, "genBonusMemberPage");
    }

    public int getActiveBonusMemberPage() {
        return activeBonusMemberPage;
    }

    public void setActiveBonusMemberPage(int activeBonusMemberPage) {
        this.activeBonusMemberPage = activeBonusMemberPage;
    }

    public int getPageBonusMemberSize() {
        return pageBonusMemberSize;
    }

    public Pageable<GenMember> getGenBonusMemberPage() {
        return genBonusMemberPage;
    }

    @Command(value = {"bonusWsGen"})
    public void bonusWsGen() {
        genWsPage = testService.findAllBonusWsGen(activeWsPage, pageWsSize);
        BindUtils.postNotifyChange(null, null, this, "genWsPage");
    }

    public void setActiveWsPage(int activeWsPage) {
        this.activeWsPage = activeWsPage;
    }

    public int getActiveWsPage() {
        return activeWsPage;
    }

    public int getPageWsSize() {
        return pageWsSize;
    }

    public Pageable<Ws> getGenWsPage() {
        return genWsPage;
    }

    @Command(value = {"bonusWsBlGen"})
    public void bonusWsBlGen() {
        genWsBlPage = testService.findAllBonusWsBlGen(activeWsBlPage, pageWsBlSize);
        BindUtils.postNotifyChange(null, null, this, "genWsBlPage");
    }

    public void setActiveWsBlPage(int activeWsBlPage) {
        this.activeWsBlPage = activeWsBlPage;
    }

    public int getActiveWsBlPage() {
        return activeWsBlPage;
    }

    public int getPageWsBlSize() {
        return pageWsBlSize;
    }

    public Pageable<Ws> getGenWsBlPage() {
        return genWsBlPage;
    }

    @Command(value = {"bonusMatchingGen"})
    public void bonusMatchingGen() {
        genMatchingPage = testService.findAllBonusMatchungGen(activeMatchingPage, pageMatchingSize);
        BindUtils.postNotifyChange(null, null, this, "genMatchingPage");
    }

    public int getActiveMatchingPage() {
        return activeMatchingPage;
    }

    public void setActiveMatchingPage(int activeMatchingPage) {
        this.activeMatchingPage = activeMatchingPage;
    }

    public int getPageMatchingSize() {
        return pageMatchingSize;
    }

    public Pageable<GenMember> getGenMatchingPage() {
        return genMatchingPage;
    }

    @Command(value = {"bonusUniLevelGen"})
    public void bonusUniLevelGen() {
        genUniPage = testService.findAllBonusUniLevelGen(activeUniPage, pageUniSize);
        BindUtils.postNotifyChange(null, null, this, "genUniPage");
    }

    public int getActiveUniPage() {
        return activeUniPage;
    }

    public void setActiveUniPage(int activeUniPage) {
        this.activeUniPage = activeUniPage;
    }

    public int getPageUniSize() {
        return pageUniSize;
    }

    public Pageable<GenMember> getGenUniPage() {
        return genUniPage;
    }

}
