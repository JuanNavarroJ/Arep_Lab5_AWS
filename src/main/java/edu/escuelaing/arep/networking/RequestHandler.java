/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arep.networking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 *
 * @author juan.navarro
 */
public class RequestHandler extends Thread{
    
    
    public void run(){
        PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("GET")) {
                    String[] listaURL = inputLine.split("/");
                    String[] get = listaURL[1].split(" ");
                    if (get[0].contains(".jpg")) {
                        img("/src/main/resources/img/" + get[0], clientSocket.getOutputStream());
                    }else if(get[0].contains(".html")) {
                	html("/src/main/resources/hmtl/"+get[0],clientSocket.getOutputStream());
                    }else if(get[0].contains(".js")) {
                	js("/src/main/resources/js/"+get[0],clientSocket.getOutputStream());	
                    }                   

                }
                if (!in.ready()) {
                    break;
                }
            }
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
    }
    
}
