/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.admin;

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.model.admin.Country; 
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
public class AddCountryVM extends CommonAddVM<Country> implements Serializable {

    @WireVariable
    private CommonService commonService;
    private BufferedImage image = null;
    private String fileName;
    
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam(CommonVM.PARAM_NAME_OBJECT) Country item
        , @ExecutionArgParam("icon") String icon
        , @ExecutionArgParam("headerLabel") String headerLabel ) { 
        initial(item,icon,headerLabel);  
    }

    @Override
    protected void setEditItem() {
        item = commonService.findByCountryId(item.getId()); 
        fileName = item.getIcon();
        if (fileName != null) {
            File file = new File(item.getCountryPath(), fileName);
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
        item = new Country(); 
    }

    @Override
    protected void saveItem() {
        if(image instanceof BufferedImage){
            (new File(item.getCountryPath())).mkdirs(); 
                String newFileName =  FileEntry.getNewName(fileName + SecurityUtil.getUserDetails().getUser().getCompanyId(), fileName);
                InputStream is = FileEntry.convertToInputStream(image,newFileName);
                try {
                    Files.copy(new File(item.getCountryPath(), newFileName ), is );
                } catch (IOException ex) {
                    Logger.getLogger(CompanyVM.class.getName()).log(Level.SEVERE, null, ex);
                }
                item.setIcon(newFileName); 
        }
        commonService.saveCountry(item); 
    }
    
    @Command("onUpload")
    @NotifyChange({"item", "fileName", "image"})
    public void upload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) throws IOException {
        UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
        if (validateUpdate(event.getMedia(), FileEntry.FILE_COUNTRY_MAX_SIZE)) {
            image = FileEntry.resizeImageByType(event.getMedia().getStreamData(), FileEntry.RESIZE_COUNTRY);
            fileName = event.getMedia().getName();
        }
    }

    private boolean validateUpdate(Media media, int maxSize) {
        if (media.getByteData().length > maxSize) {
            throw new RuntimeException("ขนาดไฟล์เกิน 500b.");
        } else if (media instanceof org.zkoss.image.AImage) {
            return true;
        } else {
            throw new RuntimeException("ประเภทไฟล์ที่ upload ไม่ใช่รูปภาพ");
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    protected boolean validate() {
        return true ;
    }
    
}
