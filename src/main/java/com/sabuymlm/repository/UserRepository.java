/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.repository;
 
import com.sabuymlm.model.admin.User;
import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 

/**
 *
 * @author bugteng
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where UPPER(u.username) = UPPER(?1) ")
    public User findByUsername(String username);  
    @Query("select u from User u where UPPER(u.email) = UPPER(?1) ")
    public User findByEmail(String email);  
    @Query("select u from User u where (UPPER(u.username) like UPPER(?1) or UPPER(u.name) like UPPER(?1) ) ")
    public Page<User> findByLike(String keyword, Pageable pageRequest);  
    @Query("select u from User u where u.companyId = ?2 and (UPPER(u.username) like UPPER(?1) or UPPER(u.name) like UPPER(?1) ) ")
    public Page<User> findByLike(String keyword, Integer companyId, Pageable pageRequest);  
    @Query("select u from User u where u.id = ?2 and (UPPER(u.username) like UPPER(?1) or UPPER(u.name) like UPPER(?1) ) ")
    public Page<User> findByLikeUser(String keyword, Integer id, Pageable pageRequest);  
}
