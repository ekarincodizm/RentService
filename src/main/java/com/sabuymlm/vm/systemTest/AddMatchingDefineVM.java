/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;
   
import com.sabuymlm.authen.SecurityUtil;   
import com.sabuymlm.model.systemTest.Position;   
import com.sabuymlm.model.systemTest.MatchingDefKey;
import com.sabuymlm.model.systemTest.MatchingDefine;   
import com.sabuymlm.model.systemTest.MatchingDefineHeader;
import com.sabuymlm.service.SystemTestService;   
import com.sabuymlm.vm.CommonVM;    
import java.io.Serializable;    
import java.util.Date;    
import org.springframework.transaction.annotation.Transactional;  
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
public class AddMatchingDefineVM extends AddCommonRefSponsorDefineVM<MatchingDefine,MatchingDefineHeader> implements Serializable {
 
    @WireVariable
    private SystemTestService systemTestService;   
    
    @Init 
    public void init(@ContextParam(ContextType.VIEW) Component view
            , @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) MatchingDefineHeader item
            , @ExecutionArgParam("icon") String icon
            , @ExecutionArgParam("headerLabel") String headerLabel) {
        super.initial(item, icon, headerLabel);    
    }

    @Override
    protected void setEditItem() { 
        selectItems.addAll(systemTestService.findAllMatchingDefineByLevel(item.getLevelIndex())) ;
    }

    @Override
    protected void setNewItem() {
        positions.addAll(systemTestService.findAllPositions());
        Integer level = systemTestService.findByMatchingDefineMaxId();
        if( level == null){
            level = 1;
        }else {
            level++;
        }
        item = new MatchingDefineHeader(); 
        item.setLevelIndex(level); 
        
        selectItems.clear();
        for(Position pos : positions){
            MatchingDefine itm = new MatchingDefine(); 
            MatchingDefKey itemKey = new MatchingDefKey(); 
            itemKey.setLevelIndex(level); 
            itemKey.setPosition(pos); 
            itm.setId(itemKey);
            itm.setPcent(0.0f);
            itm.setPcentPro(0.0f); 
            selectItems.add(itm);
        } 
    }   

    @Transactional
    @Override
    protected void saveItem() { 
        systemTestService.saveMatchingDefine(selectItems);
    }  
    
    @Override
    protected void setItems(){
        Integer level = 0 ;
        if(positions.isEmpty()){ 
            level = item.getLevelIndex();
        }else { 
            level = systemTestService.findByMatchingDefineMaxId();
            if( level == null){
                level = 1;
            }else {
                level++;
            }
        }
        for(MatchingDefine def: selectItems){
            def.setName(item.getName()); 
            def.getId().setLevelIndex(level);  
            if(def.getCreateUser() != null){
                def.setUpdateDate(new Date());
                def.setUpdateUser(SecurityUtil.getUserDetails().getUser());
            }else {
                def.setCreateDate(new Date());
                def.setCreateUser(SecurityUtil.getUserDetails().getUser());
            }
            def.setCompany(SecurityUtil.getUserDetails().getCompany());
        } 
    }   
    
    @Override
    protected void privateValidate() {  
    }
    
}
