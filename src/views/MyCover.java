package views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

public class MyCover extends VBox {

    public MyCover(int currValue, double maxValue){
        super();

        this.setAlignment(Pos.CENTER);
        this.setMaxSize(500,500);

        ProgressBar hpBar = new ProgressBar(currValue/maxValue);
        hpBar.setPrefSize(85,12);
        hpBar.getStylesheets().add(getClass().getResource("progress.css").toExternalForm());
        hpBar.getStyleClass().add("green-bar");

        hpBar.setOpacity(0);

        Button button = new Button();

        button.getStylesheets().add(this.getClass().getResource("cover.css").toExternalForm());
        button.getStyleClass().add("snow");

        button.setOnMouseEntered(e->{
            hpBar.setOpacity(1);
        });

        button.setOnMouseExited(e -> {
            hpBar.setOpacity(0);
        });

        this.getChildren().addAll(hpBar, button);
    }
}
