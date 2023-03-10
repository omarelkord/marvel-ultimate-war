package views;

import engine.Game;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.world.Champion;
import model.world.Cover;

import static views.Controller.*;

public class MapSelectionRoot extends StackPane {

    HBox mapButtons;
    Label promptLabel;
    Button forestButton;
    Button snowButton;
    Button fightButton;
    String mapChosen;

    VBox container;

    public MapSelectionRoot(Game game) {
        super();

        ImageView blackImg = new ImageView(new Image("views/gen_imgs/black-img.png"));
        ImageView alertImg = new ImageView(new Image("views/gen_imgs/map-alert.png"));

        BoardRoot forestMapPreview = new BoardRoot(game, "forest");
        BoardRoot snowMapPreview = new BoardRoot(game, "snow");

        container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        this.getChildren().add(alertImg);
        this.getChildren().add(container);



        forestButton = new Button();
        forestButton.setOnAction(e -> onSwitchToBoard("forest"));

        forestButton.setOnMouseEntered(e -> {
            forestMapPreview.center.getChildren().clear();
            forestMapPreview.center.getChildren().add(startGrid(game, "forest"));
            this.getChildren().add(0, forestMapPreview);
            this.getChildren().add(1, blackImg);
        });

        forestButton.setOnMouseExited(e -> {
            this.getChildren().remove(0);
            this.getChildren().remove(0);
        });

        forestButton.getStylesheets().add(this.getClass().getResource("css/map-btns.css").toExternalForm());
        forestButton.getStyleClass().add("forest");


        snowButton = new Button();
        snowButton.setOnAction(e -> onSwitchToBoard("snow"));

        snowButton.setOnMouseEntered(e -> {
            snowMapPreview.center.getChildren().clear();
            snowMapPreview.center.getChildren().add(startGrid(game, "snow"));
            this.getChildren().add(0, snowMapPreview);
            this.getChildren().add(1, blackImg);
        });

        snowButton.setOnMouseExited(e -> {
            this.getChildren().remove(0);
            this.getChildren().remove(0);
        });
        snowButton.getStylesheets().add(this.getClass().getResource("css/map-btns.css").toExternalForm());
        snowButton.getStyleClass().add("snow");


        mapButtons = new HBox(10);
        mapButtons.setAlignment(Pos.CENTER);
        mapButtons.getChildren().addAll(forestButton, snowButton);

        promptLabel = new Label("SELECT A MAP TO FIGHT!");
        promptLabel.getStylesheets().add(this.getClass().getResource("css/game-font.css").toExternalForm());
        promptLabel.getStyleClass().add("map-sel-font");
//        promptLabel.setStyle("-fx-font: normal bold 30 Langdon;" + "-fx-text-fill: white;");

        container.getChildren().addAll(promptLabel, mapButtons);
        this.setStyle("-fx-background-image: url('views/bg_imgs/mapBG.png');");

    }


    public static GridPane startGrid(Game game, String mapChosen) {
        Object[][] b = game.getBoard();

        GridPane gridPane = new GridPane();

        gridPane.setStyle("-fx-background-image: url('views/bg_imgs/" + mapChosen + "Map.png');");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                DamageableBtn damageableBtn;

                //later

                if (b[i][j] instanceof Cover) {
                    Cover cover = (Cover) b[i][j];

                    damageableBtn = new DamageableBtn(cover.getCurrentHP(), cover.getMaxHP(), "cover", mapChosen);

                } else if (b[i][j] instanceof Champion) {
//                else {
//                    button.setText(((Champion) b[i][j]).getName());
                    Champion champion = (Champion) b[i][j];

                    damageableBtn = new DamageableBtn(champion.getCurrentHP(), champion.getMaxHP(), "icons", champion.getName().substring(0, 2));

                } else {
                    damageableBtn = new DamageableBtn(0,0,"cover","snow");
                    damageableBtn.setOpacity(0);
                    damageableBtn.setStyle("-fx-background-color: transparent;");
                }

                gridPane.add(damageableBtn, j, 4 - i);
//                mainFrame.boardRoot.buttonBoard[i][j] = damageableBtn;
            }
        }

        return gridPane;

    }
}
