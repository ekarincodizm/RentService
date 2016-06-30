/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.search;
 
import com.sabuymlm.model.customer.Customer;
import com.sabuymlm.service.CustomerService; 
import com.sabuymlm.vm.CommonVM;
import java.io.Serializable;   
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
public class CustomerVM extends CommonVM<Customer> implements Serializable {

    @WireVariable
    private CustomerService customerService; 

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {  
        initialVM("","name",icon,headerLabel); 
        search();
    }  
    
    @Override
    public int getPageSize(){
        return 5 ;
    } 

    @Override
    public void load() { 
        items = customerService.findAllCustomer(getActivePage(), getPageSize(), order, getKeyword());
    }

    @Override
    public void deleteSelected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     
}
