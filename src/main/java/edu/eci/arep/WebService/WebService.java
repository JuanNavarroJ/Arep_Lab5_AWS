/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arep.WebService;
import edu.eci.arep.annotations.Web;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;

/**
 * Clase WebService la cual por medio de etiquetas pemite la ejecucion de sus funcionalidades.
 * @author juan.navarro
 */

public class WebService {
    /**
     * Metodo que da un recurso HTML al usuario.
     * @param element Es la ruta donde se encuentra el recurso.
     * @param clientOutput Es quien solicita el recurso.
     */
    @Web
    public static void html(String element, OutputStream clientOutput){
        try {
            String text = "";
            String temp;
            BufferedReader t = new BufferedReader(new FileReader(System.getProperty("user.dir") + element));
            while ((temp = t.readLine()) != null) {
                text = text + temp;
            }
            clientOutput.write(("HTTP/1.1 201 Found  \r\n"
                    + "Content-Type: text/html; charset=\"utf-8\" \r\n"
                    + "\r\n"
                    + text).getBytes());
        } catch (IOException e) {
        }        
    }   
    
    /**
     * Metodo que da un recurso JPG al usuario.
     * @param element Es la ruta donde se encuentra el recurso.
     * @param clientOutput Es quien solicita el recurso.
     */
    @Web
    public static void jpg(String element, OutputStream clientOutput){
        try {
            BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + element));
            ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
            DataOutputStream writeimg = new DataOutputStream(clientOutput);
            ImageIO.write(image, "PNG", ArrBytes);
            writeimg.writeBytes("HTTP/1.1 200 OK \r\n");
            writeimg.writeBytes("Content-Type: image/png \r\n");
            writeimg.writeBytes("\r\n");
            writeimg.write(ArrBytes.toByteArray());
        } catch (IOException e) {

        }
        
    }  
    
    /**
     * Metodo que da un recurso JS al usuario.
     * @param element Es la ruta donde se encuentra el recurso.
     * @param clientOutput Es quien solicita el recurso.
     */
    @Web
    public static void js(String element, OutputStream clientOutput){
        try {
            BufferedReader read = new BufferedReader(new FileReader(System.getProperty("user.dir") + element));
            String cont = "";
            String line;
            while ((line = read.readLine()) != null) {
                cont = cont + line;
            }
            clientOutput.write(("HTTP/1.1 404 Not Found \r\n"
                    + "Content-Type: application/json; charset=\"utf-8\" \r\n"
                    + "\r\n"
                    + cont).getBytes());
        } catch (IOException e) {
        }        
    }  
}
