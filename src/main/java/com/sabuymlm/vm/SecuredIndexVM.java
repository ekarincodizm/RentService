/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm;

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.service.CommonService;
import com.sabuymlm.utils.CommonException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Center;

/**
 *
 * @author MY-TENG
 */
@VariableResolver(DelegatingVariableResolver.class)
public class SecuredIndexVM implements Serializable {

    @WireVariable
    private CommonService commonService;

    @Init
    public void init() {
        Company company = commonService.findByCompanyId(SecurityUtil.getUserDetails().getUser().getCompanyId());
        if (company == null) {
            Company comp = commonService.findByCompanyId(1);
            company = new Company();
            company.setId(SecurityUtil.getUserDetails().getUser().getCompanyId());
            company.setCompanyName("Company Name");
            company.setCompanyNameEng("Company Name");
            company.setUploadPath(comp.getUploadPath());
            company.setLogo(comp.getLogo());
            commonService.saveCompany(company);
        }
        SecurityUtil.getUserDetails().getUser().setLastLoginDate(new Date());
        commonService.saveUser(SecurityUtil.getUserDetails().getUser());
        SecurityUtil.getUserDetails().setCompany(company);
    }

    @Command
    public void clickMenuTab(@BindingParam("target") Component target, @BindingParam("tabindex") Integer tabindex) {
        if (target instanceof Center) {
            if (!target.getChildren().isEmpty()) {
                target.getChildren().clear();
            }
        }
    }

    @Command
    public void loadDefaultPage(@BindingParam("target") Component target) {
        onClickMenu(target, "/secured/event/rentEvent.zul", "/images/icons/32x32/folder-invoices.png", "ตรวจสอบค่าเช่ารายเดือน");
    }

    @Command
    public void onLogOut() throws CommonException {
        Executions.sendRedirect("/logout");
    }

    public String getMyLoginName() {
        return SecurityUtil.getUserDetails().getUser().getName();
    }

    @Command
    public void onClickMenu(@BindingParam("target") Component target, @BindingParam("pageName") String pageName, @BindingParam("icon") String icon, @BindingParam("headerLabel") String headerLabel) {
        if (target instanceof Center) {
            if (!target.getChildren().isEmpty()) {
                target.getChildren().clear();
            }
            Map map = new HashMap();
            map.put("icon", icon);
            map.put("headerLabel", headerLabel);
            Executions.createComponents(pageName, target, map);
        } else {
            throw new RuntimeException("ไม่สามารภเปิด link " + pageName);
        }
    }

    public boolean isAdmin() {
        return (SecurityUtil.getUserDetails().getUser().getAdminLevel().equals("ADMIN"));
    }

    public boolean isUserAdmin() {
        return ((SecurityUtil.getUserDetails().getUser().getAdminLevel().equals("USER-ADMIN")) || isAdmin());
    }

}
