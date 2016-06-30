/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service; 

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.ExpensesType;
import com.sabuymlm.model.event.ExpensesIncome;
import com.sabuymlm.model.event.RentEvent;
import com.sabuymlm.model.transformers.SumBean;
import com.sabuymlm.repository.event.ExpensesIncomeRepository;
import com.sabuymlm.repository.event.RentEventDetailRepository;
import com.sabuymlm.repository.event.RentEventRepository;
import java.util.Date;
import java.util.List; 
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
@Service(value = "eventService")
@Repository 
@Transactional 
public class EventServiceImpl extends ConfigEntityManager implements EventService {   

    @Autowired
    protected RentEventRepository reposRentEvent;   
    @Autowired
    protected ExpensesIncomeRepository reposExpensesIncome;   
    @Autowired
    protected RentEventDetailRepository reposRentEventDt;   

    @Override
    public Page<RentEvent> findAllRentEvent(int startPage, int maxSize, Date startdate, Date enddate, String status , String reStatus, Sort.Order order, String keyword) {
        setPageRequest(startPage,maxSize,order);
        if (status != null && status.equals("ALL_PAUSE")){
            return reposRentEvent.findByLikeNotPause("%" + keyword + "%",startdate,enddate, reStatus, SecurityUtil.getUserDetails().getCompany() , pageRequest);
        }else {
            return reposRentEvent.findByLike("%" + keyword + "%",startdate,enddate, status, reStatus, SecurityUtil.getUserDetails().getCompany() , pageRequest);
        }
    }

    @Override
    public RentEvent findByRentEventId(int rentEventId) {
        return reposRentEvent.findOne(rentEventId);
    }

    @Override
    public void deleteAllRentEvents(List<RentEvent> rentEvents) {
        reposRentEvent.delete(rentEvents);
    }

    @Transactional 
    @Override
    public RentEvent saveRentEvent(RentEvent rentEvent) { 
        if (rentEvent.getId() != null) {
            reposRentEventDt.save(rentEvent.getItemDetails());
        }  
        return reposRentEvent.save(rentEvent);
    }

    @Override
    public Page<ExpensesIncome> findAllExpensesIncome(int startPage, int maxSize, Date startdate, Date enddate, String status , ExpensesType expensesType, Sort.Order order, String keyword) {
        setPageRequest(startPage,maxSize,order); 
        return reposExpensesIncome.findByLike("%" + keyword + "%",startdate,enddate, status 
                , (expensesType != null && expensesType.getId() > 0 ? expensesType  : null )
                , SecurityUtil.getUserDetails().getCompany() , pageRequest);
    }
    
    @Override
    public SumBean findSumExpensesIncome(Date startdate, Date enddate, String status ,ExpensesType expensesType, String keyword) { 
        Query query = unwrapHibernateSession().createSQLQuery("select cast( sum((case when u.expenses_income_type = 'Y' then u.total_amount else 0.0 end )) as float) sum1 "
            + " , cast( sum((case when u.expenses_income_type = 'N' then u.total_amount else 0.0 end )) as float) sum2 "
            + " from expenses_income u where ( UPPER(u.expenses_income_code) like UPPER(:keyword) "
            + " or UPPER(u.remark) like UPPER(:keyword) ) "  
            + " and u.expenses_income_date between :startdate and :enddate " 
            + " and ( case when :status = 'ALL' then 'Y' else u.expenses_income_type end ) = ( case when :status =  'ALL' then 'Y' else :status end ) "   
            + " and ( case when :expensesTypeId <= 0 then :expensesTypeId else u.type_id end ) = :expensesTypeId "   
            + " and u.company_id = :companyId ")
                .setResultTransformer(Transformers.aliasToBean(SumBean.class));
        query.setString("keyword", "%" + keyword + "%");
        query.setDate("startdate", startdate);
        query.setDate("enddate", enddate);
        query.setString("status", status);
        query.setInteger("expensesTypeId", (expensesType != null && expensesType.getId() > 0 ? expensesType.getId() : -1 ));
        query.setInteger("companyId", SecurityUtil.getUserDetails().getCompany().getId() );
        return (SumBean) query.uniqueResult(); 
    }

    @Override
    public ExpensesIncome findByExpensesIncomeId(int expensesIncomeId) {
        return reposExpensesIncome.findOne(expensesIncomeId);
    }

    @Override
    public void deleteAllExpensesIncomes(List<ExpensesIncome> expensesIncomes) {
        reposExpensesIncome.delete(expensesIncomes); 
    }

    @Override
    public ExpensesIncome saveExpensesIncome(ExpensesIncome expensesIncome) {
        return reposExpensesIncome.save(expensesIncome);
    }
     
}