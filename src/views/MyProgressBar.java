package views;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MyProgressBar extends VBox {

    public MyProgressBar (String title , int currVar, double maxVar){

        ProgressBar bar = new ProgressBar(currVar/maxVar);
        bar.setPrefSize(250,12);

        HBox hbox = new HBox(10);

        Text titleText = new Text(title);
        Text currText = new Text(currVar + "");
        currText.setStyle("-fx-font-size : 15px;");


        hbox.setAlignment(Pos.CENTER_LEFT);

        hbox.getChildren().addAll(bar,currText);
        this.getChildren().addAll(titleText, hbox);


    }
}
