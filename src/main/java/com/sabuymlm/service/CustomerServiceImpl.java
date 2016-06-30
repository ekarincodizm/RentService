/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;
  
import com.sabuymlm.authen.SecurityUtil; 
import com.sabuymlm.model.customer.Customer;
import com.sabuymlm.model.customer.TreatyRule;
import com.sabuymlm.repository.customer.CustomerRepository;
import com.sabuymlm.repository.customer.RelateDetailRepository;
import com.sabuymlm.repository.customer.TreatyRuleRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

/**
 *
 * @author bugteng
 */
@Service(value = "customerService")
@Repository 
@Transactional 
public class CustomerServiceImpl extends ConfigEntityManager implements CustomerService {   

    @Autowired
    protected CustomerRepository reposCustomer; 
    @Autowired
    protected TreatyRuleRepository reposTreatyRule;  
    @Autowired
    protected RelateDetailRepository reposRelateDetail  ;
    
    @Override
    public Page<Customer> findAllCustomer(int startPage, int maxSize ,Date startdate ,Date enddate,String status, Sort.Order order, String keyword) {
        setPageRequest(startPage,maxSize,order);
        return reposCustomer.findByLike("%" + keyword + "%",startdate,enddate, status, SecurityUtil.getUserDetails().getCompany() , pageRequest);
    }
    
    @Override
    public Page<Customer> findAllCustomer(int startPage, int maxSize , Sort.Order order, String keyword) {
        setPageRequest(startPage,maxSize,order);
        return reposCustomer.findByLike("%" + keyword + "%", SecurityUtil.getUserDetails().getCompany() , pageRequest);
    }

    @Override
    public Customer findByCustomerId(int customerId) {
        return reposCustomer.findOne(customerId);
    }

    @Override
    public void deleteAllCustomers(List<Customer> customers) {
        reposCustomer.delete(customers);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return reposCustomer.save(customer); 
    }

    @Override
    public Page<TreatyRule> findAllTreatyRule(int startPage, int maxSize ,Date startdate ,Date enddate, String status, Sort.Order order, String keyword) {
        setPageRequest(startPage,maxSize,order);
        return reposTreatyRule.findByLike("%" + keyword + "%", startdate,enddate, status , SecurityUtil.getUserDetails().getCompany() , pageRequest);
    }

    @Override
    public TreatyRule findByTreatyRuleId(int treatyRuleId) {
        return reposTreatyRule.findOne(treatyRuleId);
    }

    @Override
    public void deleteAllTreatyRules(List<TreatyRule> treatyRules) {
        reposTreatyRule.delete(treatyRules);
    }

    @Override
    public TreatyRule saveTreatyRule(TreatyRule treatyRule) {
        return reposTreatyRule.save(treatyRule); 
    }
     
}