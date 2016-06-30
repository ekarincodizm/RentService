/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.systemTest;
        
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.systemTest.SponsorDefine;
import com.sabuymlm.model.systemTest.SponsorDefKey;
import com.sabuymlm.model.systemTest.SponsorDefineHeader;
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
public interface SponsorDefRepository extends JpaRepository<SponsorDefine, SponsorDefKey> { 
 
    @Query("select u from SponsorDefineHeader u where ( UPPER(u.name) like UPPER(?1) ) "      
            + " and u.company = ?2 ")
    public Page<SponsorDefineHeader> findByLike(String keyword, Company company, Pageable pageRequest);
    @Query("select Max(u.id.levelIndex) from SponsorDefine u where u.company = ?1 ")
    public Integer findByMaxId(Company company); 
    
    @Query("select u from SponsorDefine u where u.id.levelIndex = ?1 and u.company = ?2 ")
    public List<SponsorDefine> findAllByLevel(Integer level, Company company ,Sort sort);
 
}
