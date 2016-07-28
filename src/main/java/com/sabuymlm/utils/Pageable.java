/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabuymlm.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List; 

/**
 *
 * @author MY-TENG
 */
public class Pageable<T> implements Serializable {
    private List<T> content ;
    private Integer totalElements ;
    private Number sum1;
    private Number sum2;
    private Number sum3;
    private Number sum4;
    private Number sum5;
    private Number sum6;
    private Number sum7;
    private Number sum8;
    private Number sum9;
    private Number sum10; 
    ///  page start index = 0
    private int pageIndex ; 
    private int pageSize ;

    public List<T> getContent() {
        if(content == null ) {
            content = new ArrayList<T>();
        }
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Integer getTotalElements() {
        if(totalElements == null ){
            totalElements = 0;
        }
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Number getSum1() {
        if(sum1 == null ) {
            sum1 = 0d;
        }
        return sum1;
    }

    public void setSum1(Number sum1) {
        this.sum1 = sum1;
    }

    public Number getSum2() {
        if(sum2 == null ) {
            sum2 = 0d;
        }
        return sum2;
    }

    public void setSum2(Number sum2) {
        this.sum2 = sum2;
    }

    public Number getSum3() {
        if(sum3 == null ) {
            sum3 = 0d;
        }
        return sum3;
    }

    public void setSum3(Number sum3) {
        this.sum3 = sum3;
    }

    public Number getSum4() {
        if(sum4 == null ) {
            sum4 = 0d;
        }
        return sum4;
    }

    public void setSum4(Number sum4) {
        this.sum4 = sum4;
    }

    public Number getSum5() {
        if(sum5 == null ) {
            sum5 = 0d;
        }
        return sum5;
    }

    public void setSum5(Number sum5) {
        this.sum5 = sum5;
    }

    public Number getSum6() {
        if(sum6 == null ) {
            sum6 = 0d;
        }
        return sum6;
    }

    public void setSum6(Number sum6) {
        this.sum6 = sum6;
    }

    public Number getSum7() {
        if(sum7 == null ) {
            sum7 = 0d;
        }
        return sum7;
    }

    public void setSum7(Number sum7) {
        this.sum7 = sum7;
    }

    public Number getSum8() {
        if(sum8 == null ) {
            sum8 = 0d;
        }
        return sum8;
    }

    public void setSum8(Number sum8) {
        this.sum8 = sum8;
    }

    public Number getSum9() {
        if(sum9 == null ) {
            sum9 = 0d;
        }
        return sum9;
    }

    public void setSum9(Number sum9) {
        this.sum9 = sum9;
    }

    public Number getSum10() {
        return sum10;
    }

    public void setSum10(Number sum10) {
        if(sum10 == null ) {
            sum10 = 0d;
        }
        this.sum10 = sum10;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        if(pageIndex < 0 ){
            pageIndex = 0 ;
        }
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) { 
        this.pageSize = pageSize;
    }
    
    public int getFirstRow(){
        return (pageIndex*pageSize)+1; 
    }
    public int getMaxRow(){
        return (pageIndex*pageSize) + pageSize;
    }
    
    public String getSum1Format() {
        return Format.formatNumber("#,##0.00", getSum1());
    }
    public String getSum2Format() {
        return Format.formatNumber("#,##0.00", getSum2());
    }
    public String getSum3Format() {
        return Format.formatNumber("#,##0.00", getSum3());
    }
    public String getSum4Format() {
        return Format.formatNumber("#,##0.00", getSum4());
    }
    public String getSum5Format() {
        return Format.formatNumber("#,##0.00", getSum5());
    }
    public String getSum6Format() {
        return Format.formatNumber("#,##0.00", getSum6());
    }
    public String getSum7Format() {
        return Format.formatNumber("#,##0.00", getSum7());
    }
    public String getSum8Format() {
        return Format.formatNumber("#,##0.00", getSum8());
    }
    public String getSum9Format() {
        return Format.formatNumber("#,##0.00", getSum9());
    }
    public String getSum10Format() {
        return Format.formatNumber("#,##0.00", getSum10());
    }
    
}
