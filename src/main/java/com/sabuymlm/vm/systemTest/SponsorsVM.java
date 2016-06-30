/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;

import com.sabuymlm.model.systemTest.Position;  
import com.sabuymlm.model.systemTest.SponsorDefineHeader;
import com.sabuymlm.service.SystemTestService;
import com.sabuymlm.vm.CommonVM;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.BindUtils;
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
public class SponsorsVM extends CommonVM<SponsorDefineHeader> implements Serializable {

    @WireVariable
    private SystemTestService systemTestService;
    private final List<Position> positions = new ArrayList<Position>();
    private final List<String[]> headers = new ArrayList<String[]>();

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {
        initialVM("/secured/systemTest/addSponsorsDef.zul", "id", icon, headerLabel);
        search();
        headers.add(new String[]{"No.", "60px"});
        headers.add(new String[]{"แก้ไข", "60px"});
        headers.add(new String[]{"ชื่อโบนัส", "100px"});
        headers.add(new String[]{"Level", "50px"});
        positions.addAll(systemTestService.findAllPositions());
        for (Position pos : positions) {
            headers.add(new String[]{pos.getName(), "100%"});
        }
    }

    @Override
    public void load() {
        items = systemTestService.findAllSponsorHeaders(getActivePage(), getPageSize(), order, getKeyword());
    }

    @Override
    public void deleteSelected() {
        systemTestService.deleteAllSponsors(selectItems);  
        search();
        BindUtils.postNotifyChange(null, null, SponsorsVM.this, ".");
    }

    public List<String[]> getHeaders() {
        return headers;
    }

}
