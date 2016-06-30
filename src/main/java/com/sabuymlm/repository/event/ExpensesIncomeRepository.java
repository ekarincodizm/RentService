/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.event;
    
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.admin.ExpensesType;
import com.sabuymlm.model.event.ExpensesIncome;  
import java.util.Date; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bugteng
 */
public interface ExpensesIncomeRepository extends JpaRepository<ExpensesIncome, Integer> { 

    @Query("select u from ExpensesIncome u where ( UPPER(u.code) like UPPER(?1) "
            + " or UPPER(u.remark) like UPPER(?1) ) "  
            + " and u.expensesDate between ?2 and ?3 " 
            + " and ( case when ?4 = 'ALL' then 'Y' else u.expensesIncomeType end ) = ( case when ?4 =  'ALL' then 'Y' else ?4 end ) "   
            + " and ( case when ?5 is null then 1 else u.expensesType end ) = ( case when ?5 is null then 1 else ?5 end ) "   
            + " and u.company = ?6 ")
    public Page<ExpensesIncome> findByLike(String keyword, Date startdate, Date enddate, String status , ExpensesType expensesType , Company company, Pageable pageRequest);
     
    
}
