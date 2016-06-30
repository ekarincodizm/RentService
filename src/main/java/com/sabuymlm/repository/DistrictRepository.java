/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository;
 
import com.sabuymlm.model.admin.District;
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
public interface DistrictRepository extends JpaRepository<District, Integer> { 
    @Query("select u from District u where (UPPER(u.name) like UPPER(?1) or UPPER(u.engname) like UPPER(?1) ) "
            + " and ( case when ?2 <= 0 then 0 else u.province.country.id end ) = ( case when ?2 <= 0 then 0 else ?2 end ) " 
            + " and ( case when ?3 <= 0 then 0 else u.province.id end ) = ( case when ?3 <= 0 then 0 else ?3 end ) ")
    public Page<District> findByLike(String keyword, int countryId, int provinceId, Pageable pageRequest); 

    @Query("select u from District u where u.province = ?1 ") 
    public List<District> findAllByProvince(Province province, Sort sort);
}
