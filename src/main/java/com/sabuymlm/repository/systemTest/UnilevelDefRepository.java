/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.systemTest;
        
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.systemTest.UnilevelDefine;
import com.sabuymlm.model.systemTest.UnilevelDefKey;
import com.sabuymlm.model.systemTest.UnilevelDefineHeader;
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
public interface UnilevelDefRepository extends JpaRepository<UnilevelDefine, UnilevelDefKey> { 
 
    @Query("select u from UnilevelDefineHeader u where u.company = ?1 ")
    public Page<UnilevelDefineHeader> findByLike( Company company, Pageable pageRequest);
    @Query("select Max(u.id.levelIndex) from UnilevelDefine u where u.company = ?1 ")
    public Integer findByMaxId(Company company); 
    
    @Query("select u from UnilevelDefine u where u.id.levelIndex = ?1 and u.company = ?2 ")
    public List<UnilevelDefine> findAllByLevel(Integer level, Company company ,Sort sort);
 
}
