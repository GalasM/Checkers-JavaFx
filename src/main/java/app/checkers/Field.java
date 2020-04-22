package app.checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Arrays;

public class Field  extends StackPane {
    private int[] position = new int[2];
    private boolean isEmpty;
    private Rectangle rectangle;
    private Pawn pawn;

    public Field(Pawn pawn) {
        this.rectangle = new Rectangle(100,100);
        this.pawn = pawn;
    }


    public Field(){
        this.rectangle = new Rectangle(100,100);
        this.getChildren().add(rectangle);
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int r,int c) {
        this.position[0] = r;
        this.position[1] = c;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        if(pawn==null){
            this.getChildren().remove(this.pawn);
            this.pawn = pawn;
            this.setEmpty(true);
        }
        else {
            this.pawn = pawn;
            this.getChildren().add(this.pawn);
            this.pawn.setPosition(position);
            this.setEmpty(false);
        }
    }


    @Override
    public String toString() {
        return "Field{" +
                "position=" + Arrays.toString (position) +
                ", isEmpty=" + isEmpty +
                ", pawn=" + pawn +
                '}';
    }
}
