/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proz.alpha;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

/**
 *
 * @author schoenix
 */
class MyNewMessageListener implements MessageListener {
    MyChat myChat;

    public MyNewMessageListener(MyChat myChat) {
        this.myChat = myChat;
    }
    //@Override
    public void processMessage(Chat chat, Message message) {
        System.out.println(chat.getParticipant() + ": " + message.getBody());
        myChat.setChat(chat);
    }
    
}
