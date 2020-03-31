package checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    private GridPane root = new GridPane();
    private Board board = new Board();

    @Override
    public void start(Stage primaryStage){

        primaryStage.setTitle("Warcaby");
        primaryStage.setScene(new Scene(root, 800, 800));
        print();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void print(){

        for(int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++) {
                root.add(board.getBoard()[i][j],i,j);
            }
        }
    }
}
