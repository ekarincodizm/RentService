/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository;
  
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.admin.ExpensesType;
import java.util.List;
import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 

/**
 *
 * @author bugteng
 */
public interface ExpensesTypeRepository extends JpaRepository<ExpensesType, Integer> { 
    @Query("select u from ExpensesType u where u.company =?2 and (UPPER(u.name) like UPPER(?1)  or UPPER(u.description) like UPPER(?1) or UPPER(u.engname) like UPPER(?1) ) ")
    public Page<ExpensesType> findByLike(String keyword ,Company company, Pageable pageRequest);   
    
    @Query("select u from ExpensesType u where u.company = ?1 ") 
    public List<ExpensesType> findAllByCompany(Company compnay, Sort sort);
    
    @Query("select u from ExpensesType u where u.company = ?1 and u.expensesIncomeType = ?2 ") 
    public List<ExpensesType> findAllByCompanyAndType(Company compnay,String incexptype, Sort sort);
}
