package checkers;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


import java.util.LinkedList;

public class Board {
    private Field[][] board = new Field[8][8];
    private Controller controller;
    private LinkedList<Pawn> redPawns = new LinkedList<>();
    private LinkedList<Pawn> blackPawns = new LinkedList<>();

    public Board() {
        this.controller = new Controller(this);
        for(int i=0;i<8;i++){
            for(int j = 0; j<8; j++){
                if(i%2==0){
                    if(j%2==0){
                        board[i][j] = new Field();
                        board[i][j].setPosition(i,j);
                        board[i][j].getRectangle().setFill(Color.WHITE);
                        board[i][j].setEmpty(true);
                        board[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, controller.RectEventHandler);
                    }
                    else {
                        board[i][j] = new Field();
                        board[i][j].setPosition(i,j);
                        board[i][j].getRectangle().setFill(Color.LIGHTGRAY);
                        board[i][j].setEmpty(true);
                        board[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, controller.RectEventHandler);
                    }
                }
                else {
                    if(j%2==0){
                        board[i][j] = new Field();
                        board[i][j].setPosition(i,j);
                        board[i][j].getRectangle().setFill(Color.LIGHTGRAY);
                        board[i][j].setEmpty(true);
                        board[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, controller.RectEventHandler);

                    }
                    else {
                        board[i][j] = new Field();
                        board[i][j].setPosition(i,j);
                        board[i][j].getRectangle().setFill(Color.WHITE);
                        board[i][j].setEmpty(true);
                        board[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, controller.RectEventHandler);
                    }
                }
            }
        }
        addPawns();
        controller.setBoard(this);
    }

    public Field[][] getBoard() {
        return board;
    }

    public void setBoard(Field[][] board) {
        this.board = board;
    }

    public LinkedList<Pawn> getRedPawns() {
        return redPawns;
    }

    public void setRedPawns(LinkedList<Pawn> redPawns) {
        this.redPawns = redPawns;
    }

    public LinkedList<Pawn> getBlackPawns() {
        return blackPawns;
    }

    public void setBlackPawns(LinkedList<Pawn> blackPawns) {
        this.blackPawns = blackPawns;
    }

    private void addPawns(){
        for(int i=0;i<8;i++){
            for(int j=0;j<3;j++){
                if(board[i][j].getRectangle().getFill().equals(Color.LIGHTGRAY)){
                    board[i][j].setEmpty(false);
                    Pawn p = new Pawn(Color.RED,i,j);
                    board[i][j].setPawn(p);
                    board[i][j].getPawn().addEventHandler(MouseEvent.MOUSE_CLICKED, controller.PawnEventHandler);
                    redPawns.add(p);
                }
            }
        }

        for(int i=0;i<8;i++){
            for(int j=5;j<8;j++){
                if(board[i][j].getRectangle().getFill().equals(Color.LIGHTGRAY)){
                    board[i][j].setEmpty(false);
                    Pawn p = new Pawn(Color.BLACK,i,j);
                    board[i][j].setPawn(p);
                    board[i][j].getPawn().addEventHandler(MouseEvent.MOUSE_CLICKED, controller.PawnEventHandler);
                    blackPawns.add(p);
                }
            }
        }
    }


}
