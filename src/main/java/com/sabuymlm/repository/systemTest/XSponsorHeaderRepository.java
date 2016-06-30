/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository.systemTest;
          
import com.sabuymlm.model.systemTest.XSponsorDefineHeader;
import com.sabuymlm.model.systemTest.XSponsorHeaderKey;
import org.springframework.data.jpa.repository.JpaRepository;   

/**
 *
 * @author bugteng
 */
public interface XSponsorHeaderRepository extends JpaRepository<XSponsorDefineHeader, XSponsorHeaderKey > { 
  
}
