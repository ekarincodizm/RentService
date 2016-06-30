/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.event;
  
import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.ExpensesType;
import com.sabuymlm.service.CommonService;
import com.sabuymlm.utils.DateUtils;
import com.sabuymlm.utils.Format;
import com.sabuymlm.vm.CommonReportVM; 
import com.sabuymlm.vm.CommonVM;
import java.io.Serializable; 
import java.util.ArrayList;
import java.util.Date; 
import java.util.HashMap;  
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init; 
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;  
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver; 
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;  

/**
 *
 * @author MY-TENG
 */
@VariableResolver(DelegatingVariableResolver.class)
public class ReportStatementVM extends CommonReportVM implements Serializable {
   
    @WireVariable
    private CommonService commonService;
    private String type = CommonVM.ALL_STATUS ; 
    private ExpensesType expensesType  ; 
    private boolean openStatus = true ;
    private boolean closeStatus = true ;
    private boolean pauseStatus = true ;
    private final List<ExpensesType> types = new ArrayList<ExpensesType>();
    
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {  
        initialVM( icon,headerLabel);
        startdate = DateUtils.getTruncateDate(DateUtils.getFirstDate( new Date() )) ;
        enddate = DateUtils.getEndOfDate(DateUtils.getLastDate( new Date() )) ; 
        types.add(new ExpensesType(-1,"<-- ทุกประเภท -->")); 
        expensesType = types.get(0); 
        types.addAll(commonService.findAllExpensesType(SecurityUtil.getUserDetails().getCompany()));
    }    
    
    @Override
    public String condition() {
        StringBuilder sb = new StringBuilder();
        sb.append("Statement Date ").append(Format.formatDateEn("dd/MM/yyyy", startdate));
        sb.append(" To ").append(Format.formatDateEn("dd/MM/yyyy", enddate));
        sb.append(", ประเภท : ").append( type.equals("N")?"Expenses":(type.equals("Y")?"Income":type) );
        sb.append(", สถานะการเช่า : { ")
                .append( (openStatus?"OPEN":"") ) 
                .append( (closeStatus?",CLOSE":"") ) 
                .append( (pauseStatus?",PAUSE":"") ) 
                .append(" }");
        return sb.toString();
    }

    @Override
    @Command
    public void search() {
        param = new HashMap();
        param.put("condition",condition()); 
        param.put("openStatus",(openStatus?"OPEN":"")); 
        param.put("closeStatus",(closeStatus?"CLOSE":"")); 
        param.put("pauseStatus",(pauseStatus?"PAUSE":"")); 
        param.put("type",type); 
        param.put("expensesTypeId",expensesType.getId()); 
        param.put("startdate",startdate); 
        param.put("enddate",enddate); 
        param.put("path", Executions.getCurrent().getDesktop().getWebApp().getRealPath("secured/event/jasper/reportStatement.jasper")); 
        param.put("exportType", "pdf"); 
        openReportWindowPopup(param);
    } 
    
    @Command("onSelectedType")
    @NotifyChange({"types"})
    public void onSelectedType() {
        types.clear();
        types.add(new ExpensesType(-1,"<-- ทุกประเภท -->")); 
        expensesType = types.get(0);
        if(type.equals(CommonVM.ALL_STATUS)){
            types.addAll(commonService.findAllExpensesType(SecurityUtil.getUserDetails().getCompany()));
        }else {
            types.addAll(commonService.findAllExpensesType(SecurityUtil.getUserDetails().getCompany(),type));
        }
    }  

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(boolean openStatus) {
        this.openStatus = openStatus;
    }

    public boolean isCloseStatus() {
        return closeStatus;
    }

    public void setCloseStatus(boolean closeStatus) {
        this.closeStatus = closeStatus;
    }

    public boolean isPauseStatus() {
        return pauseStatus;
    }

    public void setPauseStatus(boolean pauseStatus) {
        this.pauseStatus = pauseStatus;
    } 

    public List<ExpensesType> getTypes() {
        return types;
    } 

    public ExpensesType getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(ExpensesType expensesType) {
        this.expensesType = expensesType;
    }
    
}
