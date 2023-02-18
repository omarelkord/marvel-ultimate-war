package views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class AbilityButton extends Button {

    final ImageView imageView = new ImageView(new Image("views/emptyButton.png"));
    final ImageView imageViewGlow = new ImageView(new Image("views/emptyButtonGlow.png"));

    StackPane stackPane;

    Label abilityName;
    public AbilityButton(String name) {

        stackPane = new StackPane();
        abilityName = new Label(name);
        abilityName.setStyle("-fx-font: bold 10 'Century Gothic Bold';" + "-fx-text-fill: white;" +
                "-fx-effect: dropshadow( one-pass-box , black , 10 , 0.0 , 0 , 0 )");

        stackPane.getChildren().addAll(imageView, abilityName);
        this.setStyle("-fx-background-color: transparent;");
        this.setGraphic(stackPane);

//        this.setOnMousePressed(e -> {
//            stackPane.getChildren().clear();
//            stackPane.getChildren().addAll(imageView, abilityName);
//        });


        this.setOnMouseEntered(e -> {
            stackPane.getChildren().clear();
            stackPane.getChildren().addAll(imageViewGlow, abilityName);
        });

        this.setOnMouseExited(e -> {
            stackPane.getChildren().clear();
            stackPane.getChildren().addAll(imageView, abilityName);
        });


    }
}
