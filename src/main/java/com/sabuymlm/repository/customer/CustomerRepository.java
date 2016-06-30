/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.customer;
  
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.customer.Customer;
import java.util.Date;
import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 

/**
 *
 * @author bugteng
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> { 
    @Query("select u from Customer u where ( UPPER(u.name) like UPPER(?1) "
            + " or UPPER(u.engname) like UPPER(?1) "
            + " or UPPER(u.contact) like UPPER(?1) "
            + " or UPPER(u.addrs) like UPPER(?1) "
            + " or UPPER(u.mobile) like UPPER(?1) ) and u.company = ?5 "
            + " and u.createDate between ?2 and ?3 "
            + " and ( case when ?4 = 'ALL' then '' else u.rentStatus end ) = ( case when ?4 =  'ALL' then '' else ?4 end ) "  )
    public Page<Customer> findByLike(String keyword,Date startdate ,Date enddate,String status,Company company, Pageable pageRequest); 
    
    @Query("select u from Customer u where ( UPPER(u.name) like UPPER(?1) "
            + " or UPPER(u.engname) like UPPER(?1) "
            + " or UPPER(u.contact) like UPPER(?1) "
            + " or UPPER(u.addrs) like UPPER(?1) "
            + " or UPPER(u.mobile) like UPPER(?1) ) and u.company = ?2 and u.rentStatus ='Y' " )
    public Page<Customer> findByLike(String keyword,Company company, Pageable pageRequest); 
}
