package ro.utcluj.ssatr.curs2.ssatr.ia;

public class Car {

    //atribute
    private String name;
    public int speed;
    private String plateNumber;

    //constructori
    Car(String name, int speed, String plateNumber) {
        this.name = name;
        this.speed = speed;
        this.plateNumber = plateNumber;
    }

    void accelerate() {
        speed++;
        System.out.println("Vehicle speed is " + speed);
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

}
