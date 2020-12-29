package ro.utcluj.ssatr.aplicatie.parcare;

public class CarAccess {

    private String plateNumber;
    private long accessTime;

    public CarAccess(String plateNumber, long accessTime) {
        this.plateNumber = plateNumber;
        this.accessTime = accessTime;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(long accessTime) {
        this.accessTime = accessTime;
    }
}
