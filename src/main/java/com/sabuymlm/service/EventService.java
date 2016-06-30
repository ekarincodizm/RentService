/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;
        
import com.sabuymlm.model.admin.ExpensesType;
import com.sabuymlm.model.event.ExpensesIncome;
import com.sabuymlm.model.event.RentEvent;
import com.sabuymlm.model.transformers.SumBean;
import java.util.Date;
import java.util.List; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 *
 * @author bugteng
 */

public interface EventService { 
    
    public Page<RentEvent> findAllRentEvent(int startPage , int maxSize ,Date startdate ,Date enddate,String status,String reStatus, Sort.Order order, String keyword ) ; 
    public RentEvent findByRentEventId(int rentEventId) ; 
    public void deleteAllRentEvents(List<RentEvent> rentEvents) ; 
    public RentEvent saveRentEvent(RentEvent rentEvent) ;
    
    public Page<ExpensesIncome> findAllExpensesIncome(int startPage , int maxSize ,Date startdate ,Date enddate,String status ,ExpensesType expensesType, Sort.Order order, String keyword ) ; 
    public SumBean findSumExpensesIncome(Date startdate, Date enddate, String status ,ExpensesType expensesType , String keyword);
    public ExpensesIncome findByExpensesIncomeId(int expensesIncomeId) ; 
    public void deleteAllExpensesIncomes(List<ExpensesIncome> expensesIncomes) ; 
    public ExpensesIncome saveExpensesIncome(ExpensesIncome expensesIncome) ;
    
}
