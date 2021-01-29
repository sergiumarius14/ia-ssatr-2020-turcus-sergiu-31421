/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.utcluj.ssatr.proiecttre.train;

/**
 *
 * @author sergiu
 */
public class Train {

    private String rank;
    private int number;
    private int stopTime;

    public Train(String rank, int number) {
        this.rank = rank;
        this.number = number;
    }

    public Train(String Rank, int number, int stopTime) {
        this.rank = Rank;
        this.number = number;
        this.stopTime = stopTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getStopTime() {
        return stopTime;
    }

    public void setStopTime(int stopTime) {
        this.stopTime = stopTime;
    }

}
