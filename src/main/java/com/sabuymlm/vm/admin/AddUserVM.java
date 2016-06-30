/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.admin;

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.User;
import com.sabuymlm.service.CommonService;
import com.sabuymlm.utils.Format;
import com.sabuymlm.utils.Validations;
import com.sabuymlm.vm.CommonAddVM;
import com.sabuymlm.vm.CommonVM;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.apache.commons.codec.digest.DigestUtils;
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
public class AddUserVM extends CommonAddVM<User> implements Serializable {

    @WireVariable
    private CommonService commonService;
    @WireVariable
    private Validator validator;
    private final Set<ConstraintViolation> constraintViolations = new HashSet<ConstraintViolation>();

    private String oldEmail = "";

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) User item, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {
        initial(item, icon, headerLabel);
    }

    @Override
    protected void setEditItem() {
        item = commonService.findByUserName(item.getUsername());
        oldEmail = item.getEmail();
    }

    @Override
    protected void setNewItem() {
        item = new User();
        item.setCompanyId(SecurityUtil.getUserDetails().getUser().getCompanyId());
    }

    @Override
    protected void saveItem() {
        if (!Format.isValidMD5(item.getPassword().toString())) {
            item.setPasswordShow(item.getPassword());
            item.setPassword(DigestUtils.md5Hex(item.getPassword()));
        }
        commonService.saveUser(item);
        oldEmail = item.getEmail();
    }

    private Set<ConstraintViolation> validateAll() {
        constraintViolations.clear();
        constraintViolations.addAll(validator.validate(item));
        return constraintViolations;
    }

    @Override
    protected boolean validate() {
        if( !isUserAdmin() && item.getId() == null ) {
            throw new RuntimeException(" ระดับผู้ใช้งานของคุณไม่สามารถสร้าง ผู้ใช้งานใหม่ได้เพิ่มได้");
        }else {
            if (!validateAll().isEmpty()) {
                Validations.showValidationError(constraintViolations);
                return false;
            } else {

                User user = null;
                if (!item.getEmail().equals(oldEmail)) {
                    user = commonService.findByUserEmail(item.getEmail());
                }
                if (user instanceof User) {
                    throw new RuntimeException(" email นี้มีผู้ใช้งานแล้ว");
                } else {
                    return true;
                } 
            }
        }
    }
        
}
