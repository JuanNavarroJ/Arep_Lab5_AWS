/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arep.Server;

import edu.eci.arep.annotations.Web;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Clase que representa un servidor la cual resuelve solicitudes html,jpg,js.
 * @author Juan David
 */
public class SpringBoot implements Runnable {

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
        while (true) {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                System.exit(1);
            }
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            inputLine = in.readLine();
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
                            if (!in.ready()) {
                                break;
                            }
                        }
                    }
                }

            } catch (NullPointerException e) {
            } catch (ClassNotFoundException ex) {
            }
            out.close();
            in.close();
            clientSocket.close();
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
