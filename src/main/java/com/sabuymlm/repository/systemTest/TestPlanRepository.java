/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.systemTest;
       
import com.sabuymlm.model.systemTest.TestPlan;   
import com.sabuymlm.model.systemTest.TestPlanKey; 
import org.springframework.data.jpa.repository.JpaRepository;   

/**
 *
 * @author bugteng
 */
public interface TestPlanRepository extends JpaRepository<TestPlan, TestPlanKey> {  
 
    
}
