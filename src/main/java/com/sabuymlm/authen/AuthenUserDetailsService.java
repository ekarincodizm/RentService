/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.authen;

import com.sabuymlm.model.admin.User;
import com.sabuymlm.service.CommonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;  
 

/**
 * 
 * @author bugteng for teng override
 */
@Transactional
public class AuthenUserDetailsService implements UserDetailsService {

    @Autowired
    private CommonService services;
    private Logger log = Logger.getLogger(AuthenUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {

        // //////////////////////////
        User user = new User();
        try {
            user = services.findByUserName(username);  
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        // If userExtraInfo not exist then create for login user.
        if (user == null) {
            user = new User();
        }
        // //////////////////////////
        return new AuthenUserDetails(user);

    }
}
