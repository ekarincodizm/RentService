/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.authen;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.admin.User;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; 

/**
 *
 * @author bugteng edit by teng
 */
public class AuthenUserDetails implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 8343502186553730024L;
    private final String username;
    private final String password;
    private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    private User user;
    private Company company;  
    private Locale locale;

    AuthenUserDetails(User user) {
        username = user.getUsername();
        password = user.getPassword();
        this.user = user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public String toString() {
        return "AuthenUserDetails [authorities=" + authorities + ", password=" + password + ", username=" + username + "]";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    } 

    public Locale getLocale() {
        return locale;
    }
    
    public String getLanguage() {
        ///  th_TH = Thai
        ///  en = English 
        ///  lo_LA = Lao  
        return locale.getLanguage();
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
