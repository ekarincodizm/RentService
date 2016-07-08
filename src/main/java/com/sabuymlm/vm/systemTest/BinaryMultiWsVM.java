/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;
 
import com.sabuymlm.authen.SecurityUtil; 
import com.sabuymlm.model.systemTest.BinaryMultiWsDefKey;
import com.sabuymlm.model.systemTest.BinaryMultiWsDefine; 
import com.sabuymlm.model.systemTest.BinaryMultiWsDetail;
import com.sabuymlm.model.systemTest.BinaryMultiWsDetailKey;
import com.sabuymlm.model.systemTest.Position; 
import com.sabuymlm.service.SystemTestService; 
import com.sabuymlm.vm.CommonVM;  
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date; 
import java.util.List;
import org.springframework.transaction.annotation.Transactional;  
import org.zkoss.bind.annotation.BindingParam;
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
public class BinaryMultiWsVM extends AddCommonRefSponsorDefineVM<BinaryMultiWsDefine,BinaryMultiWsDefine> implements Serializable {

    @WireVariable
    private SystemTestService systemTestService;    
    
    @Init 
    public void init(@ContextParam(ContextType.VIEW) Component view
            , @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) BinaryMultiWsDefine item
            , @ExecutionArgParam("icon") String icon
            , @ExecutionArgParam("headerLabel") String headerLabel) {
        selectItems.addAll(systemTestService.findAllBinaryMultiWsByCompany()) ;
        for(BinaryMultiWsDefine itm :selectItems){
            itm.getItems();
        }
        pageHasDetail = false ;
        super.initial(item, icon, headerLabel);      
        setStatusEdit();
    } 
    
    @Command(value = {"onCalculateCharts"})
    @NotifyChange({"selectItems"})
    public void calculateCharts(@BindingParam("selected") Integer selectedChart ) {  
        generateChartDetails(selectedChart);
    } 
    
    public String getSelectChart(){
        int select = 3;
        if(!selectItems.isEmpty()) {
            if(!selectItems.get(0).getItems().isEmpty()){
                select = selectItems.get(0).getItems().size() + 1 ;
            } 
        } 
        return select + "";
    } 
    
    private void generateChartDetails(Integer selectedChart ){ 
        if(!selectItems.isEmpty()) {
            for(BinaryMultiWsDefine itm : selectItems){
                 
                List<BinaryMultiWsDetail> ls = new ArrayList<BinaryMultiWsDetail>();
                for(int i= 1;i <selectedChart ; i++ ){
                    BinaryMultiWsDetailKey dtkey = new BinaryMultiWsDetailKey(itm.getId(),i);
                    BinaryMultiWsDetail dt = itm.findKeyId(dtkey); 
                    if(dt == null ) {
                        dt = new BinaryMultiWsDetail();
                        dt.setId(dtkey); 
                        dt.setWsPcent(0f);
                        dt.setStPcent(0f);
                    }
                    ls.add(dt); 
                } 
                itm.setItems(ls); 
            }
        }
    }
    
    @Override
    protected void setEditItem() { 
        selectItems.addAll(systemTestService.findAllBinaryMultiWsByCompany()) ;
        for(BinaryMultiWsDefine itm :selectItems){
            itm.getItems();
        }
    }

    @Override
    protected void setNewItem() { 
        if(selectItems.isEmpty()) {
            setStatusAdd();
            positions.addAll(systemTestService.findAllPositions()); 
            selectItems.clear();
            for(Position pos : positions){
                BinaryMultiWsDefine itm = new BinaryMultiWsDefine(); 
                BinaryMultiWsDefKey itemKey = new BinaryMultiWsDefKey(); 
                itemKey.setCompany(SecurityUtil.getUserDetails().getCompany()); 
                itemKey.setPosition(pos); 
                itm.setId(itemKey);
                itm.setPowerCharts(3); 
                itm.setMaxComm(0.0f); 
                itm.setCircleUnit(1); 
                selectItems.add(itm); 
            } 
            generateChartDetails(3);
        }else {
            setStatusEdit();
        }
    }   

    @Transactional
    @Override
    protected void saveItem() { 
        selectItems = systemTestService.saveBinaryMultiWsDefine(selectItems);
    }  
    
    @Override
    protected void setItems(){ 
        for(BinaryMultiWsDefine def: selectItems){
            if(def.getCreateUser() != null){
                def.setUpdateDate(new Date());
                def.setUpdateUser(SecurityUtil.getUserDetails().getUser());
            }else {
                def.setCreateDate(new Date());
                def.setCreateUser(SecurityUtil.getUserDetails().getUser());
            } 
        } 
    }   
    
    @Override
    protected void privateValidate() {  
        for(BinaryMultiWsDefine def: selectItems){
            for(BinaryMultiWsDetail dt: def.getItems()){
                constraintViolations.addAll(validator.validate(dt)); 
            }
        } 
    }  
      
}
