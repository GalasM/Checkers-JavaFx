package app.checkers;

import java.util.Arrays;
import java.util.LinkedList;

public class Player {
    private PawnColor color;
    private LinkedList<Pawn> pawns;
    private LinkedList<Move> allPlayerMoves;

    public Player(PawnColor color, LinkedList<Pawn> pawns) {
        this.color = color;
        LinkedList<Pawn> list = new LinkedList<>(pawns);
        this.pawns = new LinkedList<>(list);
    }

    public PawnColor getColor() {
        return color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }

    public LinkedList<Pawn> getPawns() {
        return pawns;
    }

    public void setPawns(LinkedList<Pawn> pawns) {
        this.pawns = pawns;
    }

    public LinkedList<Move> getAllPlayerMoves() {
        return allPlayerMoves;
    }

    public void setAllPlayerMoves(LinkedList<Move> allPlayerMoves) {
        this.allPlayerMoves = allPlayerMoves;
    }

    public int removePawn(int[] pos){
            pawns.removeIf(m -> Arrays.equals(m.getPosition(), pos));
        return pawns.size();
    }

    public void removeMovesWithoutBeat(){
        LinkedList<Move> beats = new LinkedList<>();
        for(Move m: allPlayerMoves){
            if(m.isBeating()) {
                beats.add(m);
            }
        }
        if(!beats.isEmpty()){
            allPlayerMoves.clear();
            setAllPlayerMoves(beats);
        }
    }
}
