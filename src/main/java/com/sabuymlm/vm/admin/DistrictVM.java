/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.admin;
 
import com.sabuymlm.model.admin.Country;
import com.sabuymlm.model.admin.District;
import com.sabuymlm.model.admin.Province;
import com.sabuymlm.service.CommonService;
import com.sabuymlm.vm.CommonVM;
import java.io.Serializable;  
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.BindUtils;  
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
public class DistrictVM extends CommonVM<District> implements Serializable {

    @WireVariable
    private CommonService commonService;
    private final List<Country> countrys = new ArrayList<Country>();
    private Country selectedCountry  ;
    private final List<Province> provinces = new ArrayList<Province>();
    private Province selectedProvince  ;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {  
        initialVM("/secured/admin/addDistrict.zul","name",icon,headerLabel);
        setDefaultCountry();
        countrys.addAll(commonService.findAllCountry());  
        onSearchCountry();
        search();
    }  
    
    @Command
    @NotifyChange({"provinces","selectedProvince","items"})
    public void onSearchCountry(){
        setDefaultProvince();
        provinces.addAll(commonService.findAllProvince(selectedCountry)); 
        search();
    }
    
    private void setDefaultCountry(){
        Country defaultEmpty = new Country(-1," <-- ทุกประเทศ -->");
        countrys.add(defaultEmpty);
        selectedCountry = defaultEmpty ;
    }
    
    private void setDefaultProvince(){
        provinces.clear();
        Province defaultEmpty = new Province(-1," <-- ทุกจังหวัด -->");
        provinces.add(defaultEmpty);
        selectedProvince = defaultEmpty ;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public Province getSelectedProvince() {
        return selectedProvince;
    }

    public void setSelectedProvince(Province selectedProvince) {
        this.selectedProvince = selectedProvince;
    } 

    public List<Country> getCountrys() {
        return countrys;
    }

    public List<Province> getProvinces() {
        return provinces;
    } 
    
    @Override
    public void load() {
        items = commonService.findAllDistrict(getActivePage(), getPageSize(), order, getKeyword(),selectedCountry,selectedProvince);
    }

    @Override
    public void deleteSelected() {
        commonService.deleteAllDistrict(selectItems);
        search();
        BindUtils.postNotifyChange(null, null, DistrictVM.this, ".");
    }  
}
