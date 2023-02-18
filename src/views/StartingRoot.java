package views;

import engine.Game;
import engine.Player;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class StartingRoot extends VBox {

    Player firstPlayer;
    Player secondPlayer;
    Game game;


    Button playButton;

    TextField text1;
    TextField text2;
    VBox vbox;


    public StartingRoot(double v) {
        super(v);
        this.setStyle("-fx-background-image: url('views/superwar5.jpg');");
        this.setAlignment(Pos.CENTER);

        text1 = new TextField();
        text1.setPromptText("Enter Name");
        text1.setPrefSize(160, 30);

        text2 = new TextField();
        text2.setPromptText("Enter Name");
        text2.setPrefSize(160, 30);

        Label label1 = new Label("PLAYER 1:");
//label1.setFont(new Font("Consolas", 20));
        label1.getStylesheets().add(this.getClass().getResource("label.css").toExternalForm());
        label1.getStyleClass().add("outline");
//label1.setTextFill(Color.web("white"));

        Label label2 = new Label("PLAYER 2:");
//label2.setFont(new Font("Consolas", 20));
        label2.getStylesheets().add(this.getClass().getResource("label.css").toExternalForm());
        label2.getStyleClass().add("outline");
//label2.setTextFill(Color.web("white"));


        HBox hbox1 = new HBox(10);
        HBox hbox2 = new HBox(10);


        hbox1.getChildren().addAll(label1, text1);
        hbox2.getChildren().addAll(label2, text2);

        this.getChildren().add(hbox1);
        this.getChildren().add(hbox2);

        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);

        playButton = new Button();
        playButton.getStylesheets().add(this.getClass().getResource("trial.css").toExternalForm());
        playButton.getStyleClass().add("button1");

//        playButton.setPrefSize(50, 30);

        this.getChildren().add(playButton);
    }

}

