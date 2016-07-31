/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;
           
import com.sabuymlm.model.test.GenMember;
import com.sabuymlm.model.test.Ws;
import com.sabuymlm.utils.Pageable;
import org.springframework.data.domain.Page;

/**
 *
 * @author bugteng
 */

public interface TestService {  

    public Page<GenMember> findAllGenMembers(int activeMemberPage, int pageMemberSize);
    public Page<GenMember> findAllBonusGenMembers(int activePage, int pageSize);

    public Pageable<Ws> findAllBonusWsGen(int activeWsPage, int pageWsSize);
    public Pageable<Ws> findAllBonusWsBlGen(int activeWsPage, int pageWsSize); 
      
}
