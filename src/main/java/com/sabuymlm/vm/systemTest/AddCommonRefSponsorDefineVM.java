/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;
      
import com.sabuymlm.model.systemTest.Position;    
import com.sabuymlm.utils.Validations;
import com.sabuymlm.vm.CommonAddVM;  
import java.io.Serializable;   
import java.util.ArrayList; 
import java.util.HashSet;
import java.util.List; 
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.transaction.annotation.Transactional;  
import org.zkoss.zk.ui.select.annotation.WireVariable;  

/**
 *
 * @author MY-TENG 
 */ 
public abstract class AddCommonRefSponsorDefineVM<V,T> extends CommonAddVM<T> implements Serializable {
  
    @WireVariable
    protected Validator validator;
    protected final Set<ConstraintViolation> constraintViolations = new HashSet<ConstraintViolation>();
     
    protected final List<Position> positions = new ArrayList<Position>(); 
    protected List<V> selectItems = new ArrayList<V>();   

    @Override
    protected abstract void setEditItem() ;  
    @Override
    protected abstract void setNewItem() ; 
    
    public boolean isVisibleRaw(){
        return !positions.isEmpty();
    } 
    @Transactional
    @Override
    protected abstract void saveItem() ;  
    protected abstract void setItems() ;
    protected abstract void privateValidate() ;

    private Set<ConstraintViolation> validateAll() {  
        setItems(); 
        constraintViolations.clear(); 
        privateValidate(); 
        if( item != null ) { 
            constraintViolations.addAll(validator.validate(item)); 
        } 
        for(V dt:selectItems){ 
            constraintViolations.addAll(validator.validate(dt));
        } 
        return constraintViolations;
    } 

    @Override
    protected boolean validate() {
        if (!validateAll().isEmpty()) {  
            Validations.showValidationError(constraintViolations); 
            return false;
        }else {
            return true;
        }
    } 

    public List<V> getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(List<V> selectItems) {
        this.selectItems = selectItems;
    }   
    
    public List<Position> getPositions() {
        return positions;
    } 
}
