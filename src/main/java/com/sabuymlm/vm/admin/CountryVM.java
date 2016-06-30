/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.admin;

import com.sabuymlm.model.admin.Country; 
import com.sabuymlm.service.CommonService;
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
public class CountryVM extends CommonVM<Country> implements Serializable {

    @WireVariable
    private CommonService commonService;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {  
        initialVM("/secured/admin/addCountry.zul","name",icon,headerLabel);
        search();
    }  
    
    @Override
    public void load() {
        items = commonService.findAllCountry(getActivePage(), getPageSize(), order, getKeyword());
    }

    @Override
    public void deleteSelected() {
        commonService.deleteAllCountry(selectItems);
        search();
        BindUtils.postNotifyChange(null, null, CountryVM.this, ".");
    }  
}
