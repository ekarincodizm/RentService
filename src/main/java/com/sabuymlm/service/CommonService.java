/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;
   
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.admin.Country;
import com.sabuymlm.model.admin.District;
import com.sabuymlm.model.admin.Province;
import com.sabuymlm.model.admin.ExpensesType;
import com.sabuymlm.model.admin.User;
import java.util.List;
import org.springframework.data.domain.Page;   
import org.springframework.data.domain.Sort;

/**
 *
 * @author bugteng
 */

public interface CommonService { 
    public Page<User> findAllUser(int startPage , int maxSize , Sort.Order order, String keyword ) ; 
    public Page<User> findAllUser(int startPage , int maxSize , Sort.Order order, String keyword , Company company ) ; 
    public Page<User> findAllUser(int startPage, int maxSize, Sort.Order order, String keyword, Integer id ); 
    public User findByUserName(String username) ; 
    public User findByUserEmail(String email) ; 
    public void deleteAllUsers(List<User> users) ; 
    public User saveUser(User user) ; 
    
    public Page<Country> findAllCountry(int startPage , int maxSize , Sort.Order order, String keyword) ; 
    public List<Country> findAllCountry(); 
    public Country findByCountryId(int countryId) ; 
    public void deleteAllCountry(List<Country> countrys) ; 
    public Country saveCountry(Country country) ; 

    public Page<ExpensesType> findAllExpensesType(int startPage , int maxSize , Sort.Order order, String keyword , Company company); 
    public ExpensesType findByExpensesTypeId(int expensesTypeId); 
    public void deleteAllExpensesType(List<ExpensesType> expensesTypes); 
    public ExpensesType saveExpensesType(ExpensesType expensesType); 
    public List<ExpensesType> findAllExpensesType(Company company); 
    public List<ExpensesType> findAllExpensesType(Company company,String incexptype); 
    
    public Page<Province> findAllProvince(int startPage , int maxSize , Sort.Order order, String keyword , Country country) ; 
    public List<Province> findAllProvince();
    public List<Province> findAllProvince(Country country);
    public Province findByProvinceId(int provinceId) ; 
    public void deleteAllProvince(List<Province> provinces) ; 
    public Province saveProvince(Province province) ; 
    
    public Page<District> findAllDistrict(int startPage , int maxSize , Sort.Order order, String keyword , Country country , Province province) ; 
    public List<District> findAllDistrict(Province province);
    public District findByDistrictId(int districtId) ; 
    public void deleteAllDistrict(List<District> districts) ; 
    public District saveDistrict(District district) ; 
 
    public Company findByCompanyId(int companyId) ; 
    public Integer findByMaxCompanyId() ; 
    public Company saveCompany(Company company) ; 

    
    
}
