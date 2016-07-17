/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;
 
import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.test.GenMember;
import com.sabuymlm.repository.test.GenMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bugteng
 */
@Service(value = "testService")
@Repository
@Transactional
public class TestServiceImpl extends ConfigEntityManager implements TestService {

    @Autowired
    protected GenMemberRepository reposGenMemberEvent;

    @Override
    public Page<GenMember> findAllGenMembers(int activeMemberPage, int pageMemberSize) {
        setPageRequest(activeMemberPage, pageMemberSize, new Sort.Order(Sort.Direction.ASC, "id"));
        return reposGenMemberEvent.findByLike(SecurityUtil.getUserDetails().getCompany(), pageRequest);
    }
    
    
}


