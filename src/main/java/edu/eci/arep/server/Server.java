/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arep.Server;

import edu.eci.arep.requestHandler.RequestHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Clase que representa un servidor la cual resuelve solicitudes html,jpg,js.
 * @author Juan David
 */
public class Server implements Runnable {

    /**
     * Metodo init que inicia el servicio del servidor.
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void init() throws IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int port = getPort();
        ArrayList<RequestHandler> requestHandlers = new ArrayList<RequestHandler>();
        while (true) {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                System.out.println("Entra en a");
                System.exit(1);
            }
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Entra en B");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            inputLine = in.readLine();
            System.out.println("Voy a crear y correr el HILO");
            System.out.println(inputLine + "  <-----DizqueInputLine");
            RequestHandler rh = new RequestHandler(inputLine,clientSocket);
            rh.start();
            out.close();
            in.close();
            serverSocket.close();
        }
    }

    /**
     * Permite obtener el puerto el cual va a usar el servidor.
     * @return Retorna el numero del puerto a usar.
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}