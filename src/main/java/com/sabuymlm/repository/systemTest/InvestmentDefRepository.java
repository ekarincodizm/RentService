/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.systemTest;
         
import com.sabuymlm.model.admin.Company; 
import com.sabuymlm.model.systemTest.InvestmentDefKey;
import com.sabuymlm.model.systemTest.InvestmentDefine;
import java.util.Collection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;   
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bugteng
 */
public interface InvestmentDefRepository extends JpaRepository<InvestmentDefine, InvestmentDefKey> { 
    @Query("select u from InvestmentDefine u where u.id.company = ?1 ")
    public Collection<? extends InvestmentDefine> findAllByCompany(Company company, Sort sort);
   
}
