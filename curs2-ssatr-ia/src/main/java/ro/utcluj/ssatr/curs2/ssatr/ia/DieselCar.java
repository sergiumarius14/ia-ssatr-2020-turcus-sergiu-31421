package ro.utcluj.ssatr.curs2.ssatr.ia;

public class DieselCar extends Car { //clasa derivata  

    public DieselCar(String name, int speed, String plateNumber) {
        super(name, speed, plateNumber); //apelare constructor din calsa de baza
    }

    void accelerate() { //suprascriere metoda
        speed += 2;
        System.out.println("Diesel car speed is " + speed);
    }
}
