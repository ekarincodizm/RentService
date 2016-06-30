/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.composor;

import com.sabuymlm.authen.SecurityUtil;
import com.sabuymlm.utils.Format;
import com.sabuymlm.utils.jasper.JRReportInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;  
import java.util.Date;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;  
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.util.media.AMedia; 
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent; 
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Image;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window; 
import org.zkoss.zul.event.PagingEvent; 

public class ShowReportComposor extends GenericForwardComposer {

    private Iframe reportFrame;
    private AMedia amedia; 
    private JRReportInputStream report; 
    private String exportType;
    private String fileName;
    private Object dataSource; 
    
    private Paging paging;
    private int pageSize; 

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);  
        arg.put("companyId", SecurityUtil.getUserDetails().getCompany().getId());
        arg.put("companyName", SecurityUtil.getUserDetails().getCompany().getAddr());
        arg.put("company", SecurityUtil.getUserDetails().getCompany().getCompanyName()); 
        arg.put("contactHeadOffice",SecurityUtil.getUserDetails().getCompany().getContact());
        arg.put("remarkInvoice",SecurityUtil.getUserDetails().getCompany().getRemarkInvoice());
        File file = new File(SecurityUtil.getUserDetails().getCompany().getLogoPath(), SecurityUtil.getUserDetails().getCompany().getLogo());
        if (file.exists()) {
            AImage image = new AImage(file);
            arg.put("logo", image.getStreamData());  
        }else {
            arg.put("logo", null);  
        }
        
        if (paging != null) {
            paging.setTotalSize(0);
            pageSize = paging.getPageSize();
            paging.addEventListener("onPaging", new EventListener() {

                @Override
                public void onEvent(Event event) throws Exception {
                    PagingEvent pe = (PagingEvent) event;
                    redraw(pe.getActivePage());
                }
            });
        }
        
        fileName = Format.formatDateEn("yyyyMMdd-HHmmssS", new Date())  ;
        exportType = (String) arg.get("exportType");
        dataSource = arg.get("dataSource");  
         
        if (arg.get("query") != null) {
            String query = (String) arg.get("query");
            FileInputStream in = new FileInputStream((String) arg.get("path"));
            report = new JRReportInputStream(in, query);
            arg.put("query", null);
        } else {
            report = new JRReportInputStream((String) arg.get("path"));
        }
        
        report.getJasperReport().setProperty(JRProperties.PROPERTIES_FILE, fileName); 
        if (dataSource instanceof JRBeanCollectionDataSource) {
            report.initJasperPrint(arg, (JRBeanCollectionDataSource) dataSource);
        } else {
            report.initJasperPrint(arg);
        }

        session.setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, report.getJasperPrint());
        redraw(0);
    } 
    
    public void onShowReport(ForwardEvent evnt) throws Exception{ 
        if(evnt.getOrigin().getTarget() instanceof Image ) {
            exportType = (String)((Image)evnt.getOrigin().getTarget()).getAttribute("export"); 
            redraw(0);
        }
    } 
    
    private void redraw(int startPage) {

        try {  
            
            paging.setVisible(exportType.equals("html")); 
            ByteArrayInputStream input = null ;
            
            if (exportType.equals("pdf")) {
                input = report.exportToPDF() ;
                amedia = new AMedia(fileName, "pdf", "application/pdf", input);
            } else if (exportType.equals("xls")) {
                input = report.exportToExcel() ;
                amedia = new AMedia(fileName, "xls", "application/vnd.ms-excel", input );
            } else if (exportType.equals("doc")) {
                input = report.exportToDoc() ;
                amedia = new AMedia(fileName, "rtf", "application/rtf", input);
            } else if (exportType.equals("html")) {
                input = report.exportToHtml(startPage, execution.getContextPath()) ;
                amedia = new AMedia(fileName, "html", "text/html;charset=UTF-8", input); 
            } else if (exportType.equals("xml")) {
                input = report.exportToXml() ;
                amedia = new AMedia(fileName, "xml", "text/xml;charset=UTF-8", input);
            } else if (exportType.equals("csv")) {
                input = report.exportToCsv() ;
                amedia = new AMedia(fileName, "csv", "text/plain", input);
            } else if (exportType.equals("txt")) {
                input = report.exportToText() ; 
                amedia = new AMedia(fileName, "text", "application/plain", input);
            }
            if(input != null) {
                input.close();
            }
            if (paging.isVisible()) {
                paging.setTotalSize(report.getLastPage());
                paging.setActivePage(startPage);
            }
            if(amedia != null) {
                reportFrame.setContent(amedia);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    } 

    public void onClick$btnCancel() {
        ((Window) self).onClose();
    }
}
