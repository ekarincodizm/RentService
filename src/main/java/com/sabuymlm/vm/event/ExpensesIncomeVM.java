/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.event;
 
import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.ExpensesType;
import com.sabuymlm.model.event.ExpensesIncome; 
import com.sabuymlm.model.transformers.SumBean;
import com.sabuymlm.service.CommonService;
import com.sabuymlm.service.EventService;
import com.sabuymlm.utils.DateUtils;
import com.sabuymlm.vm.CommonVM; 
import java.io.Serializable; 
import java.util.ArrayList;
import java.util.Date; 
import java.util.List; 
import org.zkoss.bind.BindUtils;  
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
public class ExpensesIncomeVM extends CommonVM<ExpensesIncome> implements Serializable {

    @WireVariable
    private EventService eventService;
    @WireVariable
    private CommonService commonService;
    private Date startdate ;
    private Date enddate ;
    private String status = ALL_STATUS ; 
    private ExpensesType expensesType  ; 
    private final List<ExpensesType> types = new ArrayList<ExpensesType>();
    
    private SumBean sumBean = new SumBean();

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {  
        initialVM("/secured/event/addExpensesIncome.zul","expensesDate desc, expensesIncomeType",icon,headerLabel);
        startdate = DateUtils.getTruncateDate(DateUtils.getFirstDate(new Date() )) ;
        enddate = DateUtils.getEndOfDate(DateUtils.getLastDate( new Date())) ;
        types.add(new ExpensesType(-1,"<-- ทุกประเภท -->")); 
        expensesType = types.get(0);
        types.addAll(commonService.findAllExpensesType(SecurityUtil.getUserDetails().getCompany()));
        search();
    } 
    
    @Command("onSelectedType")
    @NotifyChange({"types","items","sumBean"})
    public void onSelectedType() {
        types.clear();
        types.add(new ExpensesType(-1,"<-- ทุกประเภท -->")); 
        expensesType = types.get(0);
        if(status.equals(CommonVM.ALL_STATUS)){
            types.addAll(commonService.findAllExpensesType(SecurityUtil.getUserDetails().getCompany()));
        }else {
            types.addAll(commonService.findAllExpensesType(SecurityUtil.getUserDetails().getCompany(),status));
        }
        search();
    }
    
    @Override
    public void load() {
        items = eventService.findAllExpensesIncome(getActivePage(), getPageSize(),startdate,enddate,status, expensesType , order, getKeyword());
        sumBean = eventService.findSumExpensesIncome(startdate, enddate, status , expensesType, getKeyword()); 
    }

    @Override
    public void deleteSelected() {
        eventService.deleteAllExpensesIncomes(selectItems);
        search();
        BindUtils.postNotifyChange(null, null, ExpensesIncomeVM.this, ".");
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

    public SumBean getSumBean() {
        return sumBean;
    } 

    public ExpensesType getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(ExpensesType expensesType) {
        this.expensesType = expensesType;
    }

    public List<ExpensesType> getTypes() {
        return types;
    } 
    
}
