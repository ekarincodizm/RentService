package com.sabuymlm.utils.jasper;
 
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException; 
import java.util.Locale;

import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader; 
import org.zkoss.zul.Filedownload;

public class JRReportInputStream implements Serializable {

    protected JasperReport jasperReport = null;
    protected JasperPrint jasperPrint = null;
    protected static final Locale THAI_LOCAL = new Locale("th", "TH");
    protected static final String IMAGES_URI = "/servlets/image?image=";
    private static Connection conn = null;

    public JRReportInputStream(String jasperPath) throws Exception {
        try {
            jasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasperPath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JRReportInputStream(InputStream is, String query) throws Exception {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(is);
            if (query != null) {
                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(query);
                jasperDesign.setQuery(newQuery);
            }
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                JasperDesign jasperDesign = JRXmlLoader.load(is);
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
            } catch (Exception ex) {
            }

            throw new Exception(e.getMessage());
        }
    }

    protected JRHtmlExporter setParameterJRHtmlExporter(String contextPath) {
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, contextPath + IMAGES_URI); 
        exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");
        exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
        exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "");
        exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, JRHtmlExporterParameter.SIZE_UNIT_POINT);
        return exporter;
    }

    public ByteArrayInputStream exportToHtml(int pageIndex, String contextPath) throws JRException, SQLException, IOException {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRHtmlExporter exporter = setParameterJRHtmlExporter(contextPath);
        exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, output);
        exporter.setParameter(JRHtmlExporterParameter.PAGE_INDEX, pageIndex);
        exporter.exportReport();
        byte[] bcontent = output.toByteArray() ;
        output.close();
        return new ByteArrayInputStream(bcontent);

    }
    
    public ByteArrayInputStream exportToHtml(String contextPath) throws JRException, SQLException, IOException {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRHtmlExporter exporter = setParameterJRHtmlExporter(contextPath);
        exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, output);
        exporter.exportReport();
        byte[] bcontent = output.toByteArray() ;
        output.close();
        return new ByteArrayInputStream(bcontent);

    }
    
    public ByteArrayInputStream exportToXml() throws JRException, SQLException, IOException {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRXmlExporter exporter = new JRXmlExporter(); 
        exporter.setParameter(JRXmlExporterParameter.JASPER_PRINT, jasperPrint); 
        exporter.setParameter(JRXmlExporterParameter.OUTPUT_STREAM, output);  
        exporter.setParameter(JRXmlExporterParameter.IS_EMBEDDING_IMAGES, Boolean.FALSE); 
        exporter.exportReport();
        byte[] bcontent = output.toByteArray() ;
        output.close();
        return new ByteArrayInputStream(bcontent);

    }

    protected JRPdfExporter setParameterJRPdfExporter() {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
        exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
        return exporter;
    }

    public ByteArrayInputStream exportToPDF() throws JRException, SQLException, IOException {
       
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRPdfExporter exporter = setParameterJRPdfExporter();
        exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, output);
        exporter.exportReport();
        byte[] bcontent = output.toByteArray() ;
        output.close();
        return new ByteArrayInputStream(bcontent);
    }

    public ByteArrayOutputStream exportToPDFOutputStream() throws JRException, SQLException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRPdfExporter exporter = setParameterJRPdfExporter();
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
        exporter.exportReport(); 
        return output;
    }

    public void downloadToPDF(String reportName) throws JRException, SQLException {
        Filedownload.save(exportToPDFOutputStream().toByteArray(), "application/pdf", reportName);
    }

    protected JExcelApiExporter setParameterJExcelApiExporter() {
        JExcelApiExporter exporter = new JExcelApiExporter();
        exporter.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        exporter.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
        exporter.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JExcelApiExporterParameter.MAXIMUM_ROWS_PER_SHEET, Integer.decode("65000"));
        /// หมายถึงไม่ต้องแบ่ง page ให้เป็นหน้าเดียวกัน
        exporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        return exporter;
    }

    public ByteArrayInputStream exportToExcel() throws JRException, SQLException, IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JExcelApiExporter exporter = setParameterJExcelApiExporter();
        exporter.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
        exporter.exportReport();
        byte[] bcontent = output.toByteArray() ;
        output.close();
        return new ByteArrayInputStream(bcontent);
    }

    public ByteArrayOutputStream exportToExcelOutputStream() throws JRException, SQLException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JExcelApiExporter exporter = setParameterJExcelApiExporter();
        exporter.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
        exporter.exportReport();
        return output;
    }

    public void downloadToExcel(String reportName) throws JRException, SQLException {
        Filedownload.save(exportToExcelOutputStream().toByteArray(), "application/vnd.ms-excel", reportName);
    }
    
    public ByteArrayInputStream exportToCsv() throws JRException, SQLException, IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRCsvExporter exporter = new JRCsvExporter(); 
        exporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, jasperPrint);  
        exporter.setParameter(JRCsvExporterParameter.OUTPUT_STREAM, output);   
        exporter.setParameter(JRCsvExporterParameter.CHARACTER_ENCODING, "UTF-8"); 
        exporter.exportReport();
        byte[] bcontent = output.toByteArray() ;
        output.close();
        return new ByteArrayInputStream(bcontent);
    }

    public ByteArrayInputStream exportToDoc() throws JRException, SQLException, IOException {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRRtfExporter exporterRTF = new JRRtfExporter(); 
        exporterRTF.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporterRTF.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
        exporterRTF.exportReport();
        byte[] bcontent = output.toByteArray() ;
        output.close();
        return new ByteArrayInputStream(bcontent);

    }
    
    public ByteArrayInputStream exportToText() throws JRException, SQLException, IOException {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRTextExporter exporter = new JRTextExporter();
        exporter.setParameter(JRTextExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRTextExporterParameter.OUTPUT_STREAM, output); 
        exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Float(6.55) ); 
        exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Float(11.9) );  
        exporter.exportReport();
        byte[] bcontent = output.toByteArray() ;
        output.close();
        return new ByteArrayInputStream(bcontent);

    }

    public JasperReport getJasperReport() {
        return jasperReport;
    }

    public int getFirstPage() {
        return 0;
    }

    public int getLastPage() {
        return jasperPrint.getPages().size();
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public void initJasperPrint(Map h, JRDataSource dataSource) throws Exception {
        h.put(JRParameter.REPORT_LOCALE, THAI_LOCAL); 
        jasperPrint = JasperFillManager.fillReport(jasperReport, h, dataSource);
    }

    public void initJasperPrint(Map h) throws Exception {
        h.put(JRParameter.REPORT_LOCALE, THAI_LOCAL);
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, h, getConnection());
        } catch (JRException ex) {
            throw new RuntimeException(ex);
        } finally {
            setCloseConnection();
        }
    }

    public static Connection getConnection() throws Exception {
        try {

            if (conn == null || conn.isClosed()) {
                Context ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("jdbc/rentService");
                conn = ds.getConnection();
            }
            return conn;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setCloseConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
