/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.systemTest;
         
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.systemTest.BinaryWsDefine;
import com.sabuymlm.model.systemTest.BinaryWsDefKey;  
import java.util.Collection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;   
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bugteng
 */
public interface BinaryWsDefRepository extends JpaRepository<BinaryWsDefine, BinaryWsDefKey> { 
    @Query("select u from BinaryWsDefine u where u.id.company = ?1 ")
    public Collection<? extends BinaryWsDefine> findAllByCompany(Company company, Sort sort);
   
}
