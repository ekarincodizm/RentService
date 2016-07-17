/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;
           
import com.sabuymlm.model.test.GenMember;
import org.springframework.data.domain.Page;

/**
 *
 * @author bugteng
 */

public interface TestService {  

    public Page<GenMember> findAllGenMembers(int activeMemberPage, int pageMemberSize);
      
}
