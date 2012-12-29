/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proz.alpha;

/**
 *
 * @author schoenix
 */

import java.util.*;
import java.io.*;
import java.io.Console;
 
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;


class MyConnect {
    
    Connection connection;
    MyChat myChat;
    
    /*
    public MyChat getMyChat() {
        return myChat;
    }

    public void setMyChat(MyChat myChat) {
        this.myChat = myChat;
    }*/
    
    public void displayBuddyList()
    {
        Roster roster = connection.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();

        System.out.println("\n\n" + entries.size() + " buddy(ies):");
        System.out.println("------------------------");
        for(RosterEntry r:entries)
        {
            System.out.println(r.getUser());
        }
        System.out.println("------------------------");
    }
    
    public void prozess() throws XMPPException, IOException, InterruptedException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String msg;
        
        System.out.print("Server: ");
        String server = br.readLine();
        //System.out.print("\n");
        
        System.out.print("Port: (Standart: 5222): ");
        String portS = br.readLine();
        int port = Integer.parseInt(portS);
        //System.out.print("\n");
        
        ConnectionConfiguration config = new ConnectionConfiguration(server, port);
        //config.setCompressionEnabled(true);
        config.setSASLAuthenticationEnabled(true);
        
        connection = new XMPPConnection(config);
        connection.connect();
        
        System.out.print("Username: ");
        String username = br.readLine();
        //System.out.print("\n");
        //http://www.techsupportforum.com/forums/external-link/?link=http%3A%2F%2Fjava.sun.com%2Fdeveloper%2FtechnicalArticles%2FSecurity%2Fpwordmask%2F
        char[] passwd;
        Console cons;
        String password = "";
        if ((cons = System.console()) != null)
        {
            if((passwd = cons.readPassword("[%s]", "Password: ")) != null) 
            {
                password = new String(passwd);
            }
        }
        else
        {
            System.out.print("Password: ");
            password = br.readLine();
        }
        //System.out.print("\n");
        
        connection.login( username, password);
        
        displayBuddyList();
        
        
        
        // Assume we've created a Connection name "connection".
        ChatManager chatmanager = connection.getChatManager();
        myChat = new MyChat();
        myChat.setChat(null);
        chatmanager.addChatListener(
            new ChatManagerListener() {
                @Override
                public void chatCreated(Chat chat, boolean createdLocally)
                {
                    if (!createdLocally) {
                        chat.addMessageListener(new MyNewMessageListener(myChat));
                    }
                }
            });
        Thread.sleep(10000);
        //String talkTo = "";
        if(myChat.getChat() == null)
        {
            System.out.println("Who do you want to talk to? - Type contacts full email address:");
            String talkTo = br.readLine();
            System.out.println("-----");
            System.out.println("All messages will be sent to " + talkTo);

            myChat.setChat(chatmanager.createChat(talkTo, 
                        new MessageListener() {

                            @Override
                            public void processMessage(Chat chat, Message message) {
                                System.out.println(chat.getParticipant() + ": " + message.getBody());
                            }
                        }
                    )
                );
        }
        
        
        System.out.println("Enter your message in the console:");
        System.out.println("-----\n");
        while(!(msg = br.readLine()).equals("exit"))
        {
            System.out.print(username + ": ");
            try {
                myChat.getChat().sendMessage(msg);
            }
            catch(XMPPException e) {
                System.out.print("\n");
                System.out.println("Error Delivering block1");
            }
            System.out.print("\n");
        }
        System.out.println("----Exit---");
        connection.disconnect();
    }
    
}
