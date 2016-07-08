/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service; 

import com.sabuymlm.authen.SecurityUtil; 
import com.sabuymlm.model.systemTest.BinaryBalanceDefine;
import com.sabuymlm.model.systemTest.BinaryMultiWsDefine;
import com.sabuymlm.model.systemTest.BinaryWsDefine;
import com.sabuymlm.model.systemTest.MatchingDefKey;
import com.sabuymlm.model.systemTest.MatchingDefine;
import com.sabuymlm.model.systemTest.MatchingDefineHeader;
import com.sabuymlm.model.systemTest.Position;  
import com.sabuymlm.model.systemTest.SponsorDefKey;
import com.sabuymlm.model.systemTest.SponsorDefine;
import com.sabuymlm.model.systemTest.SponsorDefineHeader;
import com.sabuymlm.model.systemTest.UnilevelDefKey;
import com.sabuymlm.model.systemTest.UnilevelDefine;
import com.sabuymlm.model.systemTest.UnilevelDefineHeader; 
import com.sabuymlm.model.systemTest.XSponsorDefineHeader;
import com.sabuymlm.model.systemTest.XSponsorHeaderKey;
import com.sabuymlm.repository.systemTest.BinaryBalanceDefRepository;
import com.sabuymlm.repository.systemTest.BinaryMultiWsDefRepository;
import com.sabuymlm.repository.systemTest.BinaryWsDefRepository;
import com.sabuymlm.repository.systemTest.MatchingDefRepository;
import com.sabuymlm.repository.systemTest.PositionRepository; 
import com.sabuymlm.repository.systemTest.SponsorDefRepository;
import com.sabuymlm.repository.systemTest.UnilevelDefRepository;
import com.sabuymlm.repository.systemTest.XSponsorDefRepository;
import com.sabuymlm.repository.systemTest.XSponsorHeaderRepository;
import java.util.Collection;
import java.util.List;
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
@Service(value = "systemTestService")
@Repository 
@Transactional 
public class SystemTestServiceImpl extends ConfigEntityManager implements SystemTestService {   

    @Autowired
    protected PositionRepository reposPositionEvent;   
    @Autowired
    protected SponsorDefRepository reposSponsorDefEvent;   
    @Autowired
    protected MatchingDefRepository reposMatchingDefEvent;   
    @Autowired
    protected UnilevelDefRepository reposUnilevelDefEvent;   
    @Autowired 
    protected XSponsorDefRepository reposXSponsorDefEvent;   
    @Autowired 
    protected XSponsorHeaderRepository reposXSponsorHeaderEvent;   
    @Autowired 
    protected BinaryWsDefRepository reposBinaryWsEvent;   
    @Autowired 
    protected BinaryMultiWsDefRepository reposBinaryMultiWsEvent;   
    @Autowired 
    protected BinaryBalanceDefRepository reposBinaryBalEvent;   
       

    @Override
    public Page<Position> findAllPosition(int startPage, int maxSize, Sort.Order order, String keyword) {
        setPageRequest(startPage,maxSize,order); 
        return reposPositionEvent.findByLike("%" + keyword + "%" , SecurityUtil.getUserDetails().getCompany() , pageRequest);
    }
    
    @Override
    public List<Position> findAllPositions() { 
        return reposPositionEvent.findByLike(SecurityUtil.getUserDetails().getCompany() ,  new Sort(Sort.Direction.ASC, "id") );
    } 

    @Override
    public Position findByPositionId(int positionId) {
        return reposPositionEvent.findOne(positionId);
    }

    @Override
    public void deleteAllPositions(List<Position> positions) {
        reposPositionEvent.delete(positions); 
    }

    @Override
    public Position savePosition(Position position) {
        return reposPositionEvent.save(position);
    }
    
    @Override
    public Page<SponsorDefineHeader> findAllSponsorHeaders(int startPage, int maxSize, Sort.Order order, String keyword) {
        setPageRequest(startPage,maxSize,order); 
        return reposSponsorDefEvent.findByLike("%" + keyword + "%" , SecurityUtil.getUserDetails().getCompany() , pageRequest);
    }

    @Transactional
    @Override
    public void deleteAllSponsors(List<SponsorDefineHeader> sponsors) {
        for(SponsorDefineHeader header : sponsors){
            reposSponsorDefEvent.delete(header.getItems()); 
        }
    }
    
    @Override
    public List<SponsorDefine> saveSponsorDefine(List<SponsorDefine> sponsorDefines) {
        return reposSponsorDefEvent.save(sponsorDefines); 
    }

    @Override
    public SponsorDefine findBySponsorDefineId(SponsorDefKey id) {
        return reposSponsorDefEvent.findOne(id);
    }

    @Override
    public Integer findBySponsorDefineMaxId() {
        return reposSponsorDefEvent.findByMaxId(SecurityUtil.getUserDetails().getCompany() );
    }

    @Override
    public List<SponsorDefine> findAllSponsorDefineByLevel(Integer level) {
        return reposSponsorDefEvent.findAllByLevel(level , SecurityUtil.getUserDetails().getCompany() ,  new Sort(Sort.Direction.ASC, "id.position.id")  );
    }
    
    
    @Override
    public Page<XSponsorDefineHeader> findAllXSponsorHeaders(int startPage, int maxSize, Sort.Order order, String keyword) {
        setPageRequest(startPage,maxSize,order); 
        return reposXSponsorDefEvent.findByLike("%" + keyword + "%" , SecurityUtil.getUserDetails().getCompany() , pageRequest);
    }

