/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arep.client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Juan David
 */
public class ClientAws extends Thread {

    //Atributos
    
    private static URL url;
    private static int numThread;
    private static ArrayList<Thread> poolThread;
    
    public ClientAws(URL url){
        this.url=url;   
    }
    
    public static void main(String[] args) throws Exception {
        url = new URL(args[0]);
        numThread = Integer.parseInt(args[1]);
        poolThread = new ArrayList<Thread>();
        for(int i=0; i<numThread;i++){
            poolThread.add(new ClientAws(url));
        }
        int cant = 0;
        for(Thread th: poolThread){
            th.start();
            cant++;
        }
        System.out.println("Se ejecutaron " + cant + "de solicitudes concurrentes.");
    }
    
    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }
    
}
