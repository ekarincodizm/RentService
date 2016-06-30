/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.admin;

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.Company;
import com.sabuymlm.model.admin.Country;
import com.sabuymlm.model.admin.District;
import com.sabuymlm.model.admin.Province;
import com.sabuymlm.service.CommonService;
import com.sabuymlm.utils.FileEntry;
import com.sabuymlm.vm.CommonAddVM;
import com.sabuymlm.vm.CommonVM; 
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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author MY-TENG
 */
@VariableResolver(DelegatingVariableResolver.class)
public class CompanyVM extends CommonAddVM<Company> implements Serializable {

    @WireVariable
    private CommonService commonService;
    private List<Country> countrys = new ArrayList<Country>();
    private final List<Province> provinces = new ArrayList<Province>();
    private final List<District> districts = new ArrayList<District>();
    private BufferedImage image = null;
    private String fileName;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) Company item, @ExecutionArgParam("icon") String icon, @ExecutionArgParam("headerLabel") String headerLabel) {
        item = commonService.findByCompanyId(SecurityUtil.getUserDetails().getUser().getCompanyId());
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
        item = commonService.findByCompanyId(item.getId());
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
        item = new Company();
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
    @Command
    public void onSave(@ContextParam(ContextType.VIEW) final Component view) throws InterruptedException {
        Messagebox.show("ต้องการบันทึกข้อมูล หรือไม่?", "ยืนยัน", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {

            @Override
            public void onEvent(Event event) throws Exception {
                if (((Integer) event.getData()).intValue() == Messagebox.YES) {
                    saveItem();
                }
            }
        });
    }

    @Override
    protected void saveItem() {
        if (image instanceof BufferedImage) { 
            FileEntry entryfile = new FileEntry(item.getUploadPath());
            if (entryfile.lsEntry.isEmpty()) {
                throw new RuntimeException("ยังไม่ได้ กำหนดที่เก็บไฟล์ กรุณาแจ้งเจ้าหน้าที่ผู้ดูแลระบบ!");
            } else { 
                (new File(item.getLogoPath())).mkdirs(); 
                String newFileName =  FileEntry.getNewName(FileEntry.LOGO_FILE_NAME + SecurityUtil.getUserDetails().getUser().getCompanyId(), fileName);
                InputStream is = FileEntry.convertToInputStream(image,newFileName); 
                try {
                    Files.copy(new File(item.getLogoPath(), newFileName ), is );
                } catch (IOException ex) {
                    Logger.getLogger(CompanyVM.class.getName()).log(Level.SEVERE, null, ex);
                }
                item.setLogo(newFileName); 
            }
        }
        if(item.getId() != null ){
            item.setUpdateDate(new Date()); 
            item.setUpdateUser(SecurityUtil.getUserDetails().getUser());  
        }
        SecurityUtil.getUserDetails().setCompany(commonService.saveCompany(item));
    }

    @Override
    protected boolean validate() { 
        return true ;
    }
}
