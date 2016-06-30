/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.systemTest;
        
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.systemTest.XSponsorDefine;
import com.sabuymlm.model.systemTest.XSponsorDefKey;
import com.sabuymlm.model.systemTest.XSponsorDefineHeader; 
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable;  
import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bugteng
 */
public interface XSponsorDefRepository extends JpaRepository<XSponsorDefine, XSponsorDefKey> { 
 
    @Query("select u from XSponsorDefineHeader u where ( UPPER( u.id.position.name ) like UPPER(?1) ) "      
            + " and u.id.company = ?2 ")
    public Page<XSponsorDefineHeader> findByLike(String keyword, Company company, Pageable pageRequest); 
 
}
