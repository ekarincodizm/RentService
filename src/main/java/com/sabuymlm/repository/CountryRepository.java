/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository;
 
import com.sabuymlm.model.admin.Country;
import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 

/**
 *
 * @author bugteng
 */
public interface CountryRepository extends JpaRepository<Country, Integer> { 
    @Query("select u from Country u where (UPPER(u.name) like UPPER(?1) or UPPER(u.code) like UPPER(?1)  or UPPER(u.engname) like UPPER(?1) ) ")
    public Page<Country> findByLike(String keyword, Pageable pageRequest); 
}
