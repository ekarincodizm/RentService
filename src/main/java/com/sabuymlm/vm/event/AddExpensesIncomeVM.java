/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.event;
  
import com.sabuymlm.authen.SecurityUtil; 
import com.sabuymlm.model.admin.ExpensesType;
import com.sabuymlm.model.event.ExpensesIncome;  
import com.sabuymlm.service.CommonService;
import com.sabuymlm.service.EventService;
import com.sabuymlm.service.RunFormatService; 
import com.sabuymlm.utils.FileEntry; 
import com.sabuymlm.vm.CommonAddVM;
import com.sabuymlm.vm.CommonVM;   
import java.io.IOException;
import java.io.InputStream; 
import java.io.Serializable;   
import java.util.ArrayList;
import java.util.Date;   
import java.util.List; 
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.bind.BindContext;   
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange; 
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable; 
import org.zkoss.zkplus.spring.DelegatingVariableResolver; 

/**
 *
 * @author MY-TENG
 */
@VariableResolver(DelegatingVariableResolver.class)
public class AddExpensesIncomeVM extends CommonAddVM<ExpensesIncome> implements Serializable {

    @WireVariable
    private CommonService commonService;
    @WireVariable
    private EventService eventService;
    @WireVariable
    private RunFormatService runService;
    private InputStream is = null;
    private String fileName; 
    private final List<ExpensesType> types = new ArrayList<ExpensesType>();
    
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) ExpensesIncome item, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {
        initial(item, icon, headerLabel);   
    }

    @Override
    protected void setEditItem() {
        item = eventService.findByExpensesIncomeId(item.getId());
        fileName = item.getAttachFile();
        types.addAll(commonService.findAllExpensesType(SecurityUtil.getUserDetails().getCompany(), item.getExpensesIncomeType() ));
    }

    @Override
    protected void setNewItem() {
        item = new ExpensesIncome();
        item.setCode(runService.expensesIncomeCode()); 
        item.setTotalAmount(0.0f); 
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isVisibleFileUpload() {
        return (fileName != null);
    }

    @Command("onUpload")
    @NotifyChange({"item", "fileName", "visibleFileUpload"})
    public void upload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) throws IOException {
        UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
        is = event.getMedia().getStreamData();
        fileName = event.getMedia().getName();
    }

    @Command("onClear")
    @NotifyChange({"fileName", "visibleFileUpload"})
    public void onClear() {
        is = null;
        fileName = null;
        item.setAttachFile(null);
    } 
    
    @Command("onSelectedType")
    @NotifyChange({"types","item"})
    public void onSelectedType() {
        types.clear();
        types.addAll(commonService.findAllExpensesType(SecurityUtil.getUserDetails().getCompany(), item.getExpensesIncomeType() ));   
        item.setExpensesType(null); 
    } 

    @Transactional
    @Override
    protected void saveItem() {
         
        if (is instanceof InputStream) {
            String newFileName = FileEntry.getNewName(fileName + SecurityUtil.getUserDetails().getUser().getCompanyId(), fileName);
            FileEntry.saveUploadFile(is, item.getAttachPath(), newFileName);
            item.setAttachFile(newFileName);
        }
        if (item.getId() == null) {
            item.setCode(runService.expensesIncomeCode());
            item.setCreateDate(new Date());
            item.setCreateUser(SecurityUtil.getUserDetails().getUser());
        } else {
            item.setUpdateDate(new Date());
            item.setUpdateUser(SecurityUtil.getUserDetails().getUser());
        } 
        item.setCompany(SecurityUtil.getUserDetails().getCompany());
        eventService.saveExpensesIncome(item);
    }  

    @Override
    protected boolean validate() { 
            return true; 
    }
    
    public List<ExpensesType> getTypes() {
        return types;
    }
    
}
