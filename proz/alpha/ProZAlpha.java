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
 
public class ProZAlpha
{

    public static void main(String args[]) throws XMPPException, IOException, InterruptedException
    {
        MyConnect myConnect = new MyConnect();
        myConnect.prozess();
    }
}
