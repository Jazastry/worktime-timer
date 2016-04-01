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
public class Message {
    private final String text;
    private final MessageType type;
    
    public Message(String text, MessageType type) {
        this.type = type;
        this.text = text;
    }

    /**
     * @return the text
     */
    public String getText() {
        return this.text;
    }

    /**
     * @return the type
     */
    public MessageType getType() {
        return this.type;
    }
}
