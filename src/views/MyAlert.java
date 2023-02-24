package views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;


public class MyAlert extends StackPane {




    public MyAlert(String exception, String msg, String imageName){

        ImageView imageView = new ImageView(new Image("views/gen_imgs/"+imageName+".png"));


        Label exceptionLabel = new Label(exception);

        exceptionLabel.setPadding(new Insets(0,131,140,0));

        exceptionLabel.getStylesheets().add(getClass().getResource("css/game-font.css").toExternalForm());
        exceptionLabel.getStyleClass().add("exception");
        exceptionLabel.setWrapText(true);



        Label msgLabel = new Label(msg);
        msgLabel.getStylesheets().add(getClass().getResource("css/game-font.css").toExternalForm());
        msgLabel.getStyleClass().add("exception-msg");
        msgLabel.setTextAlignment(TextAlignment.CENTER);
        msgLabel.setWrapText(true);



        //style

        //padding insets

        this.getChildren().addAll(imageView, exceptionLabel, msgLabel);



    }
}
