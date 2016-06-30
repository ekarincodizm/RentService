/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;
     
import com.sabuymlm.model.customer.Customer;
import com.sabuymlm.model.customer.TreatyRule;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;


/**
 *
 * @author bugteng
 */

public interface CustomerService { 
    public Page<Customer> findAllCustomer(int startPage , int maxSize ,Date startdate ,Date enddate,String status, Sort.Order order, String keyword ) ; 
    public Page<Customer> findAllCustomer(int startPage , int maxSize , Sort.Order order, String keyword ) ; 
    public Customer findByCustomerId(int customerId) ; 
    public void deleteAllCustomers(List<Customer> customers) ; 
    public Customer saveCustomer(Customer customer) ;
    
    public Page<TreatyRule> findAllTreatyRule(int startPage , int maxSize ,Date startdate ,Date enddate,String status, Sort.Order order, String keyword ) ; 
    public TreatyRule findByTreatyRuleId(int treatyRuleId) ; 
    public void deleteAllTreatyRules(List<TreatyRule> treatyRules) ; 
    public TreatyRule saveTreatyRule(TreatyRule treatyRule) ;
    
}
