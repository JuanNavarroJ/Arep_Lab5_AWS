/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arep.client;

import java.io.*;
import java.net.*;

/**
 *
 * @author Juan David
 */
public class URLReader {

    public static void main(String[] args) throws Exception {
        URL url = new URL(args[0]);
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