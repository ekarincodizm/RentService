/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;
 
import com.sabuymlm.authen.SecurityUtil; 
import com.sabuymlm.model.systemTest.BinaryBalanceDefKey;
import com.sabuymlm.model.systemTest.BinaryBalanceDefine; 
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
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver; 
/**
 *
 * @author MY-TENG
 */
@VariableResolver(DelegatingVariableResolver.class)
public class BinaryBalanceVM extends AddCommonRefSponsorDefineVM<BinaryBalanceDefine,BinaryBalanceDefine> implements Serializable {

    @WireVariable
    private SystemTestService systemTestService;   
    
    @Init 
    public void init(@ContextParam(ContextType.VIEW) Component view
            , @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) BinaryBalanceDefine item
            , @ExecutionArgParam("icon") String icon
            , @ExecutionArgParam("headerLabel") String headerLabel) {
        selectItems.addAll(systemTestService.findAllBinaryBalanceByCompany()) ;
        pageHasDetail = false ;
        super.initial(item, icon, headerLabel);      
        setStatusEdit();
    }

    @Override
    protected void setEditItem() { 
        selectItems.addAll(systemTestService.findAllBinaryBalanceByCompany()) ;
    }

    @Override
    protected void setNewItem() { 
        if(selectItems.isEmpty()) {
            setStatusAdd();
            positions.addAll(systemTestService.findAllPositions()); 
            selectItems.clear();
            for(Position pos : positions){
                BinaryBalanceDefine itm = new BinaryBalanceDefine(); 
                BinaryBalanceDefKey itemKey = new BinaryBalanceDefKey(); 
                itemKey.setCompany(SecurityUtil.getUserDetails().getCompany()); 
                itemKey.setPosition(pos); 
                itm.setId(itemKey);
                itm.setWkComm(0.0f);
                itm.setWsBalance(0.0f);
                itm.setMaxUnit(0);
                itm.setMaxPv(0.0f);
                itm.setMaxComm(0.0f); 
                itm.setCircleUnit(1); 
                selectItems.add(itm);
            } 
        }else {
            setStatusEdit();
        }
    }   

    @Transactional
    @Override
    protected void saveItem() { 
        selectItems = systemTestService.saveBinaryBalanceDefine(selectItems);
    }  
    
    @Override
    protected void setItems(){ 
        for(BinaryBalanceDefine def: selectItems){
            if(positions.isEmpty()){
                def.setUpdateDate(new Date());
                def.setUpdateUser(SecurityUtil.getUserDetails().getUser());
            }else {
                def.setCreateDate(new Date());
                def.setCreateUser(SecurityUtil.getUserDetails().getUser());
            } 
        } 
    }   
    
    @Override
    protected void privateValidate() {  
    } 
     
    @Command(value = {"onCalculateMaxComm"}) 
    public void calculateMaxCom(@BindingParam("item") BinaryBalanceDefine item ) { 
       float wkBalace = 0f, wkComm = 0f ;
       int maxUnit =0  ;
        if(item.getWsBalance()!= null ){
            wkBalace = item.getWsBalance() ;
        }
        if(item.getWkComm()!= null ){
            wkComm = item.getWkComm() ;
        }
        if(item.getMaxUnit()!= null ){
            maxUnit = item.getMaxUnit();
        } 
        item.setMaxPv(maxUnit*wkBalace ); 
        item.setMaxComm( maxUnit*wkComm );  
    }  
    
}
