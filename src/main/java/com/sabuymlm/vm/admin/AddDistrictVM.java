/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.admin;

import com.sabuymlm.model.admin.Country;
import com.sabuymlm.model.admin.District; 
import com.sabuymlm.model.admin.Province;
import com.sabuymlm.service.CommonService;
import com.sabuymlm.vm.CommonAddVM; 
import com.sabuymlm.vm.CommonVM;
import java.io.Serializable; 
import java.util.ArrayList;
import java.util.List; 
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver; 

/**
 *
 * @author MY-TENG
 */
@VariableResolver(DelegatingVariableResolver.class)
public class AddDistrictVM extends CommonAddVM<District> implements Serializable {

    @WireVariable
    private CommonService commonService;
    private final List<Province> provinces = new ArrayList<Province>();
    private List<Country> countrys = new ArrayList<Country>();
    
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) District item
        , @ExecutionArgParam("icon") String icon
        , @ExecutionArgParam("headerLabel") String headerLabel ) { 
        initial(item,icon,headerLabel); 
        countrys = commonService.findAllCountry(); 
    }
    
    @Command 
    @NotifyChange({"provinces","item"})
    public void onSearchCountry(){ 
        provinces.clear();
        provinces.addAll(commonService.findAllProvince(item.getCountry())); 
        item.setProvince(null);  
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public List<Country> getCountrys() {
        return countrys;
    }

    @Override
    protected void setEditItem() {
        item = commonService.findByDistrictId(item.getId()); 
    }

    @Override
    protected void setNewItem() {
        item = new District();
    }

    @Override
    protected void saveItem() {
        commonService.saveDistrict(item); 
    }

    @Override
    protected boolean validate() { 
        return true ;
    }
}
