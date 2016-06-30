/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service;

import java.io.File; 
import java.util.List; 

/**
 *
 * @author ITeng
 */
public interface MailService {
    public void createMailSender(String host , int port, String protocol , String username , String password , String encoding)  throws Exception ;
    public void createMailSender() throws Exception ;
    public void sendMail(String to , String subject , String message )  throws Exception  ; 
    public void sendAutoMail(String to , String customerCode, String customerName , String password , String autoString )  throws Exception  ; 
    public void sendResetPasswordMail(String to , String customerCode, String customerName , String password , String autoString )  throws Exception  ; 
    public void sendMail(String [] tos , String subject , String message )  throws Exception  ; 
    public void sendMail(String to , String subject , String message , boolean isMultipart, List<File> files )  throws Exception ;
    public void sendMail(String [] tos , String subject , String message , boolean isMultipart, List<File> files )  throws Exception ;  
}
