/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usess;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQTextMessage;

/**
 *
 * @author omar
 */
public class Consumer {
    
    public Connection  connection;
    public MessageConsumer consummer  = null;
    public ActiveMQSession session ;
    
    public Consumer(String queue) {
        try { 
            ActiveMQConnectionFactory connectionfactory = new ActiveMQConnectionFactory("tcp://localhost:61616?jms.blobTransferPolicy.uploadUrl=http://localhost:8161/fileserver/");        
            connection = connectionfactory.createConnection();
            connection.start(); 
             session = (ActiveMQSession) connection.createSession(false,Session.AUTO_ACKNOWLEDGE); 
            Destination destination = session.createQueue(queue);
    
            consummer = session.createConsumer(destination);

        } catch (JMSException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("New Consummer ");

    }
    
    
    public void receiv(String directory) throws JMSException, FileNotFoundException, IOException {    

        Message message = consummer.receive();
        System.out.println("usess.Consumer.receiv()");
        
        BytesMessage bm = (BytesMessage)message;
	File file = new File(directory+"/"+message.getJMSMessageID());
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream outBuf = new BufferedOutputStream(fos);
	int i;
	while((i=bm.readInt())!=-1){
            outBuf.write(i);
	}
        outBuf.close();
        fos.close();
                  
    }
}