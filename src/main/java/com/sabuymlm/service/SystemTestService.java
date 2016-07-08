/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;
          
import com.sabuymlm.model.systemTest.BinaryBalanceDefine;
import com.sabuymlm.model.systemTest.BinaryMultiWsDefine;
import com.sabuymlm.model.systemTest.BinaryWsDefine;
import com.sabuymlm.model.systemTest.Position;  
import com.sabuymlm.model.systemTest.SponsorDefine;
import com.sabuymlm.model.systemTest.SponsorDefKey;
import com.sabuymlm.model.systemTest.SponsorDefineHeader;
import com.sabuymlm.model.systemTest.MatchingDefine;
import com.sabuymlm.model.systemTest.MatchingDefineHeader;
import com.sabuymlm.model.systemTest.MatchingDefKey;
import com.sabuymlm.model.systemTest.UnilevelDefKey;
import com.sabuymlm.model.systemTest.UnilevelDefine;
import com.sabuymlm.model.systemTest.UnilevelDefineHeader;
import com.sabuymlm.model.systemTest.XSponsorDefineHeader;
import com.sabuymlm.model.systemTest.XSponsorHeaderKey;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 *
 * @author bugteng
 */

public interface SystemTestService {  
    
    public Page<Position> findAllPosition(int startPage , int maxSize , Sort.Order order, String keyword ) ; 
    public List<Position> findAllPositions() ;   
    public Position findByPositionId(int positionId) ; 
    public void deleteAllPositions(List<Position> positions) ; 
    public Position savePosition(Position position) ;
    
    public Page<SponsorDefineHeader> findAllSponsorHeaders(int startPage , int maxSize , Sort.Order order, String keyword ); 
    public void deleteAllSponsors(List<SponsorDefineHeader> sponsors); 
    public SponsorDefine findBySponsorDefineId(SponsorDefKey id) ; 
    public Integer findBySponsorDefineMaxId() ; 
    public List<SponsorDefine> saveSponsorDefine(List<SponsorDefine> sponsorDefines) ;
    public List<SponsorDefine> findAllSponsorDefineByLevel(Integer level) ;
    
    public Page<XSponsorDefineHeader> findAllXSponsorHeaders(int startPage , int maxSize , Sort.Order order, String keyword ); 
    public void deleteAllXSponsors(List<XSponsorDefineHeader> xsponsors); 
    public XSponsorDefineHeader saveXSponsorHeaderDefine(XSponsorDefineHeader xsponsorDefine) ; 
    public XSponsorDefineHeader findXSponsorDefineById(XSponsorHeaderKey id); 
    
    public Page<MatchingDefineHeader> findAllMatchingHeaders(int startPage , int maxSize , Sort.Order order, String keyword ); 
    public void deleteAllMatchings(List<MatchingDefineHeader> matchings); 
    public MatchingDefine findByMatchingDefineId(MatchingDefKey id) ; 
    public Integer findByMatchingDefineMaxId() ; 
    public List<MatchingDefine> saveMatchingDefine(List<MatchingDefine> matchingDefines) ;
    public List<MatchingDefine> findAllMatchingDefineByLevel(Integer level) ;
    
    public Page<UnilevelDefineHeader> findAllUnilevelHeaders(int startPage , int maxSize , Sort.Order order, String keyword ); 
    public void deleteAllUnilevels(List<UnilevelDefineHeader> unlevels); 
    public UnilevelDefine findByUnilevelDefineId(UnilevelDefKey id) ; 
    public Integer findByUnilevelDefineMaxId() ; 
    public List<UnilevelDefine> saveUnilevelDefine(List<UnilevelDefine> unlevelDefines) ;
    public List<UnilevelDefine> findAllUnilevelDefineByLevel(Integer level) ;

    public Collection<? extends BinaryWsDefine> findAllBinaryWsByCompany(); 
    public List<BinaryWsDefine> saveBinaryWsDefine(List<BinaryWsDefine> selectItems);
    
    public Collection<? extends BinaryMultiWsDefine> findAllBinaryMultiWsByCompany(); 
    public List<BinaryMultiWsDefine> saveBinaryMultiWsDefine(List<BinaryMultiWsDefine> selectItems);
    
    public Collection<? extends BinaryBalanceDefine> findAllBinaryBalanceByCompany(); 
    public List<BinaryBalanceDefine> saveBinaryBalanceDefine(List<BinaryBalanceDefine> selectItems);
    
    
}
