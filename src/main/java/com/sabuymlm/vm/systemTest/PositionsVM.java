/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;
   
import com.sabuymlm.model.systemTest.Position;   
import com.sabuymlm.service.SystemTestService; 
import com.sabuymlm.vm.CommonVM; 
import java.io.Serializable;  
import org.zkoss.bind.BindUtils;   
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
public class PositionsVM extends CommonVM<Position> implements Serializable {

    @WireVariable
    private SystemTestService systemTestService; 

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {  
        initialVM("/secured/systemTest/addPosition.zul","id",icon,headerLabel);
        search();
    }  
    
    @Override
    public void load() {
        items = systemTestService.findAllPosition(getActivePage(), getPageSize() , order, getKeyword()); 
    }

    @Override
    public void deleteSelected() {
        systemTestService.deleteAllPositions(selectItems); 
        search();
        BindUtils.postNotifyChange(null, null, PositionsVM.this, ".");
    }    
    
}
