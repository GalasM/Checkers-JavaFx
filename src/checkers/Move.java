package checkers;

public class Move {
    private int place[];
    public boolean isBeating;
    public int[] beatField;

    public Move(int[] place, boolean isBeating, int[] beatield) {
        this.place = place;
        this.isBeating = isBeating;
        this.beatField = beatield;
    }

    public Move(int[] place, boolean isBeating) {
        this.place = place;
        this.isBeating = isBeating;
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
}
