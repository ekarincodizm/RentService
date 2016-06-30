/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.event;
 
import com.sabuymlm.model.event.RentEvent; 
import com.sabuymlm.service.EventService;
import com.sabuymlm.utils.DateUtils;
import com.sabuymlm.vm.CommonVM; 
import java.io.Serializable; 
import java.util.Date;
import java.util.HashMap; 
import java.util.Map;
import org.zkoss.bind.BindUtils; 
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init; 
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
public class RentEventVM extends CommonVM<RentEvent> implements Serializable {

    @WireVariable
    private EventService eventService;
    private Date startdate ;
    private Date enddate ;
    private String status = "ALL_PAUSE" ;
    private String reStatus = "false" ; //ALL_STATUS ;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {  
        initialVM("/secured/event/addRentEvent.zul","rentStatus desc,expireDate",icon,headerLabel);
        startdate = DateUtils.getTruncateDate(DateUtils.getFirstDate( DateUtils.addYear(new Date(), -1)  )) ;
        enddate = DateUtils.getEndOfDate(DateUtils.getLastDate( DateUtils.addMonth(new Date(), 1)  )) ;
        search();
    } 
    
    @Override
    public void load() {
        items = eventService.findAllRentEvent(getActivePage(), getPageSize(),startdate,enddate,status,reStatus, order, getKeyword());
    }

    @Override
    public void deleteSelected() {
        eventService.deleteAllRentEvents(selectItems);
        search();
        BindUtils.postNotifyChange(null, null, RentEventVM.this, ".");
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

    public String getReStatus() {
        return reStatus;
    }

    public void setReStatus(String reStatus) {
        this.reStatus = reStatus;
    }
    
    @Command
    public void onClickPreview(@BindingParam(PARAM_NAME_OBJECT) Object o) {
        if(o instanceof RentEvent){
            RentEvent inv = (RentEvent)o;
            Map params = new HashMap(); 
            params.put("rentEventId",inv.getId()); 
            params.put("path", Executions.getCurrent().getDesktop().getWebApp().getRealPath("secured/event/jasper/invoice.jasper")); 
            params.put("reportName", inv.getCode()); 
            params.put("exportType", "pdf");   
            openReportWindowPopup(params);
        } 
    }
}
