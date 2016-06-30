/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.utils;

import java.io.File; 
import java.io.IOException;
import java.util.UUID;

/**
 *
 * @author sumrit
 */
public class Files {

    public static File createTempDir() throws IOException {
        final File sysTempDir = new File(System.getProperty("java.io.tmpdir"));
        File newTempDir;
        final int maxAttempts = 9;
        int attemptCount = 0;
        do {
            attemptCount++;
            if (attemptCount > maxAttempts) {
                throw new IOException("The highly improbable has occurred! Failed to "
                        + "create a unique temporary directory after "
                        + maxAttempts + " attempts.");
            }
            String dirName = UUID.randomUUID().toString();
            newTempDir = new File(sysTempDir, dirName);
        } while (newTempDir.exists());
        if (newTempDir.mkdirs()) {
            return newTempDir;
        } else {
            throw new IOException("Failed to create temp dir named "
                    + newTempDir.getAbsolutePath());
        }
    }

    public static boolean recursiveDelete(File fileOrDir) {
        if (fileOrDir.isDirectory()) {
            // recursively delete contents 
            for (File innerFile : fileOrDir.listFiles()) {
                if (!Files.recursiveDelete(innerFile)) {
                    return false;
                }
            }
        }
        return fileOrDir.delete();
    }
     
}
