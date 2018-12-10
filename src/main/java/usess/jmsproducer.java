/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usess;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.jms.JMSException;

/**
 *
 * @author omar
 */
public class jmsproducer {
    
    public static void main(String[] argv) throws JMSException, FileNotFoundException, IOException {
        
        String outDir="/Users/omar/Desktop/web/";
   
        Producer producer = new Producer("QueDD");
        producer.send("/Users/omar/Downloads/windows.JPG");
   
        Consumer consumer = new Consumer("QueDD");
        consumer.receiv("outDir");
   
   
   
    }
   
}
