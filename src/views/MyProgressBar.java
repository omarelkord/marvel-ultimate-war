package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MyProgressBar extends VBox {

    public MyProgressBar (String title , int currVar, double maxVar){

        ProgressBar bar = new ProgressBar(currVar/maxVar);
//        bar.setPrefSize(230,4);
//        bar.getStylesheets().add(this.getClass().getResource("css/bar-colors.css").toExternalForm());
//        bar.getStyleClass().add("shiny-white");

        HBox hbox = new HBox(10);
//        hbox.setPrefWidth(1000);
//        hbox.setMaxWidth(1000);


        Label titleText = new Label(title);
//        titleText.setMaxWidth(50);

        Label currText = new Label(currVar + "");
//        currText.setMaxWidth(50);


        titleText.getStylesheets().add(BigTest.class.getResource("css/game-font.css").toExternalForm());
        titleText.getStyleClass().add("white-popup");

        currText.getStylesheets().add(BigTest.class.getResource("css/game-font.css").toExternalForm());
        currText.getStyleClass().add("white-popup-curr");

        hbox.setAlignment(Pos.CENTER_LEFT);

        hbox.getChildren().addAll(bar,currText);
        this.getChildren().addAll(titleText, hbox);


    }
}