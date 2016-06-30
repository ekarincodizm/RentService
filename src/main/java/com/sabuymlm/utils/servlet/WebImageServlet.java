/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.utils.servlet;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

public class WebImageServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageName = request.getParameter("imagePath");  
        ServletOutputStream out = response.getOutputStream();
        try {
            File file = new File(imageName); 
            if (file.exists()) {
                Files.copy(new File(imageName), out);
            } else {
                throw new RuntimeException("ยังไม่ได้กำหนด ไฟล์รูปภาพโลโก้บริษัท!"); 
            }
        } catch (IOException ex) {
            Logger.getLogger(WebImageServlet.class.getName()).log(Level.SEVERE, null, "File not found.");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
