package ro.utcluj.ssatr.curs2.ssatr.ia;

public class OttoCar extends Car {

    public OttoCar(String name, int speed, String plateNumber) {
        super(name, speed, plateNumber);
    }

    void accelerate() {
        speed += 3;
        System.out.println("Otto car speed is " + speed);
    }

}
