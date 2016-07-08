/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.systemTest;
         
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.systemTest.BinaryMultiWsDefine;
import com.sabuymlm.model.systemTest.BinaryMultiWsDefKey;  
import java.util.Collection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;   
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bugteng
 */
public interface BinaryMultiWsDefRepository extends JpaRepository<BinaryMultiWsDefine, BinaryMultiWsDefKey> { 
    @Query("select u from BinaryMultiWsDefine u where u.id.company = ?1 ")
    public Collection<? extends BinaryMultiWsDefine> findAllByCompany(Company company, Sort sort);
   
}