    @Override
    public void deleteAllXSponsors(List<XSponsorDefineHeader> xsponsors) {
        reposXSponsorHeaderEvent.delete(xsponsors);    
    }
    
    @Override
    public XSponsorDefineHeader saveXSponsorHeaderDefine(XSponsorDefineHeader xsponsorDefine) { 
        return reposXSponsorHeaderEvent.save(xsponsorDefine); 
    } 

    @Override
    public XSponsorDefineHeader findXSponsorDefineById(XSponsorHeaderKey id) {
        return reposXSponsorHeaderEvent.findOne(id);
    }

    @Override
    public Page<MatchingDefineHeader> findAllMatchingHeaders(int startPage, int maxSize, Sort.Order order, String keyword) {
        setPageRequest(startPage,maxSize,order); 
        return reposMatchingDefEvent.findByLike("%" + keyword + "%" , SecurityUtil.getUserDetails().getCompany() , pageRequest);
    }

    @Override
    public void deleteAllMatchings(List<MatchingDefineHeader> matchings) {
        for(MatchingDefineHeader header : matchings){
            reposMatchingDefEvent.delete(header.getItems()); 
        } 
    }

    @Override
    public MatchingDefine findByMatchingDefineId(MatchingDefKey id) {
        return reposMatchingDefEvent.findOne(id); 
    }

    @Override
    public Integer findByMatchingDefineMaxId() {
        return reposMatchingDefEvent.findByMaxId(SecurityUtil.getUserDetails().getCompany() );
    }

    @Override
    public List<MatchingDefine> saveMatchingDefine(List<MatchingDefine> matchingDefines) { 
        return reposMatchingDefEvent.save(matchingDefines); 
    }

    @Override
    public List<MatchingDefine> findAllMatchingDefineByLevel(Integer level) {
        return reposMatchingDefEvent.findAllByLevel(level , SecurityUtil.getUserDetails().getCompany() ,  new Sort(Sort.Direction.ASC, "id.position.id")  );
    }
    
    @Override
    public Page<UnilevelDefineHeader> findAllUnilevelHeaders(int startPage, int maxSize, Sort.Order order, String keyword) {
        setPageRequest(startPage,maxSize,order); 
        return reposUnilevelDefEvent.findByLike( SecurityUtil.getUserDetails().getCompany() , pageRequest);
    }

    @Override
    public void deleteAllUnilevels(List<UnilevelDefineHeader> unilevels) {
        for(UnilevelDefineHeader header : unilevels){
            reposUnilevelDefEvent.delete(header.getItems()); 
        } 
    }

    @Override
    public UnilevelDefine findByUnilevelDefineId(UnilevelDefKey id) {
        return reposUnilevelDefEvent.findOne(id); 
    }

    @Override
    public Integer findByUnilevelDefineMaxId() {
        return reposUnilevelDefEvent.findByMaxId(SecurityUtil.getUserDetails().getCompany() );
    }

    @Override
    public List<UnilevelDefine> saveUnilevelDefine(List<UnilevelDefine> unilevelDefines) { 
        return reposUnilevelDefEvent.save(unilevelDefines); 
    }

    @Override
    public List<UnilevelDefine> findAllUnilevelDefineByLevel(Integer level) {
        return reposUnilevelDefEvent.findAllByLevel(level , SecurityUtil.getUserDetails().getCompany() ,  new Sort(Sort.Direction.ASC, "id.position.id")  );
    }

    @Override
    public Collection<? extends BinaryWsDefine> findAllBinaryWsByCompany() {
        return reposBinaryWsEvent.findAllByCompany(SecurityUtil.getUserDetails().getCompany() ,  new Sort(Sort.Direction.ASC, "id.position.id")  );
    }

    @Override
    public List<BinaryWsDefine> saveBinaryWsDefine(List<BinaryWsDefine> selectItems) {
        return reposBinaryWsEvent.save(selectItems);  
    }
    
    @Override
    public Collection<? extends BinaryMultiWsDefine> findAllBinaryMultiWsByCompany() {
        return reposBinaryMultiWsEvent.findAllByCompany(SecurityUtil.getUserDetails().getCompany() ,  new Sort(Sort.Direction.ASC, "id.position.id")  );
    }

    @Override
    public List<BinaryMultiWsDefine> saveBinaryMultiWsDefine(List<BinaryMultiWsDefine> selectItems) {
        return reposBinaryMultiWsEvent.save(selectItems);  
    }

    @Override
    public Collection<? extends BinaryBalanceDefine> findAllBinaryBalanceByCompany() {
        return reposBinaryBalEvent.findAllByCompany(SecurityUtil.getUserDetails().getCompany() ,  new Sort(Sort.Direction.ASC, "id.position.id")  );
    }

    @Override
    public List<BinaryBalanceDefine> saveBinaryBalanceDefine(List<BinaryBalanceDefine> selectItems) {
        return reposBinaryBalEvent.save(selectItems);  
    }
    
}