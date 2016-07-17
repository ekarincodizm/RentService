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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.activation.MimetypesFileTypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author MY-TENG
 * @param <T>
 */
public abstract class CommonVM<T> implements Serializable {

    public static final String PARAM_NAME_OBJECT = "object";
    public static final String RELOAD_EVENTS_NAME = "onReload";
    public static final String ALL_STATUS = "ALL"; 
    private final int pageSize = 10;
    private int activePage;
    protected String keyword = "";
//    private String fileName = "";
//    private InputStream inputStreamFile = null;
    private String createPageName = "";
    private String defaultOrderName = "";
    private String icon = "";
    private String headerLabel = "";
    protected Sort.Order order = null;

    protected Page<T> items;
    protected List<T> selectItems = new ArrayList<T>();
    protected T selectItem;

    private void setOrderDefault() {
        order = new Sort.Order(Sort.Direction.ASC, defaultOrderName);
    }

    protected void initialVM(String createPageName, String defaultOrderName, String icon, String headerLabel) {
        this.createPageName = createPageName;
        this.defaultOrderName = defaultOrderName;
        this.icon = icon;
        this.headerLabel = headerLabel;
        setOrderDefault();
    }

    @Command
    @NotifyChange({"items"})
    public void onOrderBy(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
        onSort((Listheader) ctx.getTriggerEvent().getTarget());
        if (order == null) {
            setOrderDefault();
        }
    }

    protected void onSort(Listheader lh) {
        final String sortDirection = lh.getSortDirection();
        order = null;
        if ("ascending".equals(sortDirection)) {
            Comparator cmpr = lh.getSortDescending();
            if (cmpr instanceof FieldComparator) {
                String orderBy = ((FieldComparator) cmpr).getRawOrderBy();
                order = new Sort.Order(Sort.Direction.ASC, orderBy);
                search();
            }
        } else if ("descending".equals(sortDirection) || "natural".equals(sortDirection)) {
            Comparator cmpr = lh.getSortAscending();
            if (cmpr instanceof FieldComparator) {
                String orderBy = ((FieldComparator) cmpr).getRawOrderBy();
                order = new Sort.Order(Sort.Direction.DESC, orderBy);
                search();
            }
        }
    }

    @Command
    public void onClickAdd() {
        openWindowPopup(new HashMap());
    }

    @Command
    public void onClickEdit(@BindingParam(PARAM_NAME_OBJECT) Object o) {
        Map map = new HashMap();
        map.put(PARAM_NAME_OBJECT, o);
        openWindowPopup(map);
    }

    @Command
    public void onClickDelete() throws InterruptedException {
        if (selectItems.isEmpty()) {
            Messagebox.show("ไม่ได้เลือกรายการที่ต้องการลบ", "คำเตือน", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        Messagebox.show("ต้องการลบข้อมูลที่เลือก หรือไม่?", "คำยืนยัน", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {

            @Override
            public void onEvent(Event event) throws Exception {
                if (((Integer) event.getData())  == Messagebox.YES) {
                    deleteSelected();
                }
            }
        });
    }

    @Command
    public void onDeletePopup() {
        if (selectItem != null) {
            Messagebox.show("ต้องการลบข้อมูล หรือไม่?", "คำยืนยัน", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    if (((Integer) event.getData()) == Messagebox.YES) {
                        selectItems.clear();
                        selectItems.add(selectItem);
                        deleteSelected();
                    }
                }
            });
        }
    }

    protected void clearPaging() {
        activePage = 0;
    }

    @Command
    @NotifyChange({"items", "selectItems","sumBean"})
    public void search() {
        clearSelected();
        load();
    }

    public abstract void load();

    public abstract void deleteSelected();

