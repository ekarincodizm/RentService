/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.customer;

import com.sabuymlm.model.customer.Customer; 
import com.sabuymlm.service.CustomerService;
import com.sabuymlm.utils.DateUtils;
import com.sabuymlm.vm.CommonVM;  
import java.io.Serializable; 
import java.util.Date; 
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
public class CustomerVM extends CommonVM<Customer> implements Serializable {

    @WireVariable
    private CustomerService customerService;
    private Date startdate ;
    private Date enddate ;
    private String status = ALL_STATUS ;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {  
        initialVM("/secured/customer/addCustomer.zul","name",icon,headerLabel);
        startdate = DateUtils.getTruncateDate(DateUtils.getFirstDate( DateUtils.addYear(new Date(), -10)  )) ;
        enddate = DateUtils.getEndOfDate(DateUtils.getLastDate(new Date())) ;
        search();
    }  
    
    @Override
    public void load() {  
        items = customerService.findAllCustomer(getActivePage(), getPageSize(),startdate,enddate,status, order, getKeyword()); 
    }

    @Override
    public void deleteSelected() {
        customerService.deleteAllCustomers(selectItems);
        search();
        BindUtils.postNotifyChange(null, null, CustomerVM.this, ".");
    }   

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }  

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    } 

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
