/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer.startup.logger;

/**
 *
 * @author yasen.lazarov
 */
public enum LogType {
    LOGIN("LOGIN"),
    START("START"),
    STOP("STOP"),
    LOGOUT("LOGOUT"),
    PAUSE("PAUSE");


    private final String name;

    LogType(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }    
}
