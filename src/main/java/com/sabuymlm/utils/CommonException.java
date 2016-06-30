/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.utils;

/**
 *
 * @author sumrit
 */
public class CommonException extends Exception {
    private final String REF_CONSTRIENT_DELETE = "DELETE statement conflicted with the REFERENCE constraint";
    private final String REF_CONSTRIENT_DETETE_THAI = "ไม่สามารถลบข้อมูลได้\r\n เนื่องจาก ข้อมูลที่ต้องการลบถูกนำไปใช้งานที่ตารางอื่นๆ \r\n\r\n";
    private final String REF_CONSTRIENT_INSERT = "Violation of PRIMARY KEY constraint";
    private final String REF_CONSTRIENT_INSERT_THAI = "ไม่สามารถเพิ่มข้อมูลได้\r\n เนื่องจาก ข้อมูลที่ต้องการเพิ่มมีรหัสอ้างอิงซ้ำ(Pimary key) \r\n\r\n";
    private final String REF_CONSTRIENT_LOGIN = "Bad credentials";
    private final String REF_CONSTRIENT_LOGIN_THAI = "ไม่สามารถล็อกอินได้ \r\n Username/Password ไม่ถูกต้อง \r\n\r\n";
    private final String REF_CONSTRIENT_STRING_TRUNCATED = "String or binary data would be truncated";
    private final String REF_CONSTRIENT_STRING_TRUNCATED_THAI = "ข้อมูลบางตัวมีความยาวมากกว่าโปรแกรมกำหนดไว้ \r\n กรุณาลดจำนวนอักษร \r\n\r\n";
    private String message = null;
    public CommonException(){ 
    }
     
    public CommonException(String msg){
        super(msg);
        if(msg == null) {
            this.message = "null pointer message" ;
        }else if(msg.contains(REF_CONSTRIENT_DELETE)){
            this.message = REF_CONSTRIENT_DETETE_THAI + msg ;
        }else if(msg.contains(REF_CONSTRIENT_INSERT)){
            this.message = REF_CONSTRIENT_INSERT_THAI + msg ;
        }else if(msg.contains(REF_CONSTRIENT_LOGIN)){
            this.message = REF_CONSTRIENT_LOGIN_THAI + msg ;
        }else if(msg.contains(REF_CONSTRIENT_STRING_TRUNCATED)){
            this.message = REF_CONSTRIENT_STRING_TRUNCATED_THAI + msg ;
        }else {
            this.message = msg ;
        } 
    } 
    
    @Override
    public String toString(){
        return message ;
    }
    
    @Override
    public String getMessage(){
        return message ;
    }
}
