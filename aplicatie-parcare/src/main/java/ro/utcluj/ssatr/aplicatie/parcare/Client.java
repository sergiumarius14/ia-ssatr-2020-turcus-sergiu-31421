package ro.utcluj.ssatr.aplicatie.parcare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;

public class Client {
    static String response="";
    
    public static void startClient() throws IOException{
         System.out.println("Ma conectez la server.");
        Socket s = new Socket("127.0.0.1", 4050);
        System.out.println("Conexiune realizata!");
        //...... 
        BufferedReader fluxIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter fluxOut = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);

        fluxOut.println("CT-18-CCC");
        response = fluxIn.readLine();
        System.out.println(response);

        fluxOut.println("DJ-01-MSI");
        response = fluxIn.readLine();
        System.out.println(response);

        fluxOut.println("CT-18-CCC");
        response = fluxIn.readLine();
        System.out.println(response);

        fluxOut.println("PH-02-BUC");
        response = fluxIn.readLine();
        System.out.println(response);

        fluxOut.println("PH-02-BUC");
        response = fluxIn.readLine();
        System.out.println(response);

        fluxOut.println("DJ-01-MSI");
        response = fluxIn.readLine();
        System.out.println(response);

        fluxOut.println("close connection");
        response = fluxIn.readLine();
        System.out.println(response);

        s.close();
    }

    public static String getResponse() {
        return response;
    }

    public static void main(String[] args) throws IOException {
       startClient();
    }
}
