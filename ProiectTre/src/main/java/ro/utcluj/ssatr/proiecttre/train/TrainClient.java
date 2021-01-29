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
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author sergiu
 */
public class TrainClient extends Thread {

    enum TrainRank {
        R,
        IR,
        IRN
    }

    BufferedReader fluxIn;
    PrintWriter fluxOut;

    public TrainClient() throws IOException {
        Socket s = new Socket("127.0.0.1", 1988);
        fluxIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        fluxOut = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
    }

    private Train initTrain() {
        int trainNumber = parseInt(RandomStringUtils.randomNumeric(3));
        int idx = new Random().nextInt(TrainRank.values().length);
        String rank = TrainRank.values()[idx].toString();
        return new Train(rank, trainNumber);
    }

    private String getTrainString(Train t) {

        return t.getRank() + " " + t.getNumber();
    }

    public void run() {
        while (true) {
            String line;
            try {
                Train t = initTrain();
                String train = getTrainString(t);
                fluxOut.println(train);
                line = fluxIn.readLine();
                //TODO: add platform where train stops
                if (parseInt(line) / 1000 != 0) {
                       System.out.println("Trenul " + t.getRank() + " " + t.getNumber() + " a ajuns in gara! Acesta va stationa " + parseInt(line) / 1000 + " minute!");
                    } else {
                        System.out.println("Trenul " + t.getRank() + " " + t.getNumber() + " va trece prin gara!");
                    }
                try {
                    Thread.sleep(parseInt(line));
                    if (parseInt(line) / 1000 != 0) {
                        System.out.println("Trenul " + t.getRank() + " " + t.getNumber() + " a parasit gara!");
                    } else {
                        System.out.println("Trenul " + t.getRank() + " " + t.getNumber() + " a trecut prin gara!");
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(TrainClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(TrainClient.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }

        }
    }

    public void sendMessage(String msg) {
        fluxOut.println(msg);
    }

    public static void main(String[] args) throws IOException {
        TrainClient c = new TrainClient();
        c.start();

    }

}
