/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;
   
import com.sabuymlm.authen.SecurityUtil;  
import com.sabuymlm.model.systemTest.Position;   
import com.sabuymlm.service.SystemTestService;  
import com.sabuymlm.vm.CommonVM;    
import java.io.Serializable;    
import java.util.Date;    
import org.springframework.transaction.annotation.Transactional; 
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
public class AddPositionVM extends AddCommonRefSponsorDefineVM<Position,Position> implements Serializable {
 
    @WireVariable
    private SystemTestService systemTestService; 
    
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) Position item, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {
        positions.addAll(systemTestService.findAllPositions());
        initial(item, icon, headerLabel);    
    }

    @Override
    protected void setEditItem() {
        item = systemTestService.findByPositionId(item.getId());  
    }

    @Override
    protected void setNewItem() {
        item = new Position(); 
        item.setTopupPv(0.0f);
        item.setMtaBaht(0.0f);
        item.setAutoMax(0.0f);
        item.setAutoPcent(0.0f);
        clearNotSponsorRaw();
    }  
    
    private void clearNotSponsorRaw(){
        item.setSponsorCount(null); 
        item.setSponsorLevel(null); 
        item.setSponsorOrAndState("NO"); 
        item.setIsFixSponsorLevel("N"); 
        item.setMemberPosition(null); 
    }
    
    @Command("onSelectedState")
    @NotifyChange({"item","visibleState"})
    public void selectedState() {
         if(!isVisibleState()){ 
             clearNotSponsorRaw();
         }
    } 
    
    @Command("onSelectedFix")
    @NotifyChange({"item.sponsorLevel","fixLevel"})
    public void selectedFix() {
         if(!isFixLevel()){
             item.setSponsorLevel(null); 
         }
    } 
    
    public boolean isVisibleState(){
        return !(item.getSponsorOrAndState() != null && item.getSponsorOrAndState().equals("NO")); 
    }
    
    public boolean isFixLevel(){
        return (item.getIsFixSponsorLevel()!= null && item.getIsFixSponsorLevel().equals("Y"));
    } 

    @Transactional
    @Override
    protected void saveItem() {
          
        if (item.getId() == null) { 
            item.setCreateDate(new Date());
            item.setCreateUser(SecurityUtil.getUserDetails().getUser());
        } else {
            item.setUpdateDate(new Date());
            item.setUpdateUser(SecurityUtil.getUserDetails().getUser());
        }  
        item.setCompany(SecurityUtil.getUserDetails().getCompany());
        systemTestService.savePosition(item);
    }   

    @Override
    protected void setItems() { 
    }

    @Override
    protected void privateValidate() {
         
    }
    
}
