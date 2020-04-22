package app.checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Game extends Application {
    private boolean mode;

    Game(boolean mode) {
        this.mode = mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public boolean getMode() {
        return mode;
    }

    public void startGame() {
        Stage primaryStage = new Stage();
        start(primaryStage);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();

        primaryStage.setTitle("Warcaby");
        primaryStage.setScene(new Scene(root, 800, 800));
        print(root);
        primaryStage.show();
    }

    public void print(GridPane root) {
        Board board = new Board(mode);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                root.add(board.getBoard()[i][j], i, j);
            }
        }
    }
}
