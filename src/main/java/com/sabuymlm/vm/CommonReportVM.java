/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm; 
 
import java.io.Serializable;
import java.util.Date;  
import java.util.Map;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

/**
 *
 * @author MY-TENG 
 */
public abstract class CommonReportVM implements Serializable {
    protected Date startdate ;
    protected Date enddate ;
    private String icon = "";
    private String headerLabel = ""; 
    protected Map param ; 
    
    protected void initialVM( String icon, String headerLabel) { 
        this.icon = icon;
        this.headerLabel = headerLabel; 
    }
    public String getIcon() {
        return icon;
    }

    public String getHeaderLabel() {
        return headerLabel;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
     
    public abstract void search();
    public abstract String condition();
    public void openReportWindowPopup(Map argMap) {
        Window window = (Window) Executions.createComponents("/secured/report/report.zul", null , argMap);
        window.setSizable(true);  
    } 
    
}
