/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.admin;

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.ExpensesType; 
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
public class IncExpTypesVM extends CommonVM<ExpensesType> implements Serializable {

    @WireVariable
    private CommonService commonService;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {  
        initialVM("/secured/admin/addExpensesType.zul","id",icon,headerLabel);
        search();
    }  
    
    @Override
    public void load() {
        items = commonService.findAllExpensesType(getActivePage(), getPageSize(), order, getKeyword(),SecurityUtil.getUserDetails().getCompany());
    }

    @Override
    public void deleteSelected() {
        commonService.deleteAllExpensesType(selectItems);
        search();
        BindUtils.postNotifyChange(null, null, IncExpTypesVM.this, ".");
    }  
}
