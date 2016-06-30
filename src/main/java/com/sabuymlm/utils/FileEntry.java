package com.sabuymlm.utils;

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.vm.admin.CompanyVM;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Sumrit.p
 */
public class FileEntry {

    public List<Entry> lsEntry = new ArrayList<Entry>();
    private String path;
    public static final String ADMIN_FOLDER = "ADMIN";
    public static final String WEB_FOLDER = "WEB";
    public static final String ADMIN_LOGO_FOLDER = ADMIN_FOLDER + "/logo";
    public static final String ADMIN_BACKGROUND_FOLDER = ADMIN_FOLDER + "/background";
    public static final String ADMIN_CUSTOMER_FOLDER = ADMIN_FOLDER + "/customer";
    public static final String ADMIN_POSITION_FOLDER = ADMIN_FOLDER + "/position";
    public static final String ADMIN_PRODUCT_FOLDER = ADMIN_FOLDER + "/product";
    public static final String ADMIN_COUNTRY_FOLDER = ADMIN_FOLDER + "/country";
    public static final String ADMIN_ATTACHFILE_FOLDER = ADMIN_FOLDER + "/attach_file";
    public static final int FILE_LOGO_MAX_SIZE = 1000 * 1024; // 1M
    public static final int FILE_BACKGROUND_MAX_SIZE = 1000 * 1024; // 1M
    public static final int FILE_POSITION_MAX_SIZE = 1000 * 1024; // 1M
    public static final int FILE_PRODUCT_MAX_SIZE = 1000 * 1024; // 1M
    public static final int FILE_COUNTRY_MAX_SIZE = 500 * 1024; // 1M
    public static final int LOGO_IMG_WIDTH = 100;  // 100px
    public static final int LOGO_IMG_HEIGHT = 90;  // 90px 
    public static final int COUNTRY_IMG_WIDTH = 25;  // 25px
    public static final int COUNTRY_IMG_HEIGHT = 16;  // 25px 
    public static final int BACKG_IMG_WIDTH = 800;  // 800px
    public static final int BACKG_IMG_HEIGHT = 600;  // 600px  
    public static final int POSITION_IMG_WIDTH = 50;  // 50px
    public static final int POSITION_IMG_HEIGHT = 50;  // 50px  
    public static final int PRODUCT_IMG_WIDTH = 120;  // 120px
    public static final int PRODUCT_IMG_HEIGHT = 105;  // 105px  
    public static final int RESIZE_LOGO = 1;
    public static final int RESIZE_BACKGROUND = 2;
    public static final int RESIZE_POSITION = 3;
    public static final int RESIZE_PRODUCT = 4;
    public static final int RESIZE_COUNTRY = 5;
    public static final String LOGO_FILE_NAME = "logo";
    public static final String BACKGROUND_FILE_NAME = "background";

    public static File getFilePath(String path) {
        return new File(path);
    }

    public FileEntry(String path) {
        this.path = path;
        File dir = new File(path);
        String[] children = dir.list();
        if (children == null) {
        } else {
            for (int i = 0; i < children.length; i++) {
                String filename = children[i];
                Entry entry = new Entry();
                entry.setIsFolder(filename.indexOf(".") != -1);
                entry.setFileName(filename);
                entry.setFilePath(path + "//" + filename);
                lsEntry.add(entry);

            }
        }
    }

    public boolean createChildFolder() {
        return (new File(path + "/" + ADMIN_FOLDER)).mkdirs()
                && (new File(path + "/" + WEB_FOLDER)).mkdirs();
    }

    public void delete() {
        for (Entry entry : lsEntry) {
            (new File(path + "//" + entry.getFileName())).delete();
        }
        (new File(path)).delete();
    }

    public static boolean deleteAllFileInFolder(File file) {
        if (file.isDirectory()) {
            final File[] fls = file.listFiles();
            for (int j = 0; j < fls.length; ++j) {
                if (!fls[j].delete()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static BufferedImage resizeImageByType(InputStream input, int resizeType) throws IOException {
        BufferedImage originalImage = ImageIO.read(input);
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        int width = 0, height = 0;
        switch (resizeType) {
            case RESIZE_LOGO:
                width = LOGO_IMG_WIDTH;
                height = LOGO_IMG_HEIGHT;
                break;
            case RESIZE_BACKGROUND:
                width = BACKG_IMG_WIDTH;
                height = BACKG_IMG_HEIGHT;
                break;
            case RESIZE_POSITION:
                width = POSITION_IMG_WIDTH;
                height = POSITION_IMG_HEIGHT;
                break;
            case RESIZE_PRODUCT:
                width = PRODUCT_IMG_WIDTH;
                height = PRODUCT_IMG_HEIGHT;
                break;
            case RESIZE_COUNTRY:
                width = COUNTRY_IMG_WIDTH;
                height = COUNTRY_IMG_HEIGHT;
                break;
            default:
                width = originalImage.getWidth();
                height = originalImage.getHeight();
        }

        if (originalImage.getHeight() > width || originalImage.getWidth() > height) {
            return resizeImage(originalImage, type, width, height);
        } else {
            return resizeImage(originalImage, type, originalImage.getWidth(), originalImage.getHeight());
        }

    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public static InputStream convertToInputStream(BufferedImage image,String fileName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, fileName.substring(fileName.lastIndexOf('.') + 1), baos);
        } catch (IOException ex) {
            Logger.getLogger(CompanyVM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static String createRemarkText(int witdh, int height) {
        return " หาก size > " + Format.formatNumber("#,###", witdh) + "x" + Format.formatNumber("#,###", height)
                + " โปรแกรม resize " + Format.formatNumber("#,###", witdh) + "x" + Format.formatNumber("#,###", height) + " pixcel อัตโนมัติ";
    }

    public static String getNewName(String newName, String name) {
        return newName + name.substring(name.lastIndexOf('.'));
    }

    public static List<Entry> readEntryFilePath(String url) {

        List<Entry> lsEntry = new ArrayList<Entry>();

        url = url.replaceAll("&nbsp;", " ").trim();
        String path = "";
        int index = url.indexOf(" ");
        while (index != -1) {
            path = url.substring(0, index);
            Entry entry = new Entry();
            entry.setFilePath(path);
            entry.setFileName(path.substring(path.lastIndexOf("/") + 1));
            lsEntry.add(entry);
            url = url.substring(index + 1);
            while (url.indexOf(" ") == 0) {
                url = url.substring(1);
            }

            index = url.indexOf(" ");
        }
        if (url.length() > 2) {
            Entry entry = new Entry();
            entry.setFilePath(url);
            entry.setFileName(url.substring(url.lastIndexOf("/") + 1));
            lsEntry.add(entry);
        }

        return lsEntry;
    }

    public static void saveUploadFile(InputStream is,String filePath , String newFileName) throws RuntimeException {
        FileEntry entryfile = new FileEntry(SecurityUtil.getUserDetails().getCompany().getUploadPath());
        if (entryfile.lsEntry.isEmpty()) {
            throw new RuntimeException("ยังไม่ได้ กำหนดที่เก็บไฟล์ กรุณาแจ้งเจ้าหน้าที่ผู้ดูแลระบบ!");
        } else {
            (new File(filePath)).mkdirs();  
            try {
                org.zkoss.io.Files.copy(new File(filePath, newFileName ), is );
            } catch (IOException ex) {
                Logger.getLogger(CompanyVM.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
}
