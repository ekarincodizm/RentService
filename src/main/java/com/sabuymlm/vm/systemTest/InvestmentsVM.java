/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.systemTest.InvestmentDefKey;
import com.sabuymlm.model.systemTest.InvestmentDefine;
import com.sabuymlm.model.systemTest.Position;
import com.sabuymlm.service.SystemTestService;
import com.sabuymlm.vm.CommonVM;
import java.io.Serializable;
import java.util.Date;
import org.springframework.transaction.annotation.Transactional;
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
public class InvestmentsVM extends AddCommonRefSponsorDefineVM<InvestmentDefine, InvestmentDefine> implements Serializable {

    @WireVariable
    private SystemTestService systemTestService;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) InvestmentDefine item, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {
        selectItems.addAll(systemTestService.findAllInvestmentByCompany());
        pageHasDetail = false;
        super.initial(item, icon, headerLabel);
        setStatusEdit();
    }

    @Override
    protected void setEditItem() {
        selectItems.addAll(systemTestService.findAllInvestmentByCompany());
    }

    @Override
    protected void setNewItem() {
        if (selectItems.isEmpty()) {
            setStatusAdd();
            positions.addAll(systemTestService.findAllPositions());
            selectItems.clear();
            for (Position pos : positions) {
                InvestmentDefine itm = new InvestmentDefine();
                InvestmentDefKey itemKey = new InvestmentDefKey();
                itemKey.setCompany(SecurityUtil.getUserDetails().getCompany());
                itemKey.setPosition(pos);
                itm.setId(itemKey);
                itm.setRatePcent(0.0f);
                itm.setComm(0.0f);
                itm.setMaxDay(0);
                itm.setMaxComm(0.0f);
                itm.setCircleUnit(1);
                selectItems.add(itm);
            }
        } else {
            setStatusEdit();
        }
    }

    @Transactional
    @Override
    protected void saveItem() {
        selectItems = systemTestService.saveInvestmentDefine(selectItems);
    }

    @Override
    protected void setItems() {
        for (InvestmentDefine def : selectItems) {
            if (positions.isEmpty()) {
                def.setUpdateDate(new Date());
                def.setUpdateUser(SecurityUtil.getUserDetails().getUser());
            } else {
                def.setCreateDate(new Date());
                def.setCreateUser(SecurityUtil.getUserDetails().getUser());
            }
        }
    }

    @Override
    protected void privateValidate() {
    }

    private void calculateItem(InvestmentDefine item) {
        float rate = 0f,  topupPv = 0f;
        int maxDay = 0 ;
        topupPv = item.getId().getPosition().getTopupPv(); 
        if (item.getRatePcent() != null) {
            rate = item.getRatePcent();
            item.setComm(topupPv *rate / 100.0f); 
        }else {
            item.setComm(0f); 
        } 
        if (item.getMaxDay()!= null) {
            maxDay = item.getMaxDay();
        }  
        if (maxDay > 0 && rate > 0) { 
            item.setMaxComm(item.getComm()*maxDay); 
        } else {
            item.setMaxComm(0f); 
        }
    }

    @Command(value = {"onCalculateMaxComm"})
    @NotifyChange({"item.maxComm, item.comm"})
    public void calculateMaxCom(@BindingParam("item") InvestmentDefine item) {
        calculateItem(item); 
    }

}
