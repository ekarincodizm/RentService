/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.event;
  
import com.sabuymlm.utils.DateUtils;
import com.sabuymlm.utils.Format;
import com.sabuymlm.vm.CommonReportVM;  
import java.io.Serializable; 
import java.util.Date; 
import java.util.HashMap; 
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init; 
import org.zkoss.zk.ui.Component;  
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver; 
import org.zkoss.zkplus.spring.DelegatingVariableResolver;  

/**
 *
 * @author MY-TENG
 */
@VariableResolver(DelegatingVariableResolver.class)
public class ReportPerformanceVM extends CommonReportVM implements Serializable {
     
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {  
        initialVM( icon,headerLabel);
        startdate = DateUtils.addMonth( DateUtils.getTruncateDate(DateUtils.getFirstDate( DateUtils.addYear( new Date() , -1 ) )) , 1 ) ;
        enddate = DateUtils.getEndOfDate(DateUtils.getLastDate( new Date() )) ; 
    }    

    @Override
    public String condition() {
        StringBuilder sb = new StringBuilder();
        sb.append("Statement Date ").append(Format.formatDateEn("dd/MM/yyyy", startdate));
        sb.append(" To ").append(Format.formatDateEn("dd/MM/yyyy", enddate)); 
        return sb.toString();
    }
    
    @Override
    @Command
    public void search() {
        param = new HashMap(); 
        param.put("condition",condition()); 
        param.put("startdate",startdate); 
        param.put("enddate",enddate); 
        param.put("path", Executions.getCurrent().getDesktop().getWebApp().getRealPath("secured/event/jasper/reportAnalysis.jasper")); 
        param.put("exportType", "pdf");  
        openReportWindowPopup(param);
    }   
}
