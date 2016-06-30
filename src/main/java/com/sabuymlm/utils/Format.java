/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sumrit
 */
public class Format {

    private static final Pattern rfc2822 = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

    public static String formatNumber(String pattern, Integer number) {
        DecimalFormat nf = new DecimalFormat(pattern);
        return nf.format(number == null ? 0 : number);
    }
    
    public static String formatNumber(String pattern, Number number) {
        DecimalFormat nf = new DecimalFormat(pattern);
        return nf.format(number == null ? 0 : number);
    }

    public static String formatNumber(String pattern, Double number) {
        DecimalFormat nf = new DecimalFormat(pattern);
        return nf.format(number == null ? 0 : number);
    }

    public static String formatNumber(String pattern, BigDecimal number) {
        DecimalFormat nf = new DecimalFormat(pattern);
        return nf.format(number == null ? 0 : number);
    }

    public static String formatNumber(String pattern, Long number) {
        DecimalFormat nf = new DecimalFormat(pattern);
        return nf.format(number == null ? 0 : number);
    }

    public static String formatNumber(String pattern, Float number) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number == null ? 0 : number);
    }

    public static String formatDateThai(String pattern, Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern, new Locale("th", "TH"));
        return df.format(date);
    }
    
    public static String formatDateThai(String pattern , Date date, Locale locale){
        if(date == null) {
            return "" ;
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern ,locale);
	return df.format(date);
    }
    
    public static String formatDateLocale(String pattern , Date date, Locale locale){
        if(date == null) {
            return "" ;
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern ,locale);
	return df.format(date);
    }

    public static String formatDateEn(String pattern, Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return df.format(date);
    }

    public static boolean isExpRegex(String format, String numer) {
        return (numer == null ? "" : numer).matches(format);
    }

    private static String wordUnit(char number, int lengthPosition, int maxlength, char digitBefore) {
        String word = "";
        switch (number) {
            case '0':
                word = "ศุนย์";
                break;
            case '1':
                word = ((lengthPosition == 1 && maxlength > 1 && digitBefore != '0') ? "เอ็ด" : (lengthPosition == 2 ? "" : "หนึ่ง"));
                break;
            case '2':
                word = (lengthPosition == 2 ? "ยี่" : "สอง");
                break;
            case '3':
                word = "สาม";
                break;
            case '4':
                word = "สี่";
                break;
            case '5':
                word = "ห้า";
                break;
            case '6':
                word = "หก";
                break;
            case '7':
                word = "เจ็ด";
                break;
            case '8':
                word = "แปด";
                break;
            case '9':
                word = "เก้า";
                break;
        }
        if (lengthPosition >= 2) {
            switch (lengthPosition) {
                case 2:
                    word += "สิบ";
                    break;
                case 3:
                    word += "ร้อย";
                    break;
                case 4:
                    word += "พัน";
                    break;
                case 5:
                    word += "หมื่น";
                    break;
                case 6:
                    word += "แสน";
                    break;
            }
        }
        return word;
    }

    private static String convertToTextMessage(String subString, int index) {
        String strWord = "";
        char word, wordOld = '0';
        int length = subString.length();
        while (length > 0) {
            word = subString.charAt(subString.length() - length);
            if (word != '0') {
                strWord += wordUnit(word, length, subString.length(), wordOld);
            } else if (subString.length() == 1) {
                strWord = wordUnit(word, length, subString.length(), wordOld);
            }
            wordOld = word;
            length--;
        }
        return strWord + (index < 0 ? "สตางค์" : (index == 0 ? "บาท" : "ล้าน"));
    }

    private static String thaiBath(String numberFormat) {

        String ret_st = "";
        boolean isNet = (numberFormat.lastIndexOf(".00") != -1);
        int idxPoint = numberFormat.indexOf(".");
        if (!isNet) {
            ret_st = convertToTextMessage(numberFormat.substring(idxPoint + 1, numberFormat.length()), -1);
        } else {
            ret_st = "ถ้วน";
        }
        numberFormat = numberFormat.substring(0, idxPoint);
        int indexSub = 0;
        while (numberFormat.length() > 0) {
            int length = numberFormat.length();
            if (length <= 6) {
                ret_st = (convertToTextMessage(numberFormat.substring(0, length), indexSub) + ret_st);
                numberFormat = "";
            } else {
                ret_st = (convertToTextMessage(numberFormat.substring(length - 6, length), indexSub) + ret_st);
                numberFormat = numberFormat.substring(0, length - 6);
            }

            indexSub++;
        }

        return ret_st;
    }

    public static String formatThaiBath(Integer number) {
        return thaiBath(formatNumber("######0.00", number));
    }

    public static String formatThaiBath(Double number) {
        return thaiBath(formatNumber("######0.00", number));
    }

    public static String formatThaiBath(BigDecimal number) {
        return thaiBath(formatNumber("######0.00", number));
    }

    public static String formatThaiBath(Long number) {
        return thaiBath(formatNumber("######0.00", number));
    }

    public static String formatThaiBath(Float number) {
        return thaiBath(formatNumber("######0.00", number));
    }

    public static boolean isPhoneReplace08NumberValid(String phoneNumber) {
        boolean isValid = false;
        if (phoneNumber == null) {
            return isValid;
        }
        phoneNumber = phoneNumber.replaceAll("-", "").replaceAll(" ", "").trim();
        if (phoneNumber.length() > 2) {
            if (phoneNumber.substring(0, 2).equals("08")) {
                phoneNumber = "668" + phoneNumber.substring(2);
            } else {
                return isValid;
            }
        }
        //Initialize reg ex for phone number.
        String expression = "\\d{11}$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        if (phoneNumber == null) {
            return isValid;
        }
        phoneNumber = phoneNumber.replaceAll("-", "").replaceAll(" ", "").trim();

        //Initialize reg ex for phone number.
        String expression = "\\d{10}$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static String isPhoneReplace08SendSms(String phoneNumber) {
        if (phoneNumber == null) {
            return "";
        }
        phoneNumber = phoneNumber.replaceAll("-", "").replaceAll(" ", "").trim();
        if (phoneNumber.length() > 2) {
            if (phoneNumber.substring(0, 2).equals("08")) {
                phoneNumber = "668" + phoneNumber.substring(2);
            }
        }
        return phoneNumber;
    }

    public static String isPhoneSendSms(String phoneNumber) {
        if (phoneNumber == null) {
            return "";
        }
        return phoneNumber.replaceAll("-", "").replaceAll(" ", "").trim();
    }

    public static boolean validateName(String name, String expression) {

        CharSequence inputStr = name;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();

    }

    public static boolean isValideIdCard(CharSequence id) {


        //ตรวจว่าป้อนถูกตามรูปแบบที่กำหนดมั้ย x-xxxx-xxxxx-xx-x
        String expression = "^d{1}d{1,4}d{1,5}d{1,2}d{1}$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(id);
        if (matcher.matches()) {
            return false;
        }

        //ตัด - เอาแต่เลขมาตรวจ
        id = id.toString().replace(" ", "");
        //ตรวจว่ามี 13 หลักถูกมั้ย
        if (id.length() != 13) {
            return false;
        }
        //เลขนำหน้ามีได้แค่ 1-8 
        if (Integer.parseInt(id.charAt(0) + "") < 1 || Integer.parseInt(id.charAt(0) + "") > 8) {
            return false;
        }

        //คำนวณหลักสุดท้าย
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Integer.parseInt(id.charAt(i) + "") * (13 - i);
        }
        sum = sum % 11;
        if (sum <= 1) {
            sum = 1 - sum;
        } else {
            sum = 11 - sum;
        }
        return (sum == Integer.parseInt(id.charAt(12) + ""));
    }

    public static String getIdCardLastDigit(String idcard, int lastdigit) {
        if (lastdigit >= 0 && idcard.length() >= 4) {
            return idcard.substring( idcard.length() - lastdigit , idcard.length());
        } else {
            return "";
        }
    }

    public static String accFormat(String accNo) {
        if (accNo == null) {
            return "";
        } else {
            accNo = accNo.replaceAll("-", "");
            if (accNo.trim().length() == 0) {
                return "";
            } else if (accNo.trim().length() > 10) {
                return accNo.trim();
            } else {
                accNo = ("0000000000" + accNo);
                accNo = accNo.substring(accNo.length() - 10);
                return accNo;
            }
        }
    }

    public static String isEmail(String email) {
        if (email == null) {
            return "";
        } else if (!rfc2822.matcher(email).matches()) {
            return "";
        } else {
            return email;
        }
    }

    public static boolean isValidateEmail(String email) {
        if (email == null) {
            return false;
        }
        return rfc2822.matcher(email).matches();
    }

    public static boolean isValidSHA1(String s) {
        return s.matches("[a-fA-F0-9]{40}");
    }

    public static boolean isValidMD5(String s) {
        return s.matches("[a-fA-F0-9]{32}");
    }
}
