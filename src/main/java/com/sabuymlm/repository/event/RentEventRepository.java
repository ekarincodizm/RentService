/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.event;
    
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.event.RentEvent;  
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bugteng
 */
public interface RentEventRepository extends JpaRepository<RentEvent, Integer> { 

    @Query("select u from RentEvent u where ( UPPER(u.code) like UPPER(?1) "
            + " or UPPER(u.remark) like UPPER(?1) " 
            + " or UPPER(u.customer.name) like UPPER(?1)  "
            + " or UPPER(u.customer.engname) like UPPER(?1) ) "
            + " and u.rentDate between ?2 and ?3 " 
            + " and ( case when ?4 = 'ALL' then '' else u.rentStatus end ) = ( case when ?4 =  'ALL' then '' else ?4 end ) "  
            + " and ( case when ?5 = 'ALL' then '' else u.reRentStatus end ) = ( case when ?5 =  'ALL' then '' else ?5 end ) "  
            + " and u.company = ?6 " 
            + " and u.customer.rentStatus = 'Y' ")
    public Page<RentEvent> findByLike(String keyword, Date startdate, Date enddate, String status, String reStatus, Company company, Pageable pageRequest);
    
    @Query("select u from RentEvent u where ( UPPER(u.code) like UPPER(?1) "
            + " or UPPER(u.remark) like UPPER(?1) " 
            + " or UPPER(u.customer.name) like UPPER(?1)  "
            + " or UPPER(u.customer.engname) like UPPER(?1) ) "
            + " and u.rentDate between ?2 and ?3 " 
            + " and u.rentStatus <> 'PAUSE' "  
            + " and ( case when ?4 = 'ALL' then '' else u.reRentStatus end ) = ( case when ?4 =  'ALL' then '' else ?4 end ) "  
            + " and u.company = ?5 "
            + " and u.customer.rentStatus = 'Y' ")
    public Page<RentEvent> findByLikeNotPause(String keyword, Date startdate, Date enddate, String reStatus, Company company, Pageable pageRequest);
    
}
