package ro.utcluj.ssatr.aplicatie.parcare;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {

    static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    static ArrayList<CarAccess> carList = new ArrayList<>();

    public static CarAccess findPlate(String plateNumber, List<CarAccess> carsList) {
        for (CarAccess car : carsList) {
            if (car.getPlateNumber().equals(plateNumber)) {
                return car;
            }
        }
        return null;
    }

    public static String handlePlateNumber(String plateNumber) throws InterruptedException {
        String handleResponse = null;
        CarAccess carAccess = new CarAccess(plateNumber, System.currentTimeMillis());
        Date date = new Date(System.currentTimeMillis());
        int nrOfMiliSeconds=1000;
        if (carList.size() == 0) {
            carList.add(carAccess);
            handleResponse = "Intrare: " + plateNumber + " Ora " + formatter.format(date);
            Thread.sleep(nrOfMiliSeconds);
        } else if (null == findPlate(plateNumber, carList)) {
            carList.add(carAccess);
            handleResponse = "Intrare: " + plateNumber + " Ora " + formatter.format(date);
            Thread.sleep(nrOfMiliSeconds);
        } else {
            long time = System.currentTimeMillis() - findPlate(plateNumber, carList).getAccessTime();
            long price = time / nrOfMiliSeconds;
            carList.remove(findPlate(plateNumber, carList));
            handleResponse = " Iesire: " + plateNumber + " Cost " + price + " RON";
            Thread.sleep(nrOfMiliSeconds);
        }

        return handleResponse;
    }

    public static void startServer() throws IOException, InterruptedException{
         ServerSocket ss = new ServerSocket(4050);

        while (true) {
            System.out.println("Astept conexiune de la client...");
            Socket s = ss.accept(); //metoda blocanta
            System.out.println("Clientul s-a conectat!");
            
            BufferedReader fluxIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter fluxOut = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
            
            String line = "";
            while (!line.equals("close connection")) {
                line = fluxIn.readLine();
                System.out.println("Am primti de la client: " + line);
                if (!line.equals("close connection")) {
                    String plateNumber = line;
                    String resonse = handlePlateNumber(plateNumber);
                    fluxOut.println(resonse);
                }
            }
            s.close();
        }
    }

    public static ArrayList<CarAccess> getCarList() {
        return carList;
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
       startServer();
    }
}
