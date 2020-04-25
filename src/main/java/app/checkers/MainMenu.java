package app.checkers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu extends Application {
    public boolean option = true;

    public void setOption(boolean option) {
        this.option = option;
    }

    public boolean getOption() {
        return option;
    }

    public void renderMainMenu(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage menu) {
        menu.setTitle("Main menu");

        VBox vb = new VBox();
        vb.setPadding(new Insets(100, 100, 100, 100));
        vb.setSpacing(15);

        final Text header = new Text("MAIN MENU");
        header.setFill(Color.BLACK);
        header.setFont(Font.font(java.awt.Font.SERIF, 35));
        vb.getChildren().add(header);

        Button vsCPU = new Button();
        vsCPU.setText("PLAYER VS. CPU");
        vsCPU.setMinWidth(200);
        vsCPU.setMinHeight(40);
        vb.getChildren().add(vsCPU);

        Button vsPlayer = new Button();
        vsPlayer.setText("PLAYER VS. PLAYER");
        vsPlayer.setMinWidth(200);
        vsPlayer.setMinHeight(40);
        vb.getChildren().add(vsPlayer);

        menu.setScene(new Scene(vb, 400, 400, Color.LIGHTGRAY));
        menu.show();

        vsCPU.setOnAction(e -> {
            System.out.printf("Game with CPU!");
            setOption(true);
            menu.close();
            startGame(getOption());
        });

        vsPlayer.setOnAction(e -> {
            System.out.printf("Game with Player!");
            setOption(false);
            menu.close();
            startGame(getOption());
        });
    }

    public void startGame(boolean mode) {
        Game game = new Game(mode);
        game.startGame();
    }
}

