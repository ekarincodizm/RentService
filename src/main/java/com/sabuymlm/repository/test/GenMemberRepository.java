/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.test;
       
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.test.GenMember; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bugteng
 */
public interface GenMemberRepository extends JpaRepository<GenMember, Long> {  

    @Query("select u from GenMember u where u.company =?1 ")
    public Page<GenMember> findByLike(Company company, Pageable pageRequest);
    
}
