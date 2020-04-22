package app.checkers;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
        HBox root = new HBox();
        GridPane grid = new GridPane();

        primaryStage.setTitle("Warcaby");
        primaryStage.setScene(new Scene(root, 950, 800));
        print(grid,root);
        primaryStage.show();
    }

    public void print(GridPane grid,Pane root) {
        Board board = new Board(mode);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                grid.add(board.getBoard()[i][j], i, j);
            }
        }
        root.getChildren().add(grid);
        ScrollPane sp = new ScrollPane(board.getInfo());
        sp.setPrefWidth(150);
        root.getChildren().add(sp);
    }
}
