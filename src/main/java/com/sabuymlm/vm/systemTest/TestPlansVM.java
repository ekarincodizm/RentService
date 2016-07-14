/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;
   
import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.systemTest.Position;
import com.sabuymlm.model.systemTest.TestPlan;
import com.sabuymlm.model.systemTest.TestPlanKey;
import com.sabuymlm.service.SystemTestService;
import com.sabuymlm.vm.CommonVM;
import java.io.Serializable;  
import java.util.Date;
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
public class TestPlansVM extends AddCommonRefSponsorDefineVM<TestPlan,TestPlan>  implements Serializable {

    @WireVariable
    private SystemTestService systemTestService; 
    
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) TestPlan item, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {
        selectItems.addAll(systemTestService.findAllTestPlanByCompany()) ; 
        pageHasDetail = false ;
        super.initial(item, icon, headerLabel);      
        setStatusEdit();
    }

    @Override
    protected void setEditItem() { 
        selectItems.addAll(systemTestService.findAllTestPlanByCompany()) ;
    } 

    @Override
    protected void setNewItem() { 
        if(selectItems.isEmpty()) {
            setStatusAdd(); 
            selectItems.clear();
            for(int i = 1; i <= 7 ;i++ ){
                TestPlan itm = new TestPlan(); 
                TestPlanKey itemKey = new TestPlanKey(); 
                itemKey.setCompany(SecurityUtil.getUserDetails().getCompany()); 
                itemKey.setNo(i); 
                itm.setId(itemKey);
                itm.setChkPay("false"); 
                itm.setPassMatchingStrong("false"); 
                itm.setPassMatchingWeakOrBonus("false"); 
                switch(i){
                    case 1 : itm.setPlanName("Sponsor (โบนัสแนะนำ)"); break; 
                    case 2 : itm.setPlanName("X-Diff Sponsor (โบนัสแนะนำแบบส่วนต่างจ่ายหมด)"); break; 
                    case 3 : itm.setPlanName("W/S(%) (โบนัสบริหารทีมแบบจ่าย อ่อน/แข็ง)"); break; 
                    case 4 : itm.setPlanName("Multi W/S(%) (โบนัสบริหารทีมแบบจ่าย อ่อน/แข็ง) คำนวณมากกว่า 1 ครั้ง(ผัง 3 ขาขึ้นไป)"); break; 
                    case 5 : itm.setPlanName("W/S(Balance) (โบนัสบริหารทีมแบบจ่าย จับคู่)"); break; 
                    case 6 : itm.setPlanName("Matching (โบนัสแมทชิ่งตามสายแนะนำ)"); break; 
                    case 7 : itm.setPlanName("Uni-level (โบนัส Uni-Level)"); break; 
                    default : break;
                }        
                selectItems.add(itm);
            } 
        }else {
            setStatusEdit();
        }
    }    
    
    @Transactional
    @Override 
    protected void saveItem() {  
        systemTestService.saveTestPlans(selectItems);
    }  
    
    @Override
    protected void setItems(){ 
        for(TestPlan def: selectItems){
            if(def.getCreateUser() != null){
                def.setUpdateDate(new Date());
                def.setUpdateUser(SecurityUtil.getUserDetails().getUser());
            }else {
                def.setCreateDate(new Date());
                def.setCreateUser(SecurityUtil.getUserDetails().getUser());
            } 
        } 
    }   
    
    @Command(value = {"onValidateTable"})
    @NotifyChange({"items","item"}) 
    public void validateTable(@BindingParam("item") TestPlan item) {  
        if(item.getChkPay().equals("true")){
            item.setChkPay("false");
        }else {
            item.setChkPay("true");
        }  
    } 

    @Override
    protected void privateValidate() { 
    }

    
    
}
