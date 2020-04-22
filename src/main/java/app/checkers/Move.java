package app.checkers;

import java.util.Arrays;
import java.util.Objects;

public class Move {

    private double weight = 0;

    private int place[];
    public boolean isBeating;
    public int[] beatField;
    private Pawn pawn;

    public Move(int[] place, boolean isBeating, int[] beatField, Pawn pawn) {
        this.place = place;
        this.isBeating = isBeating;
        this.beatField = beatField;
        this.pawn = pawn;
    }

    public Move(int[] place, boolean isBeating, Pawn pawn) {
        this.place = place;
        this.isBeating = isBeating;
        this.pawn = pawn;
    }

    public Move() {
    }

    public int[] getPlace() {
        return place;
    }

    public void setPlace(int[] place) {
        this.place = place;
    }

    public boolean isBeating() {
        return isBeating;
    }

    public void setBeating(boolean beating) {
        isBeating = beating;
    }

    public int[] getBeatField() {
        return beatField;
    }

    public void setBeatField(int[] beatield) {
        this.beatField = beatield;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void increaseWeight(double delta) {
        this.weight += delta;
    }

    public void decreaseWeight(double delta) {
        this.weight -= delta;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    @Override
    public String toString() {
        return "Move{" +
                "weight=" + weight +
                ", place=" + Arrays.toString (place) +
                ", isBeating=" + isBeating +
                ", beatField=" + Arrays.toString (beatField) +
                '}';
    }
}

