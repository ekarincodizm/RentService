/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.customer;
 
import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.customer.TreatyRule;
import com.sabuymlm.model.event.RentEventDetail;
import com.sabuymlm.service.CustomerService;
import com.sabuymlm.utils.FileEntry;
import com.sabuymlm.utils.Validations;
import com.sabuymlm.vm.CommonAddVM;
import com.sabuymlm.vm.CommonVM; 
import java.io.FileNotFoundException; 
import java.io.IOException;
import java.io.InputStream; 
import java.io.Serializable;  
import java.util.Date; 
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
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
public class AddTreatyRuleVM extends CommonAddVM<TreatyRule> implements Serializable {

    @WireVariable
    private CustomerService customerService;
    private InputStream is = null;
    private String fileName;
    @WireVariable
    private Validator validator;
    private final Set<ConstraintViolation> constraintViolations = new HashSet<ConstraintViolation>();
    
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) TreatyRule item, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {
        initial(item, icon, headerLabel);
    }

    @Override
    protected void setEditItem() {
        item = customerService.findByTreatyRuleId(item.getTreatyRuleId());
        fileName = item.getAttachFile();
    }

    @Override
    protected void setNewItem() {
        item = new TreatyRule();
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

    @Command("saveTemplate")
    public void saveTemplate(@BindingParam("document") String document) throws FileNotFoundException {
         /// TODO
    }

    @Command("onChangeTemplate")
    public void onChangeTemplate(@BindingParam("document") String document) {
        item.setDocument(document);
    }

    @Override
    protected void saveItem() {
        if (is instanceof InputStream) {
            String newFileName = FileEntry.getNewName(fileName + SecurityUtil.getUserDetails().getUser().getCompanyId(), fileName);
            FileEntry.saveUploadFile(is, item.getAttachPath(), newFileName);
            item.setAttachFile(newFileName);
        }
        if (item.getTreatyRuleId() == null) {
            item.setCreateDate(new Date());
            item.setCreateUser(SecurityUtil.getUserDetails().getUser());
        } else {
            item.setUpdateDate(new Date());
            item.setUpdateUser(SecurityUtil.getUserDetails().getUser());
        }
        if (item.getTreatyCloseDate() instanceof Date) {
            item.setTreatyCloseUser(SecurityUtil.getUserDetails().getUser());
        } else {
            item.setTreatyCloseUser(null);
        }
        item.setCompany(SecurityUtil.getUserDetails().getCompany());
        customerService.saveTreatyRule(item);
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
