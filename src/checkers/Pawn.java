package checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;
import java.util.LinkedList;


public class Pawn extends Circle {
    private int[] position = new int[2];
    private LinkedList<Move> availableMove = new LinkedList<>();
    private PawnColor color;
    boolean isKing = false;
    public Pawn(Color color,int r,int c) {
        super();
        this.setRadius(45);
        this.setFill(color);
        position[0]=r;
        position[1]=c;
        if(color.equals(Color.BLACK)){
            setColor(PawnColor.BLACK);
        }
        else{
            setColor(PawnColor.RED);
        }
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public LinkedList<Move> getAvailableMove() {
        return availableMove;
    }

    public void setAvailableMove(LinkedList<Move> availableMove) {
        this.availableMove = availableMove;
    }

    public PawnColor getColor() {
        return color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        isKing = king;
    }

    @Override
    public String toString() {
StringBuilder x = new StringBuilder("[");
        availableMove.forEach(e -> x.append("[").append(e.getPlace()[0]).append(",").append(e.getPlace()[1]).append(e.isBeating).append("] "));
        x.append("]");

        return "Pawn{" +
                "position=" + Arrays.toString(position) +
                ", availableMove=" + x +
                ", color=" + color +
                '}';
    }

    public boolean haveBeatingAndRemove(){
        for(Move move:availableMove){
            if(move.isBeating) {
                availableMove.removeIf(m -> !m.isBeating());
                return true;
            }
        }
        return false;
    }
}


