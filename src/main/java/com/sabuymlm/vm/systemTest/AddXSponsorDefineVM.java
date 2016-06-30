/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;
   
import com.sabuymlm.authen.SecurityUtil;   
import com.sabuymlm.model.systemTest.Position;    
import com.sabuymlm.model.systemTest.XSponsorDefineHeader;
import com.sabuymlm.model.systemTest.XSponsorDefKey;
import com.sabuymlm.model.systemTest.XSponsorDefine;    
import com.sabuymlm.model.systemTest.XSponsorHeaderKey;
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
public class AddXSponsorDefineVM extends AddCommonRefSponsorDefineVM<XSponsorDefine,XSponsorDefineHeader> implements Serializable {
 
    @WireVariable
    private SystemTestService systemTestService; 
    
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) XSponsorDefineHeader item, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {
        positions.addAll(systemTestService.findAllPositions());
        super.initial(item, icon, headerLabel);   
    }

    @Override
    protected void setEditItem() { 
        item = systemTestService.findXSponsorDefineById(item.getId()) ;
    }

    @Override
    protected void setNewItem() { 
        item = new XSponsorDefineHeader(); 
        item.setBonus(0f);  
        item.getItems().clear(); 
        item.setId(new XSponsorHeaderKey());
        item.getId().setCompany( SecurityUtil.getUserDetails().getCompany() ); 
        for(Position pos : positions){ 
            XSponsorDefine itm = new XSponsorDefine(); 
            XSponsorDefKey itemKey = new XSponsorDefKey();  
            itemKey.setXposition(pos); 
            itemKey.setHeaderId(item.getId()); 
            itm.setId(itemKey);
            itm.setPcent(0.0f);
            itm.setPcentDiff(0.0f); 
            item.getItems().add(itm); 
        }   
    }   

    @Transactional
    @Override
    protected void saveItem() {   
        systemTestService.saveXSponsorHeaderDefine(item); 
    }    

    @Override
    protected void setItems() { 
        if (item.getCreateDate()== null) {   
            item.setCreateDate(new Date());
            item.setCreateUser(SecurityUtil.getUserDetails().getUser());
        } else { 
            item.setUpdateDate(new Date());
            item.setUpdateUser(SecurityUtil.getUserDetails().getUser());
        }  
    }
    
    @Override
    protected void privateValidate() { 
        constraintViolations.addAll(validator.validate(item.getId())); 
    }
     
    
}
