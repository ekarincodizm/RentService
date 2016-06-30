/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.customer;
       
import com.sabuymlm.model.customer.RelateDetail;
import com.sabuymlm.model.customer.RelateDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;  

/**
 *
 * @author bugteng
 */
public interface RelateDetailRepository extends JpaRepository<RelateDetail, RelateDetailKey> { 
 
}
