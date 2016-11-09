/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;
 
import com.sabuymlm.authen.SecurityUtil; 
import com.sabuymlm.model.test.GenMember;
import com.sabuymlm.model.test.Ws; 
import com.sabuymlm.repository.test.GenMemberRepository;
import com.sabuymlm.utils.Pageable; 
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
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
    
    @Override
    public Pageable<GenMember> findAllBonusSponsorGen(int pageIndex, int pageSize) {
        Query query = unwrapHibernateSession().createSQLQuery("select count(*) totalElements \n" 
                + "   , sum(sponsor_bonus) sum1 , sum(sponsor_pro_bonus) sum2  \n" 
                + "  from test.member_gen u where u.company_id = :companyId  ")
                .setResultTransformer(Transformers.aliasToBean(Pageable.class));
        query.setInteger("companyId", SecurityUtil.getUserDetails().getCompany().getId());  
        Object o = query.uniqueResult();
        Pageable<GenMember> pageable;
        if (o instanceof Pageable) {
            pageable = (Pageable<GenMember>) o;
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(pageSize); 
            if(pageable.getTotalElements() > 0 ) { 
                        
                query = unwrapHibernateSession().createSQLQuery("  select * from test.member_gen u  WHERE u.company_id = :companyId and u.id BETWEEN :firstRow AND :maxRow ORDER BY id ")
                        .addEntity(GenMember.class); 
                query.setInteger("companyId", SecurityUtil.getUserDetails().getCompany().getId());   
                query.setInteger("firstRow", pageable.getFirstRow());
                query.setInteger("maxRow", pageable.getMaxRow());
                pageable.setContent(query.list()); 
                
            }
        }else { 
            pageable = new Pageable<GenMember>();
        } 
        return pageable; 
    }

    @Override
    public Pageable<Ws> findAllBonusWsGen(int pageIndex, int pageSize) {
        Query query = unwrapHibernateSession().createSQLQuery("select count(*) totalElements \n" 
                + "   , sum(weak_bonus) sum1 , sum(strong_bonus) sum2, sum(bonus) sum3  \n" 
                + "  from test.weakstrong_bonus_report(:companyId)  ")
                .setResultTransformer(Transformers.aliasToBean(Pageable.class));
        query.setInteger("companyId", SecurityUtil.getUserDetails().getCompany().getId());  
        Object o = query.uniqueResult();
        Pageable<Ws> pageable;
        if (o instanceof Pageable) {
            pageable = (Pageable<Ws>) o;
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(pageSize); 
            if(pageable.getTotalElements() > 0 ) {
                
                query = unwrapHibernateSession().createSQLQuery("  select  id ,  level_gen levelGen , position_name positionName , upline_id uplineId , align , total_member totalMember , organization_pv organizationPv \n" +
"		, weak ,  strong ,  wk_pcent wkPcent , st_pcent stPcent , max_bonus maxBonus , weak_bonus weakBonus , strong_bonus strongBonus , bonus , circle  \n" 
                + "   from test.weakstrong_bonus_report(:companyId)  \n"  
                + " WHERE id BETWEEN :firstRow AND :maxRow ORDER BY id ")
                    .setResultTransformer(Transformers.aliasToBean(Ws.class));
                query.setInteger("companyId", SecurityUtil.getUserDetails().getCompany().getId());   
                query.setInteger("firstRow", pageable.getFirstRow());
                query.setInteger("maxRow", pageable.getMaxRow());
                pageable.setContent(query.list()); 
                
            }
        }else { 
            pageable = new Pageable<Ws>();
        } 
        return pageable; 
    }
    
    @Override
    public Pageable<Ws> findAllBonusWsBlGen(int pageIndex, int pageSize) {
        Query query = unwrapHibernateSession().createSQLQuery("select count(*) totalElements \n" 
                + "   , sum(bonus) sum1 \n" 
                + "  from test.weakstrong_balance_bonus_report(:companyId)  ")
                .setResultTransformer(Transformers.aliasToBean(Pageable.class));
        query.setInteger("companyId", SecurityUtil.getUserDetails().getCompany().getId());  
        Object o = query.uniqueResult();
        Pageable<Ws> pageable;
        if (o instanceof Pageable) {
            pageable = (Pageable<Ws>) o;
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(pageSize); 
            if(pageable.getTotalElements() > 0 ) {
                
                query = unwrapHibernateSession().createSQLQuery("  select  id ,  level_gen levelGen , position_name positionName , upline_id uplineId , align , total_member totalMember , organization_pv organizationPv \n" +
"		, weak ,  strong ,  wk_balance wkPcent , wk_bonus stPcent , max_bonus maxBonus , max_unit maxUnit , balance weakBonus , bonus_unit unit , bonus , circle  \n" 
                + "   from test.weakstrong_balance_bonus_report(:companyId)  \n"  
                + " WHERE id BETWEEN :firstRow AND :maxRow ORDER BY id ")
                    .setResultTransformer(Transformers.aliasToBean(Ws.class));
                query.setInteger("companyId", SecurityUtil.getUserDetails().getCompany().getId());   
                query.setInteger("firstRow", pageable.getFirstRow());
                query.setInteger("maxRow", pageable.getMaxRow());
                pageable.setContent(query.list()); 
                
            }
        }else { 
            pageable = new Pageable<Ws>();
        } 
        return pageable; 
    }
    
    @Override
    public Pageable<GenMember> findAllBonusMatchungGen(int pageIndex, int pageSize) {
        Query query = unwrapHibernateSession().createSQLQuery("select count(*) totalElements \n" 
                + "   , sum(matching_bonus) sum1 , sum(matching_pro_bonus) sum2  \n" 
                + "  from test.member_gen u where u.company_id = :companyId  ")
                .setResultTransformer(Transformers.aliasToBean(Pageable.class));
        query.setInteger("companyId", SecurityUtil.getUserDetails().getCompany().getId());  
        Object o = query.uniqueResult();
        Pageable<GenMember> pageable;
        if (o instanceof Pageable) {
            pageable = (Pageable<GenMember>) o;
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(pageSize); 
            if(pageable.getTotalElements() > 0 ) { 
                        
                query = unwrapHibernateSession().createSQLQuery("  select * from test.member_gen u  WHERE u.company_id = :companyId and u.id BETWEEN :firstRow AND :maxRow ORDER BY id ")
                        .addEntity(GenMember.class); 
                query.setInteger("companyId", SecurityUtil.getUserDetails().getCompany().getId());   
                query.setInteger("firstRow", pageable.getFirstRow());
                query.setInteger("maxRow", pageable.getMaxRow());
                pageable.setContent(query.list()); 
                
            }
        }else { 
            pageable = new Pageable<GenMember>();
        } 
        return pageable; 
    } 
    
    @Override
    public Pageable<GenMember> findAllBonusUniLevelGen(int pageIndex, int pageSize) {
        Query query = unwrapHibernateSession().createSQLQuery("select count(*) totalElements \n" 
                + "   , sum(unilevel_bonus) sum1 , sum(unilevel_pro_bonus) sum2  \n" 
                + "  from test.member_gen u where u.company_id = :companyId  ")
                .setResultTransformer(Transformers.aliasToBean(Pageable.class));
        query.setInteger("companyId", SecurityUtil.getUserDetails().getCompany().getId());  
        Object o = query.uniqueResult();
        Pageable<GenMember> pageable;
        if (o instanceof Pageable) {
            pageable = (Pageable<GenMember>) o;
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(pageSize); 
            if(pageable.getTotalElements() > 0 ) { 
                        
                query = unwrapHibernateSession().createSQLQuery("  select * from test.member_gen u  WHERE u.company_id = :companyId and u.id BETWEEN :firstRow AND :maxRow ORDER BY id ")
                        .addEntity(GenMember.class); 
                query.setInteger("companyId", SecurityUtil.getUserDetails().getCompany().getId());   
                query.setInteger("firstRow", pageable.getFirstRow());
                query.setInteger("maxRow", pageable.getMaxRow());
                pageable.setContent(query.list()); 
                
            }
        }else { 
            pageable = new Pageable<GenMember>();
        } 
        return pageable; 
    } 
    
}


