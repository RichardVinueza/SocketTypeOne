/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tibur
 */
public class HiloParaAntenderUnCliente extends Thread {

    Socket sk;

    public HiloParaAntenderUnCliente(Socket sk) {
        this.sk = sk;
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;
        int number;
        int numberToguess = 34;
        int numberHot = 24;
        int numberHot2 = 44;
        int numberTooHot = 30;
        int numberTooHot2 = 38;
        try {
            is = sk.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            os = sk.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            Inet4Address ip = (Inet4Address) sk.getInetAddress();
            String laIP = ip.getHostAddress();

            while (true) {
                System.out.println("Esperando algo");
                String linea = br.readLine();
                System.out.println(laIP + ": " + linea);
                number = Integer.parseInt(linea);
                if (number == numberToguess) {
                    bw.flush();
                    bw.write("Congratulations. You have guessed the number");
                
                }
                if (number > numberHot2 && number < 100) {
                    bw.flush();
                    bw.write("Cold");

                }
                if (number < numberHot && number > 0) {
                    bw.flush();
                    bw.write("Cold");
                    
                }
                if ( number < numberTooHot && number > numberHot ) {
                    bw.flush();
                    bw.write("Hot");
                    
                }
                if ( number > numberTooHot2 && number < numberHot2 ) {
                    bw.flush();
                    bw.write("Hot");
                    
                }
                if (number < numberToguess && number >= numberTooHot) {
                    bw.flush();
                    bw.write("Very Hot");
                    
                }
                if (number > numberToguess && number <= numberTooHot2) {
                    bw.flush();
                    bw.write("Very Hot");
                    
                }            
                bw.newLine();
                bw.flush();

            }

        } catch (IOException ex) {
            Logger.getLogger(HiloParaAntenderUnCliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(HiloParaAntenderUnCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
