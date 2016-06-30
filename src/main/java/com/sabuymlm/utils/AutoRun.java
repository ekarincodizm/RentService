/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabuymlm.utils;
 
import java.text.DecimalFormat; 

/**
 *
 * @author sumrit
 */
public class AutoRun {
    private static DecimalFormat _f = new DecimalFormat();
    public static String genNextCode(String formatNumber ,long inputCode){
        _f.applyPattern(formatNumber); 
        inputCode++;
        return _f.format(inputCode);
    } 
}
