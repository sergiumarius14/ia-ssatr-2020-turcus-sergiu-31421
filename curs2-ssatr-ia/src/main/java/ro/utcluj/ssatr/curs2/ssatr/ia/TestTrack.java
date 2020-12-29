package ro.utcluj.ssatr.curs2.ssatr.ia;

import java.util.ArrayList;

public class TestTrack {

//    Car[] cars = new Car[10]; // [null null null null ....]
    ArrayList<Car> cars = new ArrayList<>();

    void addCar(Car c) {
//        for (int i = 0; i < cars.length; i++) {
//            if (cars[i] == null) {
//                cars[i] = c;
//                System.out.println("New car added on test track.");
//                return;
//            }
//        }
//        System.out.println("No empty position found on test track.");
        cars.add(c);
        System.out.println("New car added on test track.");
    }

    //polimorfism - mecanimsul prin care in mod dinamic la rulare se determina tipul concert al obiectului
    // si se apeleaza metoda coreszpunzatoare
    void testAccelerate(int k) {
        for (int i = 0; i < k; i++) {
            for (Car c : cars) { //foreach
                if (c != null) {
                    c.accelerate();
                }
            }
        }
    }

    String getAllCarsDetails() {
        String all = "";
        for (Car c : cars) {
            if (c != null) {
                String line = "Car name=" + c.getName() + ", speed=" + c.speed + ", plate number=" + c.getPlateNumber() + "\n";
                all = all + line;
            }
        }
        return all;
    }
}
