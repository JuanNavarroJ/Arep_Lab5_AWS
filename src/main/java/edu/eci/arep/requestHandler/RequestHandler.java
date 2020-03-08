/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arep.requestHandler;

import edu.eci.arep.annotations.Web;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juan.navarro
 */
public class RequestHandler extends Thread {

    private String inputLine;
    private Socket clientSocket;

    public RequestHandler(String inputLine, Socket clientSocket) {
        this.inputLine = inputLine;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            String[] listaURL = inputLine.split(" ");
            String[] get = listaURL[1].split("/");
            if (listaURL[1].contains("/WebService")) {
                Class<?> c = Class.forName("edu.eci.arep.WebService." + get[1]);
                for (Method metodo : c.getMethods()) {
                    if (metodo.isAnnotationPresent(Web.class)) {
                        String[] ans = get[2].split("[, ?.@]+");
                        if (metodo.getName().equals(ans[1])) {
                            metodo.invoke(c, "/src/main/resources/" + get[2], clientSocket.getOutputStream());
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            clientSocket.close();

            /**
             * PrintWriter out = new PrintWriter(
             * clientSocket.getOutputStream(), true); BufferedReader in = new
             * BufferedReader( new
             * InputStreamReader(clientSocket.getInputStream())); String
             * inputLine; while ((inputLine = in.readLine()) != null) { if
             * (inputLine.contains("GET")) { String[] listaURL =
             * inputLine.split("/"); String[] get = listaURL[1].split(" "); if
             * (get[0].contains(".jpg")) { //img("/src/main/resources/img/" +
             * get[0], clientSocket.getOutputStream()); } else if
             * (get[0].contains(".html")) { //html("/src/main/resources/hmtl/" +
             * get[0], clientSocket.getOutputStream()); } else if
             * (get[0].contains(".js")) { //js("/src/main/resources/js/" +
             * get[0], clientSocket.getOutputStream()); }
             *
             * }
             * if (!in.ready()) { break; } } out.close(); in.close();
             * clientSocket.close(); //serverSocket.close();*
             */
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
