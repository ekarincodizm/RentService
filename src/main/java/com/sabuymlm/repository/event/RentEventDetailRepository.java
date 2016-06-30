/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.event;
     
import com.sabuymlm.model.event.RentEventDetail;  
import com.sabuymlm.model.event.RentEventDetailKey; 
import org.springframework.data.jpa.repository.JpaRepository;  

/**
 *
 * @author bugteng
 */
public interface RentEventDetailRepository extends JpaRepository<RentEventDetail, RentEventDetailKey> { 
 
}
