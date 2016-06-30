/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.service.scheduler;

import com.sabuymlm.utils.DateUtils;
import com.sabuymlm.utils.Entry;
import com.sabuymlm.utils.FileEntry;
import com.sabuymlm.utils.Format;
import java.io.File;
import java.util.Date; 
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service; 

/**
 *
 * @author MY-TENG
 */
@Service
public class ScheduleService {

    private final String FOLDER_PATH = "C:\\Backup Database";

    //  "* * * * * *"  pattern /// "ss mm HH dd MM yy"
    @Scheduled(cron = "0 0 3 * * *") /// delete file in folder 
    public void autoDeleteBackupDatabaseFile() {
  
        long compareLess =  Long.parseLong( Format.formatDateEn("yyyyMMdd", DateUtils.addDate( new Date(), -7) ));
        String substr = ""; 
        System.out.println(" START DELETE FILE ");
        FileEntry entryfile = new FileEntry(FOLDER_PATH);
        for (Entry en : entryfile.lsEntry) {
            try {
                if (en.getFileName().contains("_")) {
                    substr = en.getFileName().substring(en.getFileName().indexOf("_") + 1,en.getFileName().indexOf("."));
                    if(substr.length() == 8 && Format.isNumeric(substr) ) { 
                        long dateBkFile = Long.parseLong( substr ); 
                        if( dateBkFile < compareLess ){ 
                            File file = new File(FOLDER_PATH,en.getFileName());
                            if(file.delete()){
                                    System.out.println(file.getName() + " is deleted!");
                            }else{
                                    System.out.println("Delete operation is failed.");
                            }  
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
    }
 
}
