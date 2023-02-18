package views;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class MyCircle extends VBox {

    Circle background;
    Circle fill;
    Label valueLabel;
    Label nameLabel;
    StackPane stackPane;

    public MyCircle(String name, int value) {

        stackPane = new StackPane();

        background = new Circle(20, Color.BLACK);
        fill = new Circle(18, Color.LIGHTBLUE);

        valueLabel = new Label(value + "");

        valueLabel.setFont(new Font("Arial", 14));

        nameLabel = new Label(name + "");
        nameLabel.setFont(new Font("Arial", 15));
        nameLabel.setStyle("-fx-font: normal bold 14 Langdon;" + "-fx-text-fill: white;");


        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(background);
        stackPane.getChildren().add(fill);
        stackPane.getChildren().add(valueLabel);

        this.setAlignment(Pos.CENTER);
        this.getChildren().add(nameLabel);
        this.getChildren().add(stackPane);


    }
}
