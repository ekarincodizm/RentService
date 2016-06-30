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
public class ForgetPasswordVM implements Serializable {
    @WireVariable
    private CommonService commonService;
    @WireVariable
    private MailService mailServices;
    @NotEmpty(message = "Email ห้ามเป็นค่าว่าง")
    private String email;
    @NotEmpty(message = "password ห้ามเป็นค่าว่าง")
    private String password;
    @NotEmpty(message = "re-password ห้ามเป็นค่าว่าง")
    private String repassword;
    private final Set<ConstraintViolation<ForgetPasswordVM>> validateResult = new HashSet<ConstraintViolation<ForgetPasswordVM>>();
    @WireVariable
    private Validator validator;
    
    private String errorMsg , errorStyle ;
    
    @Init
    public void init() {
        repassword = "";
        password = "";
        email = "";
    } 

    @Command
    @NotifyChange({"errorMsg", "errorStyle", "email", "password", "repassword"})
    public void onReset() throws Exception {
        if (!validateAll().isEmpty()) {
            Validations.showValidationError(validateResult);
            return ;
        } 
        User user = commonService.findByUserEmail(email); 
        if(user instanceof User){
            if(password.equals(repassword)){
                user.setPassword(DigestUtils.md5Hex(password)); 
                user.setPasswordShow(password); 
                commonService.saveUser(user);
                mailServices.createMailSender();
                mailServices.sendResetPasswordMail(email, user.getName(), user.getUsername(), user.getPasswordShow(), "(sabuymlm.com) Reset Password");
                errorMsg = "Reset successful and send email.";
                errorStyle = "color:green";
                init();
            }else{
                errorMsg = "ยืนยันรหัสผ่าน ไม่ถูกต้อง";
                errorStyle = "color:red";
            }
        }else {
            errorMsg = "ไม่พบ email ในระบบลงทะเบียน";
            errorStyle = "color:red";
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

    private Set<ConstraintViolation<ForgetPasswordVM>> validateAll() {
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
