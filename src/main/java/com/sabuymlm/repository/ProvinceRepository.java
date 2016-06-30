/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository;
  
import com.sabuymlm.model.admin.Country;
import com.sabuymlm.model.admin.Province;
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
public interface ProvinceRepository extends JpaRepository<Province, Integer> { 
    @Query("select u from Province u where ( UPPER(u.name) like UPPER(?1) or UPPER(u.engname) like UPPER(?1) ) "
            + " and ( case when ?2 <= 0 then 0 else u.country.id end ) = ( case when ?2 <= 0 then 0 else ?2 end ) ")
    public Page<Province> findByLike(String keyword, int country, Pageable pageRequest); 
    
    @Query("select u from Province u where u.country = ?1 ")
    public List<Province> findAllByCountry(Country country, Sort sort);
}
