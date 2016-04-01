/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer.startup.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasen.lazarov
 */
public class ComputerStartupLogger {
    private static final String LOG_FILE_NAME = "startup-log";
    private static final String LOG_FILE_EXTENSION = ".txt";
    private static final String LOG_DIR = "startup-logs";
    private static final String START_TIME_DIR = "data/start-time";
    private static final String START_TIME_FILE_NAME = "start-time";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {       
        writeStartTime();
        long stTime = System.currentTimeMillis() - getStartTime();
        ComputerStartupLogger.writeLog(LogType.START.getName(), stTime);
        StartupLoggerWindow gadget = new StartupLoggerWindow();
        gadget.startGadget();
    }  
    
    public static void writeLog(String logEventName, long workTime) {
        String workTimeStr = longToDisplayString(workTime);        
        
        LocalDateTime dateTimeNow = LocalDateTime.now();
        int yearNow = dateTimeNow.getYear();
        String monthNowName = dateTimeNow.getMonth().name();
        String fullPath = LOG_DIR + "/" + yearNow + "/" + monthNowName;
        FileHandler fileHandler = new FileHandler();
        fileHandler.createDirIfNotExists(fullPath);
        
        String fullFileName =  dateStringNow() + "-" +  LOG_FILE_NAME + LOG_FILE_EXTENSION;
        System.out.println(fullPath + "/" + fullFileName);
        
        String outputStr = dateStringNow() + " - " + logEventName + " at - " + timeStrNow() + " - Work Time  - " + workTimeStr;
        PrintWriter out;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(fullPath + "/" + fullFileName, true)));
            out.println(outputStr);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ComputerStartupLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static long getStartTime() {
        FileHandler fileHandler = new FileHandler();
        String timeStr = fileHandler.readFile(startTimeFilePath());
        timeStr = timeStr.replace(System.getProperty("line.separator"), "");
        long timeLong =  Long.parseLong(timeStr);
       
        return timeLong;
    }
    
    public static void writeStartTime() {
        long startTime = System.currentTimeMillis();
        FileHandler fileHandler = new FileHandler();
        fileHandler.createDirIfNotExists(START_TIME_DIR);

        if (!fileHandler.fileExists(startTimeFilePath())) {
            PrintWriter out;
            try {
                out = new PrintWriter(new BufferedWriter(new FileWriter(startTimeFilePath(), true)));
                out.println(String.valueOf(startTime));
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(ComputerStartupLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static String startTimeFilePath() {
        String fullFileName = dateStringNow() + "-" + START_TIME_FILE_NAME + LOG_FILE_EXTENSION;
       return START_TIME_DIR + "/" + fullFileName;
    }
    
    private static String dateStringNow() {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        int yearNow = dateTimeNow.getYear();
        int monthNow = dateTimeNow.getMonthValue();
        int dayNow = dateTimeNow.getDayOfMonth();
        
        String dateNow = dayNow + "-" + monthNow + "-" + yearNow;
        return dateNow;
    }
    
    private static String timeStrNow() {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        String minutes = String.format("%02d", dateTimeNow.getMinute());
        
        String timeNow = dateTimeNow.getHour() + ":" + minutes;        
        return timeNow;
    }
    
    public static String longToDisplayString(long elapsedTime) {
        long second = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
        long displayHour = (second / (60 * 60)) % 24;
        long displayMinute = ((second / 60) % 60);
        long displaySecond = second % 60;
       return String.format("%02d:%02d:%02d", displayHour, displayMinute, displaySecond);
    }
}
