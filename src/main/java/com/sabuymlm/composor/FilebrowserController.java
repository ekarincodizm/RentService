/* FilebrowserController.java

 {{IS_NOTE
 Purpose:
 
 Description:
 
 History:
 Thu Nov 18 14:07:45     2010, Created by Jimmy Shiau
 }}IS_NOTE

 Copyright (C) 2009 Potix Corporation. All Rights Reserved.

 {{IS_RIGHT
 }}IS_RIGHT
 */
package com.sabuymlm.composor;

/**
 *
 * @author Jimmy Shiau
 */
import com.sabuymlm.authen.SecurityUtil; 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.zkoss.image.AImage;

import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Div;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
//import th.co.geniustree.dcg.servlet.SimpleUploadServlet;

public class FilebrowserController extends GenericForwardComposer {

    private static final String[] EXCLUDE_FOLDERS = {"WEB-INF", "META-INF"};
    private static final String[] EXCLUDE_FILES = {};
    private static final String[] IMAGES = {"gif", "jpg", "jpeg", "png"};
    private static final String[] FILES = {"htm", "html", "php", "php3",
        "php5", "phtml", "asp", "aspx",
        "ascx", "jsp", "cfm", "cfc", "pl",
        "bat", "exe", "dll", "reg", "cgi", "asmx"};
    private static final String[] FLASH = {"swf"};
    private static final String[] MEDIA = {"swf", "fla", "jpg", "gif", "jpeg", "png", "avi", "mpg", "mpeg"};
    private String type = "";
    private Map fileFilterMap;
    private Tree tree;
    private Div cntDiv;
    private Toolbarbutton selBtn;
    private File baseFile;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        (new File( SecurityUtil.getUserDetails().getCompany().getWebPath())).mkdirs();
        baseFile = new File(SecurityUtil.getUserDetails().getCompany().getWebPath());

        type = ((String[]) param.get("Type"))[0];
        fileFilterMap = initFileFilterMap();

        if ( baseFile.listFiles().length == 0) {
            throw new UiException("Folder not found: " + baseFile.getAbsolutePath() );
        }

        Map rootFolderMap = new TreeMap();
        Map map = new TreeMap();
        rootFolderMap.put(baseFile.getAbsolutePath(), map);
        parseFolders(baseFile.getAbsolutePath(), map);

        tree.setItemRenderer(new ExplorerTreeitemRenderer());
        tree.setModel(new DefaultTreeModel(new DefaultTreeNode("ROOT", initTreeModel(rootFolderMap, new ArrayList()))));

        showImages(map);
    } 
    
    private List initTreeModel(Map parentFolderMap, List list) {
        for (Iterator it = parentFolderMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            Object value = entry.getValue();

            if (value instanceof Map) {
                Map map = (Map) value;
                if (map.size() == 0) {
                    continue;
                }
                ArrayList al = new ArrayList();
                list.add(new DefaultTreeNode(entry, al));
                initTreeModel(map, al);
            }
        }
        return list;
    }

    private Map parseFolders(String path, Map parentFolderMap) throws IOException {

        File uploadDir = new File(path);
        String[] list = uploadDir.list();
        Arrays.asList(list).iterator();
        Iterator it = Arrays.asList(list).iterator();
        while (it.hasNext()) {
            String pagePath = String.valueOf(it.next());
            if (pagePath.endsWith("/")) {
                String folderName = pagePath.substring(0, pagePath.length() - 1);
                folderName = folderName.substring(folderName.lastIndexOf("/") + 1);
                if (shallShowFile(folderName)) {
                    parentFolderMap.put(folderName, parseFolders(pagePath, new TreeMap()));
                }

            } else {
                String fileName = pagePath.substring(pagePath.lastIndexOf("/") + 1);
                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (shallShowFile(fileName) || shallShowFile(extension)) {
                    parentFolderMap.put(fileName, pagePath);
                }
            }
        }
        return parentFolderMap;
    }

    private boolean shallShowFile(String folderName) {
        return new Boolean((String) fileFilterMap.get(folderName)).booleanValue();
    }

    private Map initFileFilterMap() {
        Map fileFilterMap = new HashMap();

        for (int i = 0, j = EXCLUDE_FOLDERS.length; i < j; i++) {
            fileFilterMap.put(EXCLUDE_FOLDERS[i], "false");
        }
        for (int i = 0, j = EXCLUDE_FILES.length; i < j; i++) {
            fileFilterMap.put(EXCLUDE_FILES[i], "false");
        }

        if (type.equals("Flash")) {
            for (int i = 0, j = FLASH.length; i < j; i++) {
                fileFilterMap.put(FLASH[i], "true");
            }
        } else if (type.equals("Images")) {
            for (int i = 0, j = IMAGES.length; i < j; i++) {
                fileFilterMap.put(IMAGES[i], "true");
            }
        } else if (type.equals("Files")) {
            for (int i = 0, j = FLASH.length; i < j; i++) {
                fileFilterMap.put(FLASH[i], "true");
            }
            for (int i = 0, j = IMAGES.length; i < j; i++) {
                fileFilterMap.put(IMAGES[i], "true");
            }
            for (int i = 0, j = FILES.length; i < j; i++) {
                fileFilterMap.put(FILES[i], "true");
            }
        }

        return fileFilterMap;
    }

    public void onSelect$tree() throws IOException {
        cntDiv.getChildren().clear();
        Treeitem item = tree.getSelectedItem();
        Map map = (Map) item.getValue();

        showImages(map);
    }

    private void showImages(Map map) throws IOException {
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry me = (Map.Entry) it.next();
            Object value = me.getValue();
            if (value instanceof Map) {
                continue;
            }
            String path = String.valueOf(value);
            String swfPath = "";
            if (path.endsWith("swf")) {
                swfPath = "~./ckez/img/flashIcon.jpg";
            }

            Toolbarbutton tb = new Toolbarbutton(String.valueOf(me.getKey()));
            tb.setImageContent(new AImage(baseFile.getAbsolutePath() + "/" + String.valueOf(me.getKey())));
            tb.addEventListener("onClick", new EventListener() {

                @Override
                public void onEvent(Event event) throws Exception {
                    if (selBtn != null) {
                        selBtn.setSclass(null);
                    }
                    selBtn = (Toolbarbutton) event.getTarget();
                    selBtn.setSclass("sel");
                }
            });
            int CKEditorFuncNum = 1;
            CKEditorFuncNum = new Integer(((String[]) param.get("CKEditorFuncNum"))[0]).intValue();
            String script = "window.opener.CKEDITOR.tools.callFunction("
                    + CKEditorFuncNum + ",  '" + execution.getContextPath() + "/ImageServletUtil?imagePath=" + execution.encodeURL(baseFile.getAbsolutePath() +"/"+ path) + "'); window.close(); ";
            tb.setWidgetListener("onDoubleClick", script);

            cntDiv.appendChild(tb);
        }

    }

    private class ExplorerTreeitemRenderer implements TreeitemRenderer {

        @Override
        public void render(Treeitem item, Object data, int i) throws Exception {
            Map.Entry entry = (Map.Entry) ((DefaultTreeNode) data).getData();
            item.setLabel(String.valueOf(entry.getKey()));
            Object value = entry.getValue();
            item.setValue(value);
            item.setOpen(true);
            if (item.getParentItem() == null) {
                item.setSelected(true);
            }
        }
    }
}
