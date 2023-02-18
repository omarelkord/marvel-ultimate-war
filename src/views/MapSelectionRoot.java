package views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static views.Controller.*;

public class MapSelectionRoot extends VBox {

    HBox mapButtons;
    Label promptLabel;
    Button forestButton;
    Button snowButton;
    Button galaxyButton;
    Button fightButton;

    String mapChosen;

    public MapSelectionRoot(double v) {
        super(v);
        this.setAlignment(Pos.CENTER);

        forestButton = new Button();
        forestButton.setOnAction(e -> onSwitchToBoard("forest"));
        forestButton.setOnMouseEntered(e -> this.setStyle("-fx-background-image: url('views/avengersForest.png');"));
        forestButton.setOnMouseExited(e -> this.setStyle("-fx-background-color: #11554A;"));
        forestButton.getStylesheets().add(this.getClass().getResource("mapButtons.css").toExternalForm());
        forestButton.getStyleClass().add("forest");

        snowButton = new Button();
        snowButton.setOnAction(e -> onSwitchToBoard("snow"));
        snowButton.setOnMouseEntered(e -> this.setStyle("-fx-background-image: url('views/avengersSnow.jpg');"));
        snowButton.setOnMouseExited(e -> this.setStyle("-fx-background-color: #11554A;"));
        snowButton.getStylesheets().add(this.getClass().getResource("mapButtons.css").toExternalForm());
        snowButton.getStyleClass().add("snow");

        galaxyButton = new Button();
        galaxyButton.setOnAction(e -> onSwitchToBoard("galaxy"));
//        galaxyButton.setOnMouseEntered(e -> this.setStyle("-fx-background-image: url('views/galaxyGIF2.gif');"));
//        galaxyButton.setOnMouseExited(e -> this.setStyle("-fx-background-color: #11554A;"));
        galaxyButton.getStylesheets().add(this.getClass().getResource("mapButtons.css").toExternalForm());
        galaxyButton.getStyleClass().add("galaxy");

        mapButtons = new HBox(10);
        mapButtons.setAlignment(Pos.CENTER);
        mapButtons.getChildren().addAll(forestButton, snowButton, galaxyButton);

        promptLabel = new Label("SELECT A MAP TO FIGHT!");
        promptLabel.setStyle("-fx-font: normal bold 30 Langdon;" + "-fx-text-fill: white;");

        this.getChildren().addAll(promptLabel, mapButtons);

        this.setStyle("-fx-background-color: #11554A;");

    }

}
