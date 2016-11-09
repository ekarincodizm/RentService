/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm;
 
import com.sabuymlm.authen.SecurityUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import javax.activation.MimetypesFileTypeMap;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType; 
import org.zkoss.zk.ui.Component; 
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author MY-TENG
 * @param <T>
 */
public abstract class CommonAddVM<T> implements Serializable {

    private String lbStatus;
    private String lbStatusStyle;
    private String icon = "";
    private String headerLabel = "";
    protected T item;
    protected boolean pageHasDetail = true;
     
    public void initial(T item,String icon , String headerLabel) {
        this.item = item;  
        this.icon = icon;  
        this.headerLabel = headerLabel;  
        if (this.item != null ) { 
            setEditItem();
            setStatusEdit();
        } else { 
            setNewItem(); 
            setStatusAdd();
        }
    }
    
    protected abstract void setEditItem();
    protected abstract void setNewItem();
    protected abstract void saveItem();
    protected abstract boolean validate(); 

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    } 

    protected void setStatusAdd() {
        lbStatus = "เพิ่มข้อมูล";
        lbStatusStyle = "color:green;"; 
    }

    protected void setStatusEdit() {
        lbStatus = "แก้ไขข้อมูล";
        lbStatusStyle = "color:red;";
    }
    
    protected void setStatusVew() {
        lbStatus = "ดูรายละเอียด";
        lbStatusStyle = "color:blue;";
    }
    
    protected void setStatusComposeMail() {
        lbStatus = "Compose Mail";
        lbStatusStyle = "color:blue;";
    }

    public String getLbStatus() {
        return lbStatus;
    }

    public String getLbStatusStyle() {
        return lbStatusStyle;
    }

    public String getIcon() {
        return icon;
    }

    public String getHeaderLabel() {
        return headerLabel;
    }
    
    @Transactional
    @Command
    public void onSave(@ContextParam(ContextType.VIEW) final Component view) throws InterruptedException {
        if(validate()){
            Messagebox.show("ต้องการบันทึกข้อมูล หรือไม่?", "ยืนยัน", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {

                @Override
                public void onEvent(Event event) throws Exception {
                    if (((Integer) event.getData()) == Messagebox.YES) { 
                        saveItem();
                        if(pageHasDetail){
                            Events.sendEvent(CommonVM.RELOAD_EVENTS_NAME, view, null);
                            onClose(view);
                        }
                    }
                }
            });
        }
    } 
    
    @Command
    public void onClose(@ContextParam(ContextType.VIEW) Component view) {  
        ((Window) view).onClose();
    } 
    
    @Command
    public void onDownload(@BindingParam("fileName") String fileName) throws FileNotFoundException {
        if (fileName != null && fileName.trim().length() > 0) {
            File fileDownload = new File(fileName);
            if (fileDownload.exists()) {
                MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
                String mimeType = mimeTypesMap.getContentType(fileDownload);
                Filedownload.save(fileDownload, mimeType);
            } else {
                throw new RuntimeException("ไม่พบไฟล์ที่ต้องการ download หรือไฟล์ไม่ได้อยู่บน server");
            }
        }
    }
    
    public boolean isAdmin(){
        return (SecurityUtil.getUserDetails().getUser().getAdminLevel().equals("ADMIN"));
    }
    public boolean isUserAdmin(){
        return ((SecurityUtil.getUserDetails().getUser().getAdminLevel().equals("USER-ADMIN")) || isAdmin());
    }
    
}
