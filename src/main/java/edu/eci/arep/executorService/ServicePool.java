/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arep.ExecutorService;

import edu.eci.arep.Server.Server;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Juan David
 */
public class ServicePool {
    //Atributos
    private static ExecutorService servicePool = Executors.newFixedThreadPool(10);
    
    public static void main( String[] args ) throws Exception
    {
        Server server=new Server();
        server.init();
        servicePool.submit(server);
    }
}