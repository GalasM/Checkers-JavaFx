package checkers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller {
    private Board board;
    private Pawn selectedPawn;
    private Player playerRed;
    private Player playerBlack;
    private Player currentPlayer;
    public Controller(Board b) {
        this.board = b;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
        this.playerRed = new Player(PawnColor.RED,board.getRedPawns());
        this.playerBlack = new Player(PawnColor.BLACK,board.getBlackPawns());
        setAllMovesForPlayer(playerBlack);
        setAllMovesForPlayer(playerRed);
        this.currentPlayer = playerRed;
    }

    public Player getPlayerRed() {
        return playerRed;
    }

    public void setPlayerRed(Player playerRed) {
        this.playerRed = playerRed;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public void setPlayerBlack(Player playerBlack) {
        this.playerBlack = playerBlack;
    }

    EventHandler<MouseEvent> RectEventHandler = new EventHandler<>() {
        @Override
        public void handle(MouseEvent e) {
            Field selected = (Field)e.getSource();
            if(selectedPawn==null){
                System.out.println("wybierz pionka");
            }
            if(selected.isEmpty() && selectedPawn!=null && canMove(selectedPawn,selected.getPosition())){
                makeMove(selected,selectedPawn);
            }
            System.out.println(selected.getPosition()[0]+" "+selected.getPosition()[1]);
        }
    };

    EventHandler<MouseEvent> PawnEventHandler = new EventHandler<>() {
        @Override
        public void handle(MouseEvent e) {
            selectedPawn = (Pawn)e.getSource();
            if(selectedPawn.isKing) {
                availableMovesForKing(selectedPawn);
            }
            else{
                availableMoves(selectedPawn);
            }
            showMoves(selectedPawn);
        }
    };
    public void availableMoves(Pawn pawn){
        int[] pos = pawn.getPosition();
        LinkedList<Move> moves = new LinkedList<>();
        //RUCHY CZERWONEGO
        if(pawn.getColor().equals(PawnColor.RED)) {
            //RUCH DOL PRAWO
            if (pos[1] < 7 && pos[0] < 7 && board.getBoard()[pos[0] + 1][pos[1] + 1].isEmpty()) {
                addMoveToList(moves,pos[0] + 1,pos[1] + 1);
            }
            else {
                //BICIE DOL PRAWO
                if(pos[1] < 6 && (pos[0] < 7 && board.getBoard()[pos[0] + 1][pos[1] + 1].getPawn().getColor().equals(PawnColor.BLACK)) && (pos[0] < 6 && board.getBoard()[pos[0] + 2][pos[1] + 2].isEmpty())){
                    addMoveWithBeatToList(moves, pos[0] + 2,pos[1] + 2, pos[0] + 1,pos[1] + 1);
                }
            }
            //RUCH DOL LEWO
            if (pos[1] < 7 &&  pos[0] > 0 && board.getBoard()[pos[0] - 1][pos[1] + 1].isEmpty()) {
                addMoveToList(moves,pos[0] - 1,pos[1] + 1);
            }
            else {
                //BICIE BICIE DOL LEWO
                if(pos[1] < 6 && (pos[0] > 0 && board.getBoard()[pos[0] - 1][pos[1] + 1].getPawn().getColor().equals(PawnColor.BLACK)) && (pos[0] > 1 && board.getBoard()[pos[0] - 2][pos[1] + 2].isEmpty())){
                    addMoveWithBeatToList(moves, pos[0] - 2,pos[1] + 2, pos[0] - 1,pos[1] + 1);
                }
            }
        }
        else{
            //RUCH CZARNEGO
            //RUCH GORA PRAWO
            if (pos[1] > 0 && pos[0] < 7 && board.getBoard()[pos[0] + 1][pos[1] - 1].isEmpty()) {
                addMoveToList(moves,pos[0] + 1,pos[1] - 1);
            }
            else {
                //BICIE GORA PRAWO
                if(pos[1] > 1 && (pos[0] < 7 &&board.getBoard()[pos[0] + 1][pos[1] - 1].getPawn().getColor().equals(PawnColor.RED)) && (pos[0] < 6 && board.getBoard()[pos[0] + 2][pos[1] - 2].isEmpty())){
                    addMoveWithBeatToList(moves, pos[0] + 2,pos[1] - 2, pos[0] + 1,pos[1] - 1);
                }
            }
            //RUCH GORA LEWO
            if (pos[1] > 0 && pos[0] > 0 && board.getBoard()[pos[0] - 1][pos[1] - 1].isEmpty()) {
                addMoveToList(moves,pos[0]-1,pos[1] - 1);
            }
            else {
                //BICIE GORA LEWO
                if(pos[1] > 1 && (pos[0] > 0 && board.getBoard()[pos[0] - 1][pos[1] - 1].getPawn().getColor().equals(PawnColor.RED)) && (pos[0] > 1 && board.getBoard()[pos[0] - 2][pos[1] - 2].isEmpty())){
                    addMoveWithBeatToList(moves, pos[0] - 2,pos[1] - 2, pos[0] - 1,pos[1] - 1);
                }
            }
        }
        pawn.setAvailableMove(moves);
        pawn.haveBeatingAndRemove();
       // System.out.println(pawn.toString());
    }

    public void availableMovesForKing(Pawn pawn){
        LinkedList<Move> moves = setMoveKingListFor(pawn);
        pawn.setAvailableMove(moves);
        pawn.haveBeatingAndRemove();
        //System.out.println(pawn.toString());
    }

    LinkedList<Move> setMoveKingListFor(Pawn pawn){
        PawnColor color = pawn.getColor();
        PawnColor  opponent;
        if (color.equals(PawnColor.RED)) {
            opponent = PawnColor.BLACK;
        }
        else{
            opponent = PawnColor.RED;
        }
        int[] pos = pawn.getPosition();
        LinkedList<Move> moves = new LinkedList<>();
        if(pawn.getColor().equals(color)) {
            //WIERSZE Z KTORYCH ZACZYNA SIE SZUKANIE RUCHOW
            int x = pos[0];
            int y = pos[1];
            //SPRAWDZENIE PRZEKATNEJ LEWO GORA
            while (x >= 0 && y >= 0) {
                if (board.getBoard()[x][y].isEmpty()) {
                    addMoveToList(moves, x, y);
                } else {
                    if (y > 0 && (x > 0 && board.getBoard()[x][y].getPawn().getColor().equals(opponent)) && board.getBoard()[x - 1][y - 1].isEmpty()) {
                        addMoveWithBeatToList(moves, x - 1, y - 1, x, y);
                        int beatX = x;
                        int beatY = y;
                        x = x - 2;
                        y = y - 2;
                        while (x >= 0 && y >= 0) {
                            if (board.getBoard()[x][y].isEmpty()) {
                                addMoveWithBeatToList(moves, x, y, beatX, beatY);
                            } else {
                                break;
                            }
                            x--;
                            y--;
                        }
                        break;
                    } else {
                        if (x != pos[0] && y != pos[1])
                            break;
                    }
                }
                x--;
                y--;
            }
            x = pos[0];
            y = pos[1];
            //SPRAWDZENIE PRZEKATNEJ PRAWO DOL
            while (x <= 7 && y <= 7) {
                if (board.getBoard()[x][y].isEmpty()) {
                    addMoveToList(moves, x, y);
                } else {
                    if (y < 7 && (x < 7 && board.getBoard()[x][y].getPawn().getColor().equals(opponent)) && board.getBoard()[x + 1][y + 1].isEmpty()) {
                        addMoveWithBeatToList(moves, x + 1, y + 1, x, y);
                        int beatX = x;
                        int beatY = y;
                        x = x + 2;
                        y = y + 2;
                        while (x <= 7 && y <= 7) {
                            if (board.getBoard()[x][y].isEmpty()) {
                                addMoveWithBeatToList(moves, x, y, beatX, beatY);
                            } else {
                                break;
                            }
                            x++;
                            y++;
                        }
                        break;
                    } else {
                        if (x != pos[0] && y != pos[1])
                            break;
                    }
                }
                x++;
                y++;
            }
            x = pos[0];
            y = pos[1];
            //SPRAWDZENIE PRZEKATNEJ LEWO DOL
            while (x >= 0 && y <= 7) {
                if (board.getBoard()[x][y].isEmpty()) {
                    addMoveToList(moves, x, y);
                } else {
                    if (y < 7 && (x > 0 && board.getBoard()[x][y].getPawn().getColor().equals(opponent)) && board.getBoard()[x - 1][y + 1].isEmpty()) {
                        addMoveWithBeatToList(moves, x - 1, y + 1, x, y);
                        int beatX = x;
                        int beatY = y;
                        x = x - 2;
                        y = y + 2;
                        while (x >= 0 && y <= 7) {
                            if (board.getBoard()[x][y].isEmpty()) {
                                addMoveWithBeatToList(moves, x, y, beatX, beatY);
                            } else {
                                break;
                            }
                            x--;
                            y++;
                        }
                        break;
                    } else {
                        if (x != pos[0] && y != pos[1])
                            break;
                    }
                }
                x--;
                y++;
            }

            x = pos[0];
            y = pos[1];
            //SPRAWDZENIE PRZEKATNEJ PRAWO GORA
            while (x <= 7 && y >= 0) {
                if (board.getBoard()[x][y].isEmpty()) {
                    addMoveToList(moves, x, y);
                } else {
                    //CZY PIONEK JEST PRZECIWNIKIEM I CZY NASTEPNE POLE JEST WOLE
                    if (y > 0 && (x < 7 && board.getBoard()[x][y].getPawn().getColor().equals(opponent)) && board.getBoard()[x + 1][y - 1].isEmpty()) {
                        addMoveWithBeatToList(moves, x + 1, y - 1, x, y);
                        int beatX = x;
                        int beatY = y;
                        x = x + 2;
                        y = y - 2;

                        while (x <= 7 && y >= 0) {
                            if (board.getBoard()[x][y].isEmpty()) {
                                addMoveWithBeatToList(moves, x, y, beatX, beatY);
                            } else {
                                break;
                            }
                            x++;
                            y--;
                        }
                        break;
                    } else {
                        if (x != pos[0] && y != pos[1])
                            break;
                    }
                }
                x++;
                y--;
            }
        }
        return moves;
    }


    public void addMoveToList(LinkedList<Move> list, int x,int y){
        int[] m = new int[2];
        m[0] = x;
        m[1] = y;
        Move mv = new Move(m,false);
        list.add(mv);
    }
    public void addMoveWithBeatToList(LinkedList<Move> list, int x,int y, int x2, int y2){
        int[] m = new int[2];
        m[0] = x;
        m[1] = y;
        int[] beatField = new int[2];
        beatField[0] = x2;
        beatField[1] = y2;
        list.add(new Move(m,true, beatField));
    }

    public void showMoves(Pawn pawn){
        pawn.getAvailableMove().forEach(r ->{
            //board.getBoard()[r[0]][r[1]].getRectangle().setFill(Color.ALICEBLUE);
        });
    }

    public boolean canMove(Pawn pawn, int[] field){
        AtomicBoolean can = new AtomicBoolean(false);
            pawn.getAvailableMove().forEach(e ->{
                if(Arrays.equals(e.getPlace(), field)){
                    currentPlayer.getAllPlayerMoves().forEach(move -> {
                        if(Arrays.equals(move.getPlace(), field))
                            can.set(true);
                    });

                }
            });
        return can.get();
    }

    public void makeMove(Field field,Pawn pawn){
        if(currentPlayer.getColor().equals(pawn.getColor())) {
            System.out.println("black: " + playerBlack.getPawns().size() + " red: " + playerRed.getPawns().size());
            board.getBoard()[pawn.getPosition()[0]][pawn.getPosition()[1]].setEmpty(true);
            field.setPawn(selectedPawn);
            selectedPawn = null;
            if(pawn.getColor().equals(PawnColor.RED) && field.getPosition()[1]==7){
                pawn.setKing(true);
                System.out.println("Mamy kinga");
                field.addKing();
            }
            else if(pawn.getColor().equals(PawnColor.BLACK) && field.getPosition()[1]==0){
                pawn.setKing(true);
                System.out.println("Mamy kinga");
                field.addKing();
            }
            LinkedList<Move> moves = pawn.getAvailableMove();
            for (Move tmp : moves) {
                if (Arrays.equals(tmp.getPlace(), field.getPosition())) {
                    if (tmp.isBeating) {
                        board.getBoard()[tmp.getBeatField()[0]][tmp.getBeatField()[1]].setPawn(null);
                        if (pawn.getColor().equals(PawnColor.RED)) {
                            if (playerBlack.removePawn(tmp.beatField) == 0) {
                                System.out.println("WYGRAL CZERWONY");
                            }

                            if(pawn.isKing())
                                availableMovesForKing(pawn);
                            else
                            availableMoves(pawn);
                            if(pawn.haveBeatingAndRemove()){
                                swapPlayer();
                            }

                        } else {
                            if (playerRed.removePawn(tmp.beatField) == 0) {
                                System.out.println("WYGRAL CZARNY");
                            }
                            if(pawn.isKing())
                                availableMovesForKing(pawn);
                            else
                                availableMoves(pawn);
                            if(pawn.haveBeatingAndRemove()){
                                swapPlayer();
                            }
                        }
                    }
                }
            }

            swapPlayer();
        }
    }
    public void swapPlayer(){
        if(currentPlayer.getColor().equals(PawnColor.RED)){
            currentPlayer = playerBlack;
            setAllMovesForPlayer(playerBlack);
            System.out.println("Tura CZARNYCH");
        }
        else {
            currentPlayer = playerRed;
            setAllMovesForPlayer(playerRed);
            System.out.println("Tura CZERWONYCH");
        }
    }

    public void setAllMovesForPlayer(Player player){
        LinkedList<Move> playerMoves = new LinkedList<>();
        for(Pawn p:player.getPawns()){
            if(p.isKing()) {
                availableMovesForKing(p);
                p.haveBeatingAndRemove();
            }
            else {
                availableMoves(p);
                p.haveBeatingAndRemove();
            }
            playerMoves.addAll(p.getAvailableMove());
        }
        player.setAllPlayerMoves(playerMoves);
        player.removeMovesWithoutBeat();
    }
}
