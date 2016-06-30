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
import com.sabuymlm.repository.CompanyRepository;
import com.sabuymlm.repository.CountryRepository;
import com.sabuymlm.repository.DistrictRepository;
import com.sabuymlm.repository.ProvinceRepository;
import com.sabuymlm.repository.ExpensesTypeRepository;
import com.sabuymlm.repository.UserRepository;
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
@Service(value = "commonService" )
@Repository 
@Transactional 
public class CommonServiceImpl extends ConfigEntityManager implements CommonService {   
    
    @Autowired
    protected UserRepository reposUser; 
    @Autowired
    protected CompanyRepository reposCompany; 
    @Autowired
    protected CountryRepository reposCountry; 
    @Autowired
    protected ExpensesTypeRepository reposExpensesType; 
    @Autowired
    protected ProvinceRepository reposProvince; 
    @Autowired
    protected DistrictRepository reposDistrict;  
    
    @Override
    public Page<User> findAllUser(int startPage, int maxSize, Sort.Order order, String keyword ) {
        setPageRequest(startPage,maxSize,order);
        return reposUser.findByLike("%" + keyword + "%" , pageRequest);
    } 
    
    @Override
    public Page<User> findAllUser(int startPage, int maxSize, Sort.Order order, String keyword, Company company ) {
        setPageRequest(startPage,maxSize,order);
        return reposUser.findByLike("%" + keyword + "%", company.getId() , pageRequest);
    } 
    
    @Override
    public Page<User> findAllUser(int startPage, int maxSize, Sort.Order order, String keyword, Integer id ) {
        setPageRequest(startPage,maxSize,order);
        return reposUser.findByLikeUser("%" + keyword + "%", id , pageRequest);
    } 

    @Override
    public User findByUserName(String username) { 
        return reposUser.findByUsername(username);
    } 
    
    @Override
    public User findByUserEmail(String email) { 
        return reposUser.findByEmail(email);
    } 

    @Override
    public void deleteAllUsers(List<User> users) {
        reposUser.delete(users);
    } 

    @Override
    public User saveUser(User user) {
        return reposUser.save(user);
    } 

    @Override
    public Page<Country> findAllCountry(int startPage, int maxSize, Sort.Order order, String keyword) {
        setPageRequest(startPage,maxSize,order);
        return reposCountry.findByLike("%" + keyword + "%", pageRequest);
    }
    
    @Override
    public List<Country> findAllCountry() {
        return reposCountry.findAll(new Sort(Sort.Direction.ASC, "name")); 
    }

    @Override
    public void deleteAllCountry(List<Country> countrys) {
        reposCountry.delete(countrys); 
    }

    @Override
    public Country findByCountryId(int countryId) {
        return reposCountry.findOne(countryId);
    }

    @Override
    public Country saveCountry(Country country) {
        return reposCountry.save(country);
    }

    @Override
    public Page<ExpensesType> findAllExpensesType(int startPage, int maxSize, Sort.Order order, String keyword,Company company) {
        setPageRequest(startPage,maxSize,order);
        return reposExpensesType.findByLike("%" + keyword + "%",company, pageRequest);
    }

    @Override
    public ExpensesType findByExpensesTypeId(int expensesTypeId) {
        return reposExpensesType.findOne(expensesTypeId);
    }

    @Override
    public void deleteAllExpensesType(List<ExpensesType> expensesTypes) {
        reposExpensesType.delete(expensesTypes); 
    }

    @Override
    public ExpensesType saveExpensesType(ExpensesType expensesType) {
        return reposExpensesType.save(expensesType); 
    }

    @Override
    public Page<Province> findAllProvince(int startPage, int maxSize, Sort.Order order, String keyword  , Country country) {
        setPageRequest(startPage,maxSize,order); 
        return reposProvince.findByLike("%" + keyword + "%", country.getId() , pageRequest);
    }
    
    @Override
    public List<Province> findAllProvince() {
        return reposProvince.findAll(new Sort(Sort.Direction.ASC, "name")); 
    }
    
    @Override
    public List<Province> findAllProvince(Country country) {
        return reposProvince.findAllByCountry(country,new Sort(Sort.Direction.ASC, "name")); 
    }

    @Override
    public Province findByProvinceId(int provinceId) {
        return reposProvince.findOne(provinceId);
    }

    @Override
    public void deleteAllProvince(List<Province> provinces) {
        reposProvince.delete(provinces); 
    }

    @Override
    public Province saveProvince(Province province) {
        return reposProvince.save(province);
    }

    @Override
    public Page<District> findAllDistrict(int startPage, int maxSize, Sort.Order order, String keyword , Country country , Province province) {
        setPageRequest(startPage,maxSize,order);
        return reposDistrict.findByLike("%" + keyword + "%", country.getId(), province.getId(), pageRequest);
    }
    
    @Override
    public List<District> findAllDistrict(Province province) {
        return reposDistrict.findAllByProvince(province,new Sort(Sort.Direction.ASC, "name")); 
    }

    @Override
    public District findByDistrictId(int districtId) {
        return reposDistrict.findOne(districtId);
    }

    @Override
    public void deleteAllDistrict(List<District> districts) {
        reposDistrict.delete(districts);
    }

    @Override
    public District saveDistrict(District district) {
        return reposDistrict.save(district); 
    }  

    @Override
    public Company findByCompanyId(int companyId) {
        return reposCompany.findOne(companyId);
    }
    
    @Override
    public Integer findByMaxCompanyId() {
        return reposCompany.findByMaxCompanyId();
    }

    @Override
    public Company saveCompany(Company company) {
        return reposCompany.save(company);
    }   

    @Override
    public List<ExpensesType> findAllExpensesType(Company company) {
        return reposExpensesType.findAllByCompany(company,new Sort(Sort.Direction.ASC, "name")); 
    }
    
    @Override
    public List<ExpensesType> findAllExpensesType(Company company ,String incexptype) {
        return reposExpensesType.findAllByCompanyAndType(company,incexptype,new Sort(Sort.Direction.ASC, "name")); 
    }
    
 
}