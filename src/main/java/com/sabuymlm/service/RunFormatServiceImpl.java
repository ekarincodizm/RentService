/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;
   
import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.utils.Format;
import java.util.Date;  
import org.hibernate.Query;  
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;  

/**
 *
 * @author sumrit
 */
@Service(value = "runService")
@Repository
@Transactional
public class RunFormatServiceImpl extends ConfigEntityManager implements RunFormatService { 
      
    private String runMaxPrefixCode(String prefix, String sql) {
        prefix = prefix + Format.formatDateThai("yyMM", new Date()); 
        Query query = unwrapHibernateSession().createSQLQuery(sql) ;
        query.setString("prefix", prefix + "%" );
        query.setInteger("companyId", SecurityUtil.getUserDetails().getUser().getCompanyId() );
        String code = (String) query.uniqueResult(); 
        String nextCode = prefix ;
        if(code == null || code.isEmpty()) {
            nextCode += "0001";
        }else {
            String replaceStr = code.replace(nextCode, "");
            int maxcode = 0 ;
            if(Format.isNumeric(replaceStr)) {
                maxcode = Integer.parseInt(replaceStr); 
            }
            nextCode += Format.formatNumber("0000", maxcode + 1);
        } 
        return nextCode ; 
    }

    @Override
    public String rentEventCode() {
        return runMaxPrefixCode("INV" ,"select max(rent_code) from rent_event where rent_code like :prefix and company_id = :companyId "); 
    }

    @Override
    public String expensesIncomeCode() {
        return runMaxPrefixCode("EIM" ,"select max(expenses_income_code) from expenses_income where expenses_income_code like :prefix and company_id = :companyId  "); 
    }
    
}
