/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabuymlm.utils; 

/**
 *
 * @author sumrit
 */
public class Price {
    public static double getPriceNoVat(Double priceIncVat , Double vat){
        if(priceIncVat == null){
            return 0;
        }else {
            if (vat == null){
                vat = new Double(0.0) ;
            }
            double price = priceIncVat.doubleValue()*100/(100+vat.doubleValue());
            return price ;
        }
    }
    
    public static double getPriceVat(Double priceIncVat , Double vat){
        if(priceIncVat == null){
            return 0;
        }else {
            if (vat == null){
                vat = new Double(0.0) ;
            }
            double price = priceIncVat.doubleValue()*(100+vat.doubleValue())/100  ;
            return price ;
        }
    }
    
}
