/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.systemTest;
      
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.systemTest.TestPlan; 
import com.sabuymlm.model.systemTest.TestPlanHeader; 
import java.util.Collection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bugteng
 */
public interface TestPlanRepository extends JpaRepository<TestPlanHeader, Company> {  

    @Query("select u from TestPlan u where u.id.company = ?1")
    public Collection<? extends TestPlan> findAllByCompany(Company company, Sort sort);
    
}
