package views;

import engine.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class GameOverRoot extends VBox {

    Player player;

    //testhbh
    public GameOverRoot(Player player){
        this.player = player;
        this.setStyle("-fx-background-image: url('views/gradient.jpg');");
        Button b = new Button();
        b.getStylesheets().add(this.getClass().getResource("gameover.css").toExternalForm());
        Label label = new Label(player.getName() + " WON");
        label.setFont(new Font("Fantasy",45));
        label.setTextFill(Color.WHITESMOKE);
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(b,label);
    }
}
