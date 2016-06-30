/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm;

import com.sabuymlm.model.admin.User;
import com.sabuymlm.service.CommonService;
import com.sabuymlm.service.MailService;
import com.sabuymlm.utils.Validations;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

/**
 *
 * @author MY-TENG
 */
@VariableResolver(DelegatingVariableResolver.class)
public class NewMemberVM implements Serializable {

    @WireVariable
    private CommonService commonService;
    @WireVariable
    private MailService mailServices;
    @NotEmpty(message = "Name ห้ามเป็นค่าว่าง")
    private String name;
    private String mobile;
    @NotEmpty(message = "Email ห้ามเป็นค่าว่าง")
    private String email;
    @NotEmpty(message = "Username ห้ามเป็นค่าว่าง")
    private String username;
    @NotEmpty(message = "Password ห้ามเป็นค่าว่าง")
    private String password;
    @NotEmpty(message = "Re-password ห้ามเป็นค่าว่าง")
    private String repassword;
    private String txtCaptcha;
    ;
    private final Set<ConstraintViolation<NewMemberVM>> validateResult = new HashSet<ConstraintViolation<NewMemberVM>>();
    @WireVariable
    private Validator validator;

    private String errorMsg, errorStyle;

    @Init
    public void init() {
        repassword = "";
        password = "";
        email = null;
        name = null;
        mobile = null;
        username = null;
        txtCaptcha = null;
    }

    @Command
    @NotifyChange({"errorMsg", "errorStyle", "name", "email", "mobile", "username", "password", "repassword", "txtCaptcha"})
    public void onSubmit() throws Exception {
        if (!validateAll().isEmpty()) {
            Validations.showValidationError(validateResult);
            return;
        }
        if (txtCaptcha != null && !txtCaptcha.isEmpty()) {
            User user = commonService.findByUserEmail(email);
            if (user instanceof User) {
                errorMsg = "พบ email นี้ลงทะเบียนแล้ว";
                errorStyle = "color:red";
                txtCaptcha  = "" ;
                return;
            }
            User user2 = commonService.findByUserName(username);
            if (user2 instanceof User) {
                errorMsg = "Username นี้มีผู้ใช้งานแล้ว";
                errorStyle = "color:red";
                txtCaptcha  = "" ;
                return;
            }
            if (user == null || user2 == null) {
                if (password.equals(repassword)) {

                    user = new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setUsername(username);
                    user.setPassword(DigestUtils.md5Hex(password));
                    user.setPasswordShow(password);
                    user.setMobile(mobile);
                    user.setAdminLevel("USER-ADMIN");
                    user.setCompanyId(commonService.findByMaxCompanyId() + 1);

                    commonService.saveUser(user);

                    mailServices.createMailSender();
                    mailServices.sendAutoMail(email, user.getName(), user.getUsername(), user.getPasswordShow(), "(sabuymlm.com) New Member");
                    errorMsg = "Registration successful and send email.";
                    errorStyle = "color:green";
                    init();
                } else {
                    errorMsg = "ยืนยันรหัสผ่าน ไม่ถูกต้อง";
                    errorStyle = "color:red";
                    txtCaptcha  = "" ;
                }
            }
        } else {
            errorMsg = "กรอกตามภาพที่แสดงและให้ถูกต้อง";
            errorStyle = "color:red";
            txtCaptcha  = "" ;
        }

    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTxtCaptcha() {
        return txtCaptcha;
    }

    public void setTxtCaptcha(String txtCaptcha) {
        this.txtCaptcha = txtCaptcha;
    }

    private Set<ConstraintViolation<NewMemberVM>> validateAll() {
        validateResult.clear();
        validateResult.addAll(validator.validate(this));
        return validateResult;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getErrorStyle() {
        return errorStyle;
    }

}
