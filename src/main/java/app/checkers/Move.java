package app.checkers;

import java.util.Arrays;
import java.util.Objects;

public class Move {

    private double weight = 0;

    private int place[];
    public boolean isBeating;
    public int[] beatField;

    public Move(int[] place, boolean isBeating, int[] beatField) {
        this.place = place;
        this.isBeating = isBeating;
        this.beatField = beatField;
    }

    public Move(int[] place, boolean isBeating) {
        this.place = place;
        this.isBeating = isBeating;
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


    @Override
    public String toString() {
        return "Move{" +
                "weight=" + weight +
                ", place=" + Arrays.toString (place) +
                ", isBeating=" + isBeating +
                ", beatField=" + Arrays.toString (beatField) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return Double.compare(move.weight, weight) == 0 &&
                isBeating == move.isBeating &&
                Arrays.equals(place, move.place) &&
                Arrays.equals(beatField, move.beatField);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(weight, isBeating);
        result = 31 * result + Arrays.hashCode(place);
        result = 31 * result + Arrays.hashCode(beatField);
        return result;
    }
}

