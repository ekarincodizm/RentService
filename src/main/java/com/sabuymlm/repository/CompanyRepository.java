/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository;
   
import com.sabuymlm.model.admin.Company;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bugteng
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {  
    @Query("select max(u.id) from Company u")
    public Integer findByMaxCompanyId();
}
