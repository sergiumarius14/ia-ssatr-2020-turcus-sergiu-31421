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
import static java.lang.Integer.parseInt;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author sergiu
 */
public class TrainServer {

    ArrayList<ClientHandler> clients = new ArrayList<>();
    List<String> availablePlatformList = Collections.synchronizedList(new ArrayList(Arrays.asList("Platform-1", "Platform-2", "Platform-3", "Platform-4")));
    List<String> unavailablePlatformList = Collections.synchronizedList(new ArrayList<String>());

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

    public static void main(String[] args) {
        TrainServer srv = new TrainServer();
        srv.startServer();
    }

    public String makePlatformUnavailable() {
        String platform = availablePlatformList.get(0);
        availablePlatformList.removeIf(availablePlatform -> availablePlatform.equals(platform));
        unavailablePlatformList.add(platform);
        return platform;
    }

    void makePlatformAvailable(String platform) {
        //Search in unavailable patfrom the right platform and make it available
        unavailablePlatformList.remove(platform);
        availablePlatformList.add(platform);
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

    private Train getTrain(String trainString, String stopTime) {
        StringTokenizer st = new StringTokenizer(trainString);
        String rank = st.nextToken();
        int number = Integer.parseInt(st.nextToken());
        return new Train(rank, number, parseInt(stopTime));
    }

    public void run() {
        try {
            while (true) {

                String msg = fluxIn.readLine();
                String stopTime = RandomStringUtils.randomNumeric(4);
                Train t = getTrain(msg, stopTime);
                String platform = server.makePlatformUnavailable();
                System.out.println("Train station receive train: " + msg);
                System.out.println("Train " + t.getNumber() + " is at " + platform + " for " + Integer.parseInt(stopTime) / 1000 + " minutes (" + stopTime + " seconds)"
                );
                String response = stopTime + " " + platform;
                sendMessage(response);
                msg = fluxIn.readLine();
//                StringTokenizer st = new StringTokenizer(msg);
//                String clientMsg = st.nextToken();
//                platform = st.nextToken();
                server.makePlatformAvailable(platform);
            }

        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void sendMessage(String msg) {
        fluxOut.println(msg);
    }
}
