/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;
   
import com.sabuymlm.authen.SecurityUtil;   
import com.sabuymlm.model.systemTest.Position;   
import com.sabuymlm.model.systemTest.UnilevelDefKey;
import com.sabuymlm.model.systemTest.UnilevelDefine;   
import com.sabuymlm.model.systemTest.UnilevelDefineHeader;
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
public class AddUnilevelDefineVM extends AddCommonRefSponsorDefineVM<UnilevelDefine,UnilevelDefineHeader> implements Serializable {
 
    @WireVariable
    private SystemTestService systemTestService;   
    
    @Init 
    public void init(@ContextParam(ContextType.VIEW) Component view
            , @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) UnilevelDefineHeader item
            , @ExecutionArgParam("icon") String icon
            , @ExecutionArgParam("headerLabel") String headerLabel) {
        super.initial(item, icon, headerLabel);    
    }

    @Override
    protected void setEditItem() { 
        selectItems.addAll(systemTestService.findAllUnilevelDefineByLevel(item.getLevelIndex())) ;
    }

    @Override
    protected void setNewItem() {
        positions.addAll(systemTestService.findAllPositions());
        Integer level = systemTestService.findByUnilevelDefineMaxId();
        if( level == null){
            level = 1;
        }else {
            level++;
        }
        item = new UnilevelDefineHeader(); 
        item.setLevelIndex(level); 
        
        selectItems.clear();
        for(Position pos : positions){
            UnilevelDefine itm = new UnilevelDefine(); 
            UnilevelDefKey itemKey = new UnilevelDefKey(); 
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
        systemTestService.saveUnilevelDefine(selectItems);
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
        for(UnilevelDefine def: selectItems){ 
            def.getId().setLevelIndex(level);  
            if(positions.isEmpty()){
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
