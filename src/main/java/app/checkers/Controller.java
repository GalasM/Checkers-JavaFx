package app.checkers;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


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
        this.playerRed = new Player (PawnColor.RED, board.getRedPawns());
        this.playerBlack = new Player (PawnColor.BLACK, board.getBlackPawns());
        setAllMovesForPlayer (playerBlack);
        setAllMovesForPlayer (playerRed);
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


    EventHandler RectEventHandler = new EventHandler() {
        @Override
        public void handle(Event e) {
            Field selected = (Field) e.getSource();
            if(selected.isEmpty())
                resetBoard();
            if (selectedPawn == null) {
                board.addToInfo(new Text("Wybierz pionka"));
            }
            if (selected.isEmpty () && selectedPawn != null && canMove (selectedPawn, selected.getPosition ())) {
                makeMove (selected, selectedPawn);
            }
        }
    };

    EventHandler PawnEventHandler = new EventHandler () {
        @Override
        public void handle(Event e) {
            selectedPawn = (Pawn) e.getSource ();
            if (selectedPawn.isKing) {
                availableMovesForKing (selectedPawn);
            } else {
                availableMoves (selectedPawn);
            }
            resetBoard();
            showMoves (selectedPawn);
        }

    };

    public void availableMoves(Pawn pawn) {
        int[] pos = pawn.getPosition ();
        LinkedList<Move> moves = new LinkedList<> ();
        //RUCHY CZERWONEGO
        if (pawn.getColor ().equals (PawnColor.RED)) {
            //RUCH DOL PRAWO
            if (pos[1] < 7 && pos[0] < 7 && board.getBoard ()[pos[0] + 1][pos[1] + 1].isEmpty ()) {
                addMoveToList (pawn, moves, pos[0] + 1, pos[1] + 1);
            } else {
                //BICIE DOL PRAWO
                if (pos[1] < 6 && (pos[0] < 7 && board.getBoard ()[pos[0] + 1][pos[1] + 1].getPawn ().getColor ().equals (PawnColor.BLACK)) && (pos[0] < 6 && board.getBoard ()[pos[0] + 2][pos[1] + 2].isEmpty ())) {
                    addMoveWithBeatToList (pawn, moves, pos[0] + 2, pos[1] + 2, pos[0] + 1, pos[1] + 1);
                }
            }
            //RUCH DOL LEWO
            if (pos[1] < 7 && pos[0] > 0 && board.getBoard ()[pos[0] - 1][pos[1] + 1].isEmpty ()) {
                addMoveToList (pawn, moves, pos[0] - 1, pos[1] + 1);
            } else {
                //BICIE BICIE DOL LEWO
                if (pos[1] < 6 && (pos[0] > 0 && board.getBoard ()[pos[0] - 1][pos[1] + 1].getPawn ().getColor ().equals (PawnColor.BLACK)) && (pos[0] > 1 && board.getBoard ()[pos[0] - 2][pos[1] + 2].isEmpty ())) {
                    addMoveWithBeatToList (pawn, moves, pos[0] - 2, pos[1] + 2, pos[0] - 1, pos[1] + 1);
                }
            }
        } else {
            //RUCH CZARNEGO
            //RUCH GORA PRAWO
            if (pos[1] > 0 && pos[0] < 7 && board.getBoard ()[pos[0] + 1][pos[1] - 1].isEmpty ()) {
                addMoveToList (pawn, moves, pos[0] + 1, pos[1] - 1);
            } else {
                //BICIE GORA PRAWO
                if (pos[1] > 1 && (pos[0] < 7 && board.getBoard ()[pos[0] + 1][pos[1] - 1].getPawn ().getColor ().equals (PawnColor.RED)) && (pos[0] < 6 && board.getBoard ()[pos[0] + 2][pos[1] - 2].isEmpty ())) {
                    addMoveWithBeatToList (pawn, moves, pos[0] + 2, pos[1] - 2, pos[0] + 1, pos[1] - 1);
                }
            }
            //RUCH GORA LEWO
            if (pos[1] > 0 && pos[0] > 0 && board.getBoard ()[pos[0] - 1][pos[1] - 1].isEmpty ()) {
                addMoveToList (pawn, moves, pos[0] - 1, pos[1] - 1);
            } else {
                //BICIE GORA LEWO
                if (pos[1] > 1 && (pos[0] > 0 && board.getBoard ()[pos[0] - 1][pos[1] - 1].getPawn ().getColor ().equals (PawnColor.RED)) && (pos[0] > 1 && board.getBoard ()[pos[0] - 2][pos[1] - 2].isEmpty ())) {
                    addMoveWithBeatToList (pawn, moves, pos[0] - 2, pos[1] - 2, pos[0] - 1, pos[1] - 1);
                }
            }
        }
        pawn.setAvailableMove (moves);
        pawn.haveBeatingAndRemove ();
    }

    public void availableMovesForKing(Pawn pawn) {
        LinkedList<Move> moves = setMoveKingListFor (pawn);
        pawn.setAvailableMove (moves);
        pawn.haveBeatingAndRemove ();
    }

    LinkedList<Move> setMoveKingListFor(Pawn pawn) {
        PawnColor color = pawn.getColor ();
        PawnColor opponent;
        if (color.equals (PawnColor.RED)) {
            opponent = PawnColor.BLACK;
        } else {
            opponent = PawnColor.RED;
        }
        int[] pos = pawn.getPosition ();
        LinkedList<Move> moves = new LinkedList<> ();
        if (pawn.getColor ().equals (color)) {
            //WIERSZE Z KTORYCH ZACZYNA SIE SZUKANIE RUCHOW
            int x = pos[0];
            int y = pos[1];
            //SPRAWDZENIE PRZEKATNEJ LEWO GORA
            while (x >= 0 && y >= 0) {
                if (board.getBoard ()[x][y].isEmpty ()) {
                    addMoveToList (pawn, moves, x, y);
                } else {
                    if (y > 0 && (x > 0 && board.getBoard ()[x][y].getPawn ().getColor ().equals (opponent)) && board.getBoard ()[x - 1][y - 1].isEmpty ()) {
                        addMoveWithBeatToList (pawn, moves, x - 1, y - 1, x, y);
                        int beatX = x;
                        int beatY = y;
                        x = x - 2;
                        y = y - 2;
                        while (x >= 0 && y >= 0) {
                            if (board.getBoard ()[x][y].isEmpty ()) {
                                addMoveWithBeatToList (pawn, moves, x, y, beatX, beatY);
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
                if (board.getBoard ()[x][y].isEmpty ()) {
                    addMoveToList (pawn, moves, x, y);
                } else {
                    if (y < 7 && (x < 7 && board.getBoard ()[x][y].getPawn ().getColor ().equals (opponent)) && board.getBoard ()[x + 1][y + 1].isEmpty ()) {
                        addMoveWithBeatToList (pawn, moves, x + 1, y + 1, x, y);
                        int beatX = x;
                        int beatY = y;
                        x = x + 2;
                        y = y + 2;
                        while (x <= 7 && y <= 7) {
                            if (board.getBoard ()[x][y].isEmpty ()) {
                                addMoveWithBeatToList (pawn, moves, x, y, beatX, beatY);
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
                if (board.getBoard ()[x][y].isEmpty ()) {
                    addMoveToList (pawn, moves, x, y);
                } else {
                    if (y < 7 && (x > 0 && board.getBoard ()[x][y].getPawn ().getColor ().equals (opponent)) && board.getBoard ()[x - 1][y + 1].isEmpty ()) {
                        addMoveWithBeatToList (pawn, moves, x - 1, y + 1, x, y);
                        int beatX = x;
                        int beatY = y;
                        x = x - 2;
                        y = y + 2;
                        while (x >= 0 && y <= 7) {
                            if (board.getBoard ()[x][y].isEmpty ()) {
                                addMoveWithBeatToList (pawn, moves, x, y, beatX, beatY);
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
                if (board.getBoard ()[x][y].isEmpty ()) {
                    addMoveToList (pawn, moves, x, y);
                } else {
                    //CZY PIONEK JEST PRZECIWNIKIEM I CZY NASTEPNE POLE JEST WOLE
                    if (y > 0 && (x < 7 && board.getBoard ()[x][y].getPawn ().getColor ().equals (opponent)) && board.getBoard ()[x + 1][y - 1].isEmpty ()) {
                        addMoveWithBeatToList (pawn, moves, x + 1, y - 1, x, y);
                        int beatX = x;
                        int beatY = y;
                        x = x + 2;
                        y = y - 2;

                        while (x <= 7 && y >= 0) {
                            if (board.getBoard ()[x][y].isEmpty ()) {
                                addMoveWithBeatToList (pawn, moves, x, y, beatX, beatY);
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


    public void addMoveToList(Pawn pawn, LinkedList<Move> list, int x, int y) {
        int[] m = new int[2];
        m[0] = x;
        m[1] = y;
        Move mv = new Move (m, false,pawn);
        list.add (mv);
    }

    public void addMoveWithBeatToList(Pawn pawn, LinkedList<Move> list, int x, int y, int x2, int y2) {
        int[] m = new int[2];
        m[0] = x;
        m[1] = y;
        int[] beatField = new int[2];
        beatField[0] = x2;
        beatField[1] = y2;
        list.add (new Move (m, true, beatField,pawn));
    }

    public void showMoves(Pawn pawn) {
        pawn.getAvailableMove ().forEach (r -> board.getBoard()[r.getPlace()[0]][r.getPlace()[1]].getRectangle().setFill(Color.ALICEBLUE));
    }
    public void resetBoard(){
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board.getBoard()[row][col].getRectangle().getFill().equals(Color.ALICEBLUE)) {
                    board.getBoard()[row][col].getRectangle().setFill(Color.LIGHTGRAY);
                }
            }
        }
    }

    public boolean canMove(Pawn pawn, int[] field) {
        AtomicBoolean can = new AtomicBoolean (false);
        pawn.getAvailableMove().forEach(e -> {
            if (Arrays.equals(e.getPlace(), field)) {
                currentPlayer.getAllPlayerMoves().forEach (move -> {
                    if (Arrays.equals (move.getPlace(), field))
                        can.set(true);
                });
            }
        });
        return can.get();
    }

    public void makeMove(Field field, Pawn pawn) {
        AtomicBoolean isNewKing = new AtomicBoolean(false);
        if (currentPlayer.getColor ().equals (pawn.getColor ())) {
            currentPlayer.getAllPlayerMoves().forEach(m -> {
                if(Arrays.equals(m.getPlace(), field.getPosition()) && m.getPawn().equals(pawn)){
                    board.addToInfo(new Text("Ruszono z: "+pawn.getPosition ()[0] + "," + pawn.getPosition ()[1]+ " na: "+field.getPosition ()[0] + "," + field.getPosition ()[1]));
                    board.getBoard ()[pawn.getPosition ()[0]][pawn.getPosition ()[1]].setEmpty (true);
                    field.setPawn (pawn);
                    selectedPawn = null;
                    if (pawn.getColor ().equals (PawnColor.RED) && field.getPosition ()[1] == 7 && !pawn.isKing) {
                        pawn.setKing (true);
                        isNewKing.set(true);
                    } else if (pawn.getColor ().equals (PawnColor.BLACK) && field.getPosition ()[1] == 0 && !pawn.isKing) {
                        pawn.setKing (true);
                        isNewKing.set(true);
                    }
                    LinkedList<Move> moves = pawn.getAvailableMove ();
                    for (Move tmp : moves) {
                        if (Arrays.equals (tmp.getPlace (), field.getPosition ())) {
                            if (tmp.isBeating) {
                                board.getBoard ()[tmp.getBeatField ()[0]][tmp.getBeatField ()[1]].setPawn (null);
                                if (pawn.getColor ().equals (PawnColor.RED)) {
                                    if (playerBlack.removePawn (tmp.beatField) == 0) {
                                        finishGame(PawnColor.RED);
                                    }

                                    if (pawn.isKing ())
                                        availableMovesForKing (pawn);
                                    else
                                        availableMoves (pawn);
                                    if (pawn.haveBeatingAndRemove () && !isNewKing.get()) {
                                        swapPlayer (true);
                                    }

                                } else {
                                    if (playerRed.removePawn (tmp.beatField) == 0) {
                                        finishGame(PawnColor.BLACK);
                                    }
                                    if (pawn.isKing ())
                                        availableMovesForKing (pawn);
                                    else
                                        availableMoves (pawn);
                                    if (pawn.haveBeatingAndRemove () && !isNewKing.get()) {
                                        swapPlayer (true);
                                    }
                                }
                            }
                            if(isNewKing.get()){
                                swapPlayer(false);
                            }
                        }
                    }
                    if(!isNewKing.get())
                    swapPlayer (false);
                }
            });

        }
    }

    private void finishGame(PawnColor color) {
            if (color.equals(PawnColor.RED)) {
                board.addToInfo(new Text("WYGRAL CZERWONY"));
                Alert alert = new Alert(Alert.AlertType.NONE, "WYGRAL CZERWONY", ButtonType.OK);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                    System.out.println("Wygral czerwony");
                    Platform.exit();
                    System.exit(0);
                }
            }

        else {
            board.addToInfo(new Text("WYGRAL CZARNY"));
            Alert alert = new Alert(Alert.AlertType.NONE, "WYGRAL CZARNY", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                alert.close();
                System.out.println("Wygral czarny");
                Platform.exit();
                System.exit(0);
            }
        }
    }

    public void swapPlayer(boolean isBeat) {
        if (currentPlayer.getColor ().equals (PawnColor.RED)) {
            if(!isBeat)
                board.changePlayer(currentPlayer.getColor());
            currentPlayer = playerBlack;
            setAllMovesForPlayer (playerBlack);
            if(playerBlack.getAllPlayerMoves().isEmpty()){
                finishGame(PawnColor.RED);
            }
//            call to CPU move function
            if (board.isPlayAgainstComputer () && !isBeat) {
                makeAIMove(playerBlack);
            }

        } else {
            if(!isBeat)
                board.changePlayer(currentPlayer.getColor());
            currentPlayer = playerRed;
            setAllMovesForPlayer (playerRed);
            if(playerRed.getAllPlayerMoves().isEmpty()){
                finishGame(PawnColor.BLACK);
            }
        }
    }

    private void makeAIMove(Player player) {
        double highestWeight = -50;
        Move moveToMake = null;


            for (Move move : player.getAllPlayerMoves()) {
                int[] movePlace = move.getPlace ();
                Pawn pawnCopy = new Pawn (Color.BLACK, movePlace[0], movePlace[1]);
                calculateMoveWeight (pawnCopy, move);
                if (move.getWeight () > highestWeight) {
                    moveToMake = move;
                    highestWeight = moveToMake.getWeight ();
                }
            }

            for (Move move : player.getAllPlayerMoves()) {
                int[] place = move.getPlace ();
                if (canMove (move.getPawn(), place)) {
                if (move.isBeating){ // if move is beating it has to be executed
                    executeMoveForPawn(move, move.getPawn());
                    return;
                }else if (move.equals(moveToMake)) { //if not beating we want to execute move with highest weight
                    executeMoveForPawn(move, move.getPawn());
                    return;
                    }
                }
            }

    }
    private void executeMoveForPawn(Move move, Pawn pawn){
        int[] place = move.getPlace();
        Field[][] fields = this.board.getBoard ();
        for (int row = 0; row < fields.length; row++) {
            for (int col = 0; col < fields[row].length; col++) {
                if (row == place[0] && col == place[1]) {
                    makeMove (fields[row][col], pawn);
                }
            }
        }
    }

    private void calculateMoveWeight(Pawn pawnCopy, Move originalMove) {
        setAllMovesForPawn(pawnCopy);
        LinkedList<Move> availableMovesInFuture = pawnCopy.getAvailableMove ();
        for (Move moveInFuture : availableMovesInFuture) {
            if (moveInFuture.isBeating) {
                if (isSafe(pawnCopy)){
                    originalMove.increaseWeight(20);
                }else{
                    originalMove.decreaseWeight (10);
                }
            }else{
                if (isSafe(pawnCopy)){
                    originalMove.increaseWeight(5);
                }
            }
        }
    }
    private boolean isSafe(Pawn pawnCopy){
        int[] positionAfterMove = pawnCopy.getPosition();
        return (positionAfterMove[0] == 0 || positionAfterMove[0] == 7);
    }


    public void setAllMovesForPlayer(Player player) {
        LinkedList<Move> playerMoves = new LinkedList<> ();
        for (Pawn p : player.getPawns ()) {
            setAllMovesForPawn(p);
            playerMoves.addAll (p.getAvailableMove ());
        }
        player.setAllPlayerMoves (playerMoves);
        player.removeMovesWithoutBeat ();
    }
    private void setAllMovesForPawn(Pawn p){
        if (p.isKing ()) {
            availableMovesForKing (p);
            p.haveBeatingAndRemove ();
        } else {
            availableMoves (p);
            p.haveBeatingAndRemove ();
        }
    }
}
