/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.utcluj.ssatr.proiecttre.train;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sergi
 */
public class TrainServer {

    ArrayList<ClientHandler> clients = new ArrayList<>();

    public void startServer() {

        ServerSocket ss = null;
        try {
            ss = new ServerSocket(1988);
        } catch (IOException ex) {
            Logger.getLogger(TrainServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (true) {
            try {
                System.out.println("Waiting for train (client)...");
                Socket s = ss.accept();
                System.out.println("Train arrived! (Client connected!)");
                ClientHandler h = new ClientHandler(s, this);
                h.start();
                clients.add(h);
            } catch (IOException ex) {
                Logger.getLogger(TrainServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//.startServer}

    void sendMessageToAll(String msg) {
        for (ClientHandler c : clients) {
            c.sendMessage(msg);
        }
    }

    public static void main(String[] args) {
        TrainServer srv = new TrainServer();
        srv.startServer();
    }
}
//.class
//////////////////////////////////////////////////////////////////////////////////////////

class ClientHandler extends Thread {

    Socket s;
    BufferedReader fluxIn;
    PrintWriter fluxOut;
    TrainServer server;

    public ClientHandler(Socket s, TrainServer server) throws IOException {
        this.s = s;
        fluxIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        fluxOut = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
        this.server = server;
    }

    private Train getTrain(String trainString) {
        StringTokenizer st = new StringTokenizer(trainString);
        String rank = st.nextToken();
        int number = Integer.parseInt(st.nextToken());
        int stopTime = Integer.parseInt(st.nextToken());
        return new Train(rank, number, stopTime);
    }

    public void run() {
        try {
            while (true) {

                String msg = fluxIn.readLine();
                Train t= getTrain(msg);
                System.out.println("Train station receive train: " + msg);
                server.sendMessageToAll(msg);
            }

        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void sendMessage(String msg) {
        fluxOut.println(msg);
    }
}
