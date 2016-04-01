/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer.startup.logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author yasen.lazarov
 */
public class MessageHandler {
    private Message[] messages;
    
    public MessageHandler() {
        
    }
    
    public void add(Message message) {
        this.messages[this.messages.length] = message;
    }
    
    public Message getLast() {
        return this.messages[this.messages.length - 1];
    }
    
    private static void ShowAllMessages(Message[] messages) {
        JFrame frame = new JFrame("Startup Logger Message");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        String messagesText = "";        
        for(Message m : messages){
           messagesText += m.getText() + "\n";
        }
        
         JOptionPane.showMessageDialog(frame, messagesText);
    }
}
