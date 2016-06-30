/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.utils.servlet;

import com.sabuymlm.authen.SecurityUtil; 
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*; 
import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload; 

public class SimpleUploadServlet extends HttpServlet {

    //
    private static final int MAX_WIDTH_IMAGE = 560; 

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String basePath = SecurityUtil.getUserDetails().getCompany().getWebPath();
        (new File(basePath)).mkdirs(); 
        File baseFile  = new File(basePath);  
        
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String urlPath = request.getContextPath() + "/ImageServletUtil?imagePath=" + basePath.replaceAll("\\\\", "//") + "/" ; 

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List items = upload.parseRequest(request);

            Map fields = new HashMap();

            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next(); 
                if (item.isFormField()) {
                    fields.put(item.getFieldName(), item.getString());
                } else {
                    fields.put(item.getFieldName(), item);
                }
            } 
            FileItem uplFile = (FileItem) fields.get("upload");
            String uploadFileName = uplFile.getName();
            File pathToSave = new File(baseFile, uploadFileName); 
            uplFile.write(pathToSave);

            BufferedImage img = ImageIO.read(pathToSave);
            if (img.getWidth() > MAX_WIDTH_IMAGE) {
                BufferedImage outFile = resize(pathToSave);
                ImageIO.write(outFile, "JPG", new File(baseFile, uploadFileName));
            } else {
                uplFile.write(pathToSave);
            }

            urlPath += uploadFileName;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        out.println("<script type=\"text/javascript\">");
        out.println(" function getUrlParam(paramName) ");
        out.println(" { ");
        out.println("     var reParam = new RegExp('(?:[\\?&]|&amp;)' + paramName + '=([^&]+)', 'i') ; ");
        out.println("     var match = window.location.search.match(reParam) ; ");
        out.println("     return (match && match.length > 1) ? match[1] : '' ; ");
        out.println(" } ");
        out.println(" var funcNum = getUrlParam('CKEditorFuncNum'); ");
        out.println(" window.parent.CKEDITOR.tools.callFunction(funcNum,  '" + urlPath + "'); ");
        out.println("</script>");
        out.flush();
        out.close();

    }

    private BufferedImage resize(File in) throws IOException {
        BufferedImage originalImage = ImageIO.read(in);
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        return resizeImage(originalImage, type, MAX_WIDTH_IMAGE, 460);

    }

    private BufferedImage resizeImage(BufferedImage image, int imageType, int newWidth, int newHeight) {

        double thumbRatio = (double) newWidth / (double) newHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double aspectRatio = (double) imageWidth / (double) imageHeight;
        if (thumbRatio < aspectRatio) {
            newHeight = (int) (newWidth / aspectRatio);
        } else {
            newWidth = (int) (newHeight * aspectRatio);
        }
        // Draw the scaled image        
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, imageType);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);
        return newImage;


    }
}
