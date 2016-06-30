/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm;

import com.sabuymlm.authen.AuthenUserDetails;
import com.sabuymlm.utils.CommonException;
import com.sabuymlm.utils.Validations;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

/**
 *
 * @author MY-TENG
 */
@VariableResolver(DelegatingVariableResolver.class)
public class IndexVM implements Serializable {

    @NotEmpty(message = "Username ห้ามเป็นค่าว่าง")
    private String username;
    @NotEmpty(message = "password ห้ามเป็นค่าว่าง")
    private String password;
    private final Set<ConstraintViolation<IndexVM>> validateResult = new HashSet<ConstraintViolation<IndexVM>>();
    @WireVariable
    private Validator validator;

    @Init
    public void init() {
        username = "";
        password = "";
    }

    @Command
    public void onLogOut() throws CommonException {
        Executions.sendRedirect("/logout");
    }

    @Command
    public void onLogin() throws CommonException {
        if (!validateAll().isEmpty()) {
            Validations.showValidationError(validateResult);
            return;
        }

        AuthenticationManager authenMgr = (AuthenticationManager) SpringUtil.getApplicationContext().getBean("authenManager");
        Authentication result;
        try {
            result = authenMgr.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(result);

            AuthenUserDetails staff = (AuthenUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (staff != null && staff.getUser() != null) {
                Executions.sendRedirect("/secured/index.zul");
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new CommonException(e.getMessage());
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Set<ConstraintViolation<IndexVM>> validateAll() {
        validateResult.clear();
        validateResult.addAll(validator.validate(this));
        return validateResult;
    }

}