    public void openWindowPopup(Map map) {
        map.put("icon", icon);
        map.put("headerLabel", headerLabel);
        Window window = (Window) Executions.createComponents(createPageName, null, map);
        window.setClosable(true);
        window.doHighlighted();
        window.addEventListener(RELOAD_EVENTS_NAME, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                search(); 
                BindUtils.postNotifyChange(null, null, CommonVM.this, ".");
            }
        });
    }

//    protected void clearUpload() {
//        search();
//        setFileName("");
//        setInputStreamFile(null);
//    }
//    protected void onClearData() {
//        clearUpload();
//        Messagebox.show("การปรับปรุงข้อมูลเรียบร้อย", "แจ้งสถานะ", Messagebox.OK, Messagebox.INFORMATION);
//    }
    public List<T> getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(List<T> selectItems) {
        this.selectItems = selectItems;
    }

    public T getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(T selectItem) {
        this.selectItem = selectItem;
    }

    public Page<T> getItems() {
        return items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getActivePage() {
        return activePage;
    }

    public String getKeyword() {
        return keyword.trim();
    }

    public void setActivePage(int activePage) {
        this.activePage = activePage;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getObjectName() {
        return PARAM_NAME_OBJECT;
    }

//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//    public InputStream getInputStreamFile() {
//        return inputStreamFile;
//    }
//
//    public void setInputStreamFile(InputStream inputStreamFile) {
//        this.inputStreamFile = inputStreamFile;
//    }
//    public void onUploadXlsFile(UploadEvent event) {
//        String type = null;
//        if (event.getMedia() != null) {
//            type = event.getMedia().getName().substring(event.getMedia().getName().lastIndexOf('.')).toUpperCase();
//        }
//        if (type == null) {
//            throw new RuntimeException(" การนำเข้าไฟล์ผิดพลาด กรุณาตรวจสอบไฟล์ ");
//        } else if (event.getMedia().getByteData().length > (1024 * 1000 * 20)) {
//            throw new RuntimeException(" ไฟล์ขนาดเกิน 20M ");
//        } else if (type.indexOf(".XLS") != -1) {
//            if (type.equals(".XLS")) {
//                fileName = (event.getMedia().getName());
//                inputStreamFile = event.getMedia().getStreamData();
//            } else {
//                throw new RuntimeException(" ยังไม่ support excel version 2007 หรือ มากกว่า version 2003 ");
//            }
//        } else {
//            throw new RuntimeException(" การนำเข้าประเภทไฟล์ไม่ถูกต้อง ");
//        }
//    }
    @Command
    public void onClose(@ContextParam(ContextType.VIEW) Component view) {
        ((Window) view).onClose();
    }

    @Command
    public void onPopupOpen(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
        OpenEvent openEvt = (OpenEvent) ctx.getTriggerEvent();
        if (openEvt.getReference() instanceof Listitem) {
            selectItem = (T) ((Listitem) openEvt.getReference()).getValue();
        }
    }

    public void clearSelected() {
        selectItems.clear();
        selectItem = null;
    }

    private void open() {
        Map map = new HashMap();
        map.put(PARAM_NAME_OBJECT, selectItem);
        openWindowPopup(map);
    }

    @Command
    public void onEditPopup() {
        if (selectItem != null) {
            open();
        }
    }

    @Command
    public void onDoubleEdit(@BindingParam("selectItem") T selectItem) {
        this.selectItem = selectItem;
        if (this.selectItem != null) {
            open();
        }
    }

    public String getIcon() {
        return icon;
    }

    public String getHeaderLabel() {
        return headerLabel;
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
    public void openReportWindowPopup(Map argMap) {
        Window window = (Window) Executions.createComponents("/secured/report/report.zul", null , argMap);
        window.setSizable(true);  
    } 
    
    public boolean isAdmin(){
        return (SecurityUtil.getUserDetails().getUser().getAdminLevel().equals("ADMIN"));
    }
    public boolean isUserAdmin(){
        return ((SecurityUtil.getUserDetails().getUser().getAdminLevel().equals("USER-ADMIN")) || isAdmin());
    }
}
