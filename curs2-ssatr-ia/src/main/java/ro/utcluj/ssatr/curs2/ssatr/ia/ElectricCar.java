package ro.utcluj.ssatr.curs2.ssatr.ia;

public class ElectricCar extends Car {

    private int batteryLevel;

    public ElectricCar(String name, int speed, String plateNumber) {
        super(name, speed, plateNumber);
        batteryLevel = 100;
    }

    @Override //poate sa lipseasca
    void accelerate() {
        speed += 4;
        batteryLevel--;
        System.out.println("Electric car speed is " + speed + " and battery level is " + batteryLevel);
    }

}
