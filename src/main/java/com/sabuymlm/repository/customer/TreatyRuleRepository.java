/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.customer;
  
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.customer.TreatyRule;
import java.util.Date;
import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 

/**
 *
 * @author bugteng
 */
public interface TreatyRuleRepository extends JpaRepository<TreatyRule, Integer> { 
    @Query("select u from TreatyRule u where ( UPPER(u.name) like UPPER(?1) "
            + " or UPPER(u.engname) like UPPER(?1) "
            + " or UPPER(u.contact) like UPPER(?1) "
            + " or UPPER(u.treatyAddrs) like UPPER(?1) "
            + " or UPPER(u.contactMobile) like UPPER(?1)  "
            + " or UPPER(u.remark) like UPPER(?1)  "
            + " or UPPER(u.customer.name) like UPPER(?1)  "
            + " or UPPER(u.customer.engname) like UPPER(?1) ) "
            + " and u.createDate between ?2 and ?3 " 
            + " and ( case when ?4 = 'ALL' then '' else u.treatyRuleStatus end ) = ( case when ?4 =  'ALL' then '' else ?4 end ) "  
            + " and u.company = ?5 ")
    public Page<TreatyRule> findByLike(String keyword,Date startdate ,Date enddate, String status, Company company, Pageable pageRequest); 
}
