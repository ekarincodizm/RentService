/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.admin;
 
import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.ExpensesType;
import com.sabuymlm.service.CommonService;
import com.sabuymlm.utils.Validations;
import com.sabuymlm.vm.CommonAddVM; 
import com.sabuymlm.vm.CommonVM;
import java.io.Serializable;  
import java.util.Date; 
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
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
public class AddIncExpTypeVM extends CommonAddVM<ExpensesType> implements Serializable {

    @WireVariable
    private CommonService commonService; 
    @WireVariable
    private Validator validator;
    private final Set<ConstraintViolation> constraintViolations = new HashSet<ConstraintViolation>();
    
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) ExpensesType item
        , @ExecutionArgParam("icon") String icon
        , @ExecutionArgParam("headerLabel") String headerLabel ) { 
        initial(item,icon,headerLabel);     
    }

    @Override
    protected void setEditItem() {
        item = commonService.findByExpensesTypeId(item.getId()); 
    }

    @Override
    protected void setNewItem() {
        item = new ExpensesType();
    }

    @Override
    protected void saveItem() {
        if( item.getId() == null ){ 
            item.setCreateDate(new Date()); 
            item.setCreateUser(SecurityUtil.getUserDetails().getUser());
        }else { 
            item.setUpdateDate(new Date()); 
            item.setUpdateUser(SecurityUtil.getUserDetails().getUser());
        }
        item.setCompany(SecurityUtil.getUserDetails().getCompany()); 
        commonService.saveExpensesType(item); 
    }
    
    private Set<ConstraintViolation> validateAll() {  
        constraintViolations.clear(); 
        constraintViolations.addAll(validator.validate(item)); 
        return constraintViolations;
    } 

    @Override
    protected boolean validate() { 
        if (!validateAll().isEmpty()) {  
            Validations.showValidationError(constraintViolations); 
            return false;
        }else {
            return true;
        }
    }  
}
