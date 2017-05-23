/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.utils;

/**
 *
 * @author veckardt
 */
public class LastMessage {

    String lastMessage;

    public void init() {
        this.lastMessage = "";
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() {
        return (this.lastMessage);
    }
}
