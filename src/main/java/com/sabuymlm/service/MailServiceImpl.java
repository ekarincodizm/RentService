/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;

import java.io.File; 
import java.net.URLEncoder;
import java.util.List;
import javax.mail.internet.MimeMessage; 
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper; 
import org.springframework.stereotype.Service;
 
/**
 *
 * @author ITeng
 */
@Service(value = "mailServices" )
@Repository
@Transactional
public class MailServiceImpl implements MailService{

    private JavaMailSenderImpl sender ;  
    private MimeMessage mimeMsg ;
    
    @Override
    public void sendMail(String to , String subject , String message )  throws Exception {
        sendMail(to , subject , message , false , null );
    }
    
    @Override
    public void sendMail(String[] tos , String subject , String message )  throws Exception {
        sendMail(tos , subject , message , false , null );
    }
    
    @Override
    public void sendMail(String to , String subject , String message , boolean isMultipart, List<File> files )  throws Exception {
        String[] ts = new String[1] ;
        ts[0] = to ;
        sendMail(ts , subject ,  message , isMultipart, files ) ;
    }
    
    @Override
    public void sendMail(String[] tos , String subject , String message , boolean isMultipart, List<File> files )  throws Exception {
        
        mimeMsg = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, isMultipart);
        helper.setTo( tos );
        helper.setSubject(subject); 
        helper.setText(message);
        if(files != null && !files.isEmpty()){
            for(File f : files){ 
                helper.addAttachment(URLEncoder.encode(f.getName(),"UTF-8"), f);   
            }
        }
        
        sender.send(mimeMsg); 
        
    }


    @Override
    public void createMailSender(String host , int port, String protocol , String username , String password , String encoding) throws Exception {
        
        sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setProtocol(protocol);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setDefaultEncoding(encoding);
        sender.getJavaMailProperties().setProperty("mail.smtps.auth", "true"); 
        sender.getJavaMailProperties().setProperty("mail.smtps.starttls.enable", "true"); 
        sender.getJavaMailProperties().setProperty("mail.smtps.debug", "true"); 
 

    } 
    
    @Override
    public void createMailSender() throws Exception { 
        createMailSender( "smtp.gmail.com" ,  465,  "smtps" ,  "sumrit@geniustree.co.th" ,  "passwordsumrit" ,  "tis-620");  
    } 

    @Override
    public void sendAutoMail(String to, String name, String username, String password, String autoString) throws Exception {
        StringBuilder html = new StringBuilder();
        html.append("เรียน คุณ").append( name ).append("\r\n");
        html.append("\r\n ");
        html.append("แจ้งการสมัครสมาชิกใหม่ เรียบร้อยแล้ว \r\n");
        html.append("\r\n ");
        html.append(" username = ").append(username).append("   , "); 
        html.append(" password = ").append(password).append("\r\n"); 
        html.append("\r\n ");
        html.append("\r\n ");
        html.append("(ขอบคุณครับ)");
        html.append("\r\nwww.sabuymlm.com");
        html.append("\r\n086-550-2292");
        sendMail(to , autoString , html.toString() , false , null );
    }
    
    @Override
    public void sendResetPasswordMail(String to, String name, String username, String password, String autoString) throws Exception {
        StringBuilder html = new StringBuilder();
        html.append("เรียน คุณ").append( name ).append("\r\n");
        html.append("\r\n ");
        html.append("ระบบทำการ reset password เรียบร้อยแล้ว \r\n");
        html.append("\r\n ");
        html.append(" username = ").append(username).append("   , "); 
        html.append(" password = ").append(password).append("\r\n"); 
        html.append("\r\n ");
        html.append("\r\n ");
        html.append("(ขอบคุณครับ)");
        html.append("\r\nwww.sabuymlm.com");
        html.append("\r\n086-550-2292");
        sendMail(to , autoString , html.toString() , false , null );
    }
    
}
