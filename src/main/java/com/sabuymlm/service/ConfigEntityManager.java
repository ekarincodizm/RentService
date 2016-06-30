/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager; 
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional; 

/**
 *
 * @author MY-TENG
 */

@Repository 
@Transactional
public abstract class ConfigEntityManager {
    @PersistenceContext(name = "RentService")
    protected EntityManager em;
    
    protected PageRequest pageRequest;
    
    protected Session unwrapHibernateSession(){
        HibernateEntityManager hem = em.unwrap(HibernateEntityManager.class);
        return hem.getSession(); 
    }
    protected void setPageRequest(int startPage, int maxSize, Sort.Order order){
        pageRequest = new PageRequest(startPage, maxSize, new Sort(order));
    }
}

