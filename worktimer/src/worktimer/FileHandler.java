/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worktimer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasen.lazarov
 */
public class FileHandler {

    /**
     *
     * @param dir
     * @return
     */
    public static boolean createDirIfNotExists(String dir) {
        File file = new File(dir);
        boolean created = false;
        if (!file.exists()) {
            created = file.mkdirs();
        }        
        return created;
    }
    
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
    
    public static String readFile(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return content;
    }
}
