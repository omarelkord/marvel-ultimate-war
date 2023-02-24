package views;

import engine.Game;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.world.Champion;

import java.util.ArrayList;

import static views.Controller.*;

public class LeaderRoot extends VBox {
    HBox firstTeam;
    HBox secondTeam;
    StackPane leaderAbilityBox;
    ArrayList<Button> firstTeamButtons = new ArrayList<>();
    ArrayList<Button> secondTeamButtons = new ArrayList<>();

    Game game;

    public LeaderRoot(Game game, double v) {

        super(v);

        this.setAlignment(Pos.BOTTOM_CENTER);
        this.setStyle("-fx-background-image: url('views/bg_imgs/LeaderBG.png');");

        firstTeam = new HBox();
        firstTeam.setAlignment(Pos.CENTER);
        secondTeam = new HBox();
        secondTeam.setAlignment(Pos.CENTER);

        leaderAbilityBox = new StackPane();
        leaderAbilityBox.setPrefHeight(110);


        int i = 0;

        for (Champion c : game.getFirstPlayer().getTeam()) {
            Button button = new Button();

            button.getStylesheets().add(this.getClass().getResource("css/leader.css").toExternalForm());
            button.getStyleClass().add(c.getName().substring(0, 2));

            int finalI = i;

            button.setOnMouseEntered(e -> leaderAbilityBox.getChildren().add(new ImageView(new Image("views/gen_imgs/" + getChampionType(c)  + "Ability.png"))));
            button.setOnMouseExited(e -> leaderAbilityBox.getChildren().clear());

            firstTeam.getChildren().add(button);
            firstTeamButtons.add(button);

            button.setOnAction(e -> onSelectLeader(true, finalI));
            i++;
        }

        i = 0;

        for (Champion c : game.getSecondPlayer().getTeam()) {
            Button button = new Button();

            button.getStylesheets().add(this.getClass().getResource("css/leader.css").toExternalForm());
            button.getStyleClass().add(c.getName().substring(0, 2));

            int finalI = i;

            button.setOnMouseEntered(e -> leaderAbilityBox.getChildren().add(new ImageView(new Image("views/gen_imgs/" + getChampionType(c)  + "Ability.png"))));
            button.setOnMouseExited(e -> leaderAbilityBox.getChildren().clear());

            secondTeam.getChildren().add(button);
            secondTeamButtons.add(button);

            button.setOnAction(e -> onSelectLeader(false, finalI));
            i++;
        }

        Label VS = new Label("SELECT YOUR LEADERS");
        VS.getStylesheets().add(BigTest.class.getResource("css/game-font.css").toExternalForm());
        VS.getStyleClass().add("leader-font");



        Label firstPlayerSelect = new Label(mainFrame.game.getFirstPlayer().getName() + ", select your leader!");
        firstPlayerSelect.getStylesheets().add(BigTest.class.getResource("css/game-font.css").toExternalForm());
        firstPlayerSelect.getStyleClass().add("select-leader-font");


        this.getChildren().add(firstPlayerSelect);
        this.getChildren().add(firstTeam);
        this.getChildren().add(VS);

        Label secondPlayerSelect = new Label(mainFrame.game.getSecondPlayer().getName() + ", select your leader!");
        secondPlayerSelect.getStylesheets().add(BigTest.class.getResource("css/game-font.css").toExternalForm());
        secondPlayerSelect.getStyleClass().add("select-leader-font");


        this.getChildren().add(secondPlayerSelect);
        this.getChildren().add(secondTeam);
        this.getChildren().add(leaderAbilityBox);
    }
}
