package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MyRectangle extends VBox {

    Region background;
    Region fill;
    int currVar;

    double maxVar;
    StackPane stackPane;
    Label varValue;
    Label varName;

    HBox hbox;

    public MyRectangle(String nameLabel, int currVar, double maxVar) {
        super();
        //initializing size and style
        this.currVar = currVar;
        this.maxVar = maxVar;
        this.stackPane = new StackPane();
        this.hbox = new HBox(8);
        //this.setAlignment(Pos.CENTER);

        stackPane.setMaxWidth(135);
        stackPane.setPrefWidth(135);
        stackPane.setMaxHeight(7);
        stackPane.setAlignment(Pos.CENTER_LEFT);
        stackPane.setStyle("-fx-background-color: #23AB3D");

        background = new Region();
        background.setMaxSize(135, 7);
        background.setPrefSize(135, 7);
        background.getStylesheets().add(this.getClass().getResource("rectangle.css").toExternalForm());
        background.getStyleClass().add("background");

        fill = new Region();
        fill.setMaxSize(135, 7);
        fill.setPrefSize(135, 7);
        fill.getStylesheets().add(this.getClass().getResource("rectangle.css").toExternalForm());
        fill.getStyleClass().add("fill");
        fill.setAccessibleText("HELLO");

        stackPane.getChildren().add(background);
        stackPane.getChildren().add(fill);

        varValue = new Label(currVar + "");

        varName = new Label(nameLabel);

        hbox.getChildren().add(stackPane);
        hbox.getChildren().add(varValue);
        hbox.setAlignment(Pos.CENTER_LEFT);

//        hbox.setStyle("-fx-background-color: #23AB3D");

        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(varName);
        this.getChildren().add(hbox);

    }

    public MyRectangle() {
        super();

        //initializing size and style
        this.setMaxWidth(135);
        this.setPrefWidth(135);
        this.setPrefHeight(10);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: #11941A");

        background = new Region();
        background.setMaxSize(135, 10);
        background.getStylesheets().add(this.getClass().getResource("rectangle.css").toExternalForm());
        background.getStyleClass().add("background");

        fill = new Region();
        fill.setMaxSize(135, 10);
        fill.getStylesheets().add(this.getClass().getResource("rectangle.css").toExternalForm());
        fill.getStyleClass().add("fill");


        this.getChildren().add(background);
        this.getChildren().add(fill);
    }

    public void setValue(int currVar, double maxVar) {
        this.currVar = currVar;
        this.fill.setMaxWidth((currVar / maxVar) * 135);
        varValue.setText(currVar + "");
    }

}