/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabuymlm.utils;

/**
 *
 * @author MY-TENG
 */ 
import java.util.Iterator; 
import java.util.Set; 
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.zkoss.bind.Property;
import org.zkoss.bind.SimpleForm;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator; 
 
public class BeanValidator extends AbstractValidator {
        
        private static final Validator _validator = Validation.buildDefaultValidatorFactory().getValidator();
        private static Set validate(Class clazz, String propName, Object value) {
            return _validator.validateValue(clazz, propName, value);
        }
        
	protected Validator getValidator(){
		return _validator;
	}  
	
	protected Object[] getValidationInfo(ValidationContext ctx, Object base, String property){
		Class<?> clz = base.getClass(); 
		return new Object[]{clz,property};
	}
	 
	
        @Override 
	public void validate(ValidationContext ctx) {
		final Property p = ctx.getProperty();
		final Object base = p.getBase();
		String property = p.getProperty();
		final Object value = p.getValue(); 
                 
                
		Object[] info = getValidationInfo(ctx, base, property);
		Class<?> clz = (Class<?>)info[0];
                 
		if(value instanceof SimpleForm ){ 
                    SimpleForm formValues = (SimpleForm)value;
                    Iterator<?> values = formValues.getFieldNames().iterator();  
                    while(values.hasNext()) {
                        Object fieldName = values.next();
                        Object fieldValue = formValues.getField((String)fieldName) ;  
                        Set<ConstraintViolation<?>> violations = validate(clz, (String)fieldName, fieldValue) ; 
                        for(ConstraintViolation<?> v : violations){ 
                            addInvalidMessage(ctx, (String)fieldName, v.getMessage()); 
                        }
                    }  
                }else {
                    property = (String)info[1];  
                    Set<ConstraintViolation<?>> violations = validate(clz, property, value); 
                    for(ConstraintViolation<?> v : violations)
                            addInvalidMessage(ctx, property, v.getMessage()); 
                }   
                 
	}
}