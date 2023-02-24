package views;

import engine.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.world.Champion;

import java.io.IOException;

import static views.Controller.fadeOut;
import static views.Controller.onStartButton;


public class GameOverRoot extends VBox {


    public GameOverRoot(Player player){
        this.setStyle("-fx-background-image: url('views/bg_imgs/LeaderBG.png');");
        Label label = new Label(player.getName().toUpperCase() + " WINS!!");
        label.getStylesheets().add(getClass().getResource("css/game-font.css").toExternalForm());
        label.getStyleClass().add("gameover-font");

        HBox team = new HBox(10);
        team.setAlignment(Pos.CENTER);

        for (Champion c : player.getTeam()) {
            Button champBtn = new Button();
            champBtn.getStylesheets().add(getClass().getResource("css/leader.css").toExternalForm());
            champBtn.getStyleClass().add(c.getName().substring(0,2));
            team.getChildren().add(champBtn);
        }

        Button playAgain = new Button();
        playAgain.getStylesheets().add(this.getClass().getResource("css/replay-btn.css").toExternalForm());

        playAgain.setOnAction(e-> {

            MainFrame mainFrame1 = new MainFrame();
            Controller.mainFrame = mainFrame1;


            mainFrame1.startingRoot = new StartingRoot(10);
            mainFrame1.startingRoot.playButton.setOnAction(f -> onStartButton());

            fadeOut(this, mainFrame1.startingRoot);

        });

        this.setAlignment(Pos.CENTER);
        this.setSpacing(100);
        this.getChildren().addAll(label,team , playAgain);

    }
}
