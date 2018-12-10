/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usess;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

/**
 *
 * @author omar
 */
public class Producer {
    
    public Connection  connection;
    public MessageProducer producer  = null;
    public  ActiveMQSession session ;
    
    public Producer(String queue) {
        try { 
            ActiveMQConnectionFactory connectionfactory = new ActiveMQConnectionFactory("tcp://localhost:61616?jms.blobTransferPolicy.uploadUrl=http://localhost:8161/fileserver/");        
            connection = connectionfactory.createConnection();
            connection.start(); 
             session = (ActiveMQSession) connection.createSession(false,Session.AUTO_ACKNOWLEDGE); 
            Destination destination = session.createQueue(queue);
    
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        } catch (JMSException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("New Producer ");

    }
    
    
    public void send(String file) throws JMSException, FileNotFoundException, IOException {
        File fileToPublish=new File(file);
        BytesMessage bm = session.createBytesMessage();
        InputStream in= new FileInputStream(fileToPublish);
        BufferedInputStream inBuf= new BufferedInputStream(in);
		int i;
		while((i=inBuf.read())!=-1){
                    bm.writeInt(i);
                }
        bm.writeInt(-1);
	producer.send(bm);
    }
    
   
    
}
