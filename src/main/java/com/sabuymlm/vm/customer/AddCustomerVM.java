/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.customer;

import com.sabuymlm.authen.SecurityUtil; 
import com.sabuymlm.model.admin.Country;
import com.sabuymlm.model.admin.District;
import com.sabuymlm.model.admin.Province;
import com.sabuymlm.model.customer.Customer;
import com.sabuymlm.service.CommonService;
import com.sabuymlm.service.CustomerService;
import com.sabuymlm.utils.FileEntry;
import com.sabuymlm.vm.CommonAddVM;
import com.sabuymlm.vm.CommonVM;
import com.sabuymlm.vm.admin.CompanyVM;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component; 
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver; 

/**
 *
 * @author MY-TENG
 */
@VariableResolver(DelegatingVariableResolver.class)
public class AddCustomerVM extends CommonAddVM<Customer> implements Serializable {

    @WireVariable
    private CustomerService customerService;
    @WireVariable
    private CommonService commonService;
    private List<Country> countrys = new ArrayList<Country>();
    private final List<Province> provinces = new ArrayList<Province>();
    private final List<District> districts = new ArrayList<District>();
    private BufferedImage image = null;
    private String fileName;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) Customer item, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {
        initial(item, icon, headerLabel);
        countrys = commonService.findAllCountry();
    }

    @Command
    @NotifyChange({"provinces", "item"})
    public void onSearchCountry() {
        provinces.clear();
        provinces.addAll(commonService.findAllProvince(item.getCountry()));
        item.setProvince(null);
        item.setDistrict(null);
        item.setPost(null);
    }

    @Command
    @NotifyChange({"districts", "item"})
    public void onSearchProvince() {
        districts.clear();
        districts.addAll(commonService.findAllDistrict(item.getProvince()));
        item.setDistrict(null);
        item.setPost(null);
    }
    
    @Command
    @NotifyChange({"item"})
    public void onSelectedDistrict() {  
        if(item.getDistrict() instanceof District ) { 
            item.setPost(item.getDistrict().getPost());
        }else {
            item.setPost(null);
        }
    }

    public List<District> getDistricts() {
        return districts;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public List<Country> getCountrys() {
        return countrys;
    }

    @Override
    protected void setEditItem() {
        item = customerService.findByCustomerId(item.getCustomerId());
        fileName = item.getLogo();
        if (item.getCountry() instanceof Country) {
            provinces.addAll(commonService.findAllProvince(item.getCountry()));
        }
        if (item.getProvince() instanceof Province) {
            districts.addAll(commonService.findAllDistrict(item.getProvince()));
        }
        if (fileName != null) {
            File file = new File(item.getLogoPath(), fileName);
            if (file.exists()) {
                try {
                    image = ImageIO.read(new FileInputStream(file));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CompanyVM.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CompanyVM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    protected void setNewItem() {
        item = new Customer();
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getFileName() {
        return fileName;
    }

    @Command("onUpload")
    @NotifyChange({"item", "fileName", "image"})
    public void upload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) throws IOException {
        UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
        if (validateUpdate(event.getMedia(), FileEntry.FILE_LOGO_MAX_SIZE)) {
            image = FileEntry.resizeImageByType(event.getMedia().getStreamData(), FileEntry.RESIZE_LOGO);
            fileName = event.getMedia().getName();
        }
    }

    private boolean validateUpdate(Media media, int maxSize) {
        if (media.getByteData().length > maxSize) {
            throw new RuntimeException("ขนาดไฟล์เกิน 1Mb.");
        } else if (media instanceof org.zkoss.image.AImage) {
            return true;
        } else {
            throw new RuntimeException("ประเภทไฟล์ที่ upload ไม่ใช่รูปภาพ");
        }
    }

    @Override
    protected void saveItem() {
        if (image instanceof BufferedImage) { 
            FileEntry entryfile = new FileEntry(SecurityUtil.getUserDetails().getCompany().getUploadPath());
            if (entryfile.lsEntry.isEmpty()) {
                throw new RuntimeException("ยังไม่ได้ กำหนดที่เก็บไฟล์ กรุณาแจ้งเจ้าหน้าที่ผู้ดูแลระบบ!");
            } else { 
                (new File(item.getLogoPath())).mkdirs(); 
                String newFileName =  FileEntry.getNewName(fileName + SecurityUtil.getUserDetails().getUser().getCompanyId(), fileName);
                InputStream is = FileEntry.convertToInputStream(image,newFileName); 
                try {
                    Files.copy(new File(item.getLogoPath(), newFileName ), is );
                } catch (IOException ex) {
                    Logger.getLogger(CompanyVM.class.getName()).log(Level.SEVERE, null, ex);
                }
                item.setLogo(newFileName); 
            }
        }
        if(item.getCustomerId() == null ){
            item.setCreateDate(new Date()); 
            item.setCreateUser(SecurityUtil.getUserDetails().getUser()); 
        }else {
            item.setUpdateDate(new Date()); 
            item.setUpdateUser(SecurityUtil.getUserDetails().getUser()); 
        }
        item.setCompany(SecurityUtil.getUserDetails().getCompany()); 
        customerService.saveCustomer(item);
    }

    @Override
    protected boolean validate() { 
        return true ;
    }
}
