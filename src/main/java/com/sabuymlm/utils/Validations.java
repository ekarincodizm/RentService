 
package com.sabuymlm.utils;

import java.util.Set;
import javax.validation.ConstraintViolation;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author Sumrit.p
 */
public class Validations {
    public static void showValidationError(Set<? extends ConstraintViolation> validationViolations){
        StringBuilder sb = new StringBuilder("<ul>");
        for(ConstraintViolation v: validationViolations){
            sb.append("<li>").append(v.getMessage()).append("</li>");
        }
        sb.append("</ul>");
        Clients.showNotification(sb.toString(), Clients.NOTIFICATION_TYPE_ERROR, null , "middle_center", 10000, true);
    }
}
