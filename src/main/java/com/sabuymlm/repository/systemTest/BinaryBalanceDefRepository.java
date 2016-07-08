/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.systemTest;
         
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.systemTest.BinaryBalanceDefine;
import com.sabuymlm.model.systemTest.BinaryBalanceDefKey;  
import java.util.Collection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;   
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bugteng
 */
public interface BinaryBalanceDefRepository extends JpaRepository<BinaryBalanceDefine, BinaryBalanceDefKey> { 
    @Query("select u from BinaryBalanceDefine u where u.id.company = ?1 ")
    public Collection<? extends BinaryBalanceDefine> findAllByCompany(Company company, Sort sort);
   
}
