package views;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import static views.Controller.*;

public class MapSelectionRoot extends StackPane {

    HBox mapButtons;
    Label promptLabel;
    Button forestButton;
    Button snowButton;
    Button galaxyButton;
    Button fightButton;

    String mapChosen;

    VBox container;

    public MapSelectionRoot() {
        super();

        container = new VBox();

        container.setAlignment(Pos.CENTER);

        ImageView blackImg = new ImageView(new Image("views/gen_imgs/black-img.png"));

        forestButton = new Button();
//        forestButton.setOnAction(e -> onSwitchToBoard("forest"));
        forestButton.setOnMouseEntered(e -> container.setStyle("-fx-background-image: url('views/bg_imgs/forestMapPreview2.png');"));
        forestButton.setOnMouseExited(e -> container.setStyle("-fx-background-color: #11554A;"));
        forestButton.getStylesheets().add(this.getClass().getResource("css/map-btns.css").toExternalForm());
        forestButton.getStyleClass().add("forest");
//        forestButton.setOnAction(e -> {
//
//            ImageView preview = new ImageView(new Image("views/bg_imgs/forestMapPreview.png"));
//
//            this.getChildren().add(blackImg);
//            this.getChildren().add(preview);
//
//
//            FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(500), blackImg);
//            FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(500), preview);
//            fadeTransition1.setFromValue(0);
//            fadeTransition1.setToValue(1);
//            fadeTransition1.play();
//
//            fadeTransition2.setFromValue(0);
//            fadeTransition2.setToValue(1);
//            fadeTransition2.play();
//        });


        snowButton = new Button();
        snowButton.setOnAction(e -> onSwitchToBoard("snow"));
        snowButton.setOnMouseEntered(e -> this.setStyle("-fx-background-image: url('views/bg_imgs/avengersSnow.jpg');"));
        snowButton.setOnMouseExited(e -> this.setStyle("-fx-background-color: #11554A;"));
        snowButton.getStylesheets().add(this.getClass().getResource("css/map-btns.css").toExternalForm());
        snowButton.getStyleClass().add("snow");


        galaxyButton = new Button();
        galaxyButton.setOnAction(e -> onSwitchToBoard("galaxy"));
//        galaxyButton.setOnMouseEntered(e -> this.setStyle("-fx-background-image: url('views/galaxyGIF2.gif');"));
//        galaxyButton.setOnMouseExited(e -> this.setStyle("-fx-background-color: #11554A;"));
        galaxyButton.getStylesheets().add(this.getClass().getResource("css/map-btns.css").toExternalForm());
        galaxyButton.getStyleClass().add("galaxy");


        mapButtons = new HBox(10);
        mapButtons.setAlignment(Pos.CENTER);
        mapButtons.getChildren().addAll(forestButton, snowButton, galaxyButton);

        promptLabel = new Label("SELECT A MAP TO FIGHT!");
        promptLabel.setStyle("-fx-font: normal bold 30 Langdon;" + "-fx-text-fill: white;");

        container.getChildren().addAll(promptLabel, mapButtons);

        container.setStyle("-fx-background-color: #11554A;");

        this.getChildren().add(container);
    }

}
