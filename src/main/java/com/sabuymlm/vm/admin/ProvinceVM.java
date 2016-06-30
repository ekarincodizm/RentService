/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.admin;

import com.sabuymlm.model.admin.Country;
import com.sabuymlm.model.admin.Province; 
import com.sabuymlm.service.CommonService;
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
public class ProvinceVM extends CommonVM<Province> implements Serializable {

    @WireVariable
    private CommonService commonService;
    private final List<Country> countrys = new ArrayList<Country>();
    private Country selectedCountry  ;
            
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {  
        initialVM("/secured/admin/addProvince.zul","name",icon,headerLabel); 
        setDefaultCountry();
        countrys.addAll(commonService.findAllCountry()); 
        search();
    }  
    
    private void setDefaultCountry(){
        Country defaultEmpty = new Country(-1," <-- ทุกประเทศ -->");
        countrys.add(defaultEmpty);
        selectedCountry = defaultEmpty ;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public List<Country> getCountrys() {
        return countrys;
    }
    
    @Override
    public void load() {
        items = commonService.findAllProvince(getActivePage(), getPageSize(), order, getKeyword(),selectedCountry);
    }

    @Override
    public void deleteSelected() {
        commonService.deleteAllProvince(selectItems);
        search();
        BindUtils.postNotifyChange(null, null, ProvinceVM.this, ".");
    }  
}
