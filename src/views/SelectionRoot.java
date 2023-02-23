package views;

import engine.Game;
import engine.Player;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import model.world.Champion;

import java.io.IOException;
import java.util.ArrayList;

import static engine.Game.getAvailableChampions;
import static engine.Game.loadChampions;
import static views.Controller.*;

public class SelectionRoot extends StackPane {

    VBox selectionRoot;

    HBox championButtons;

    BorderPane hboxStats;
    ArrayList<Button> buttonList = new ArrayList<>();
    ArrayList<Button> chosenList = new ArrayList<>();

    Button next;

    Label selectionLabel;

    boolean firstClick;

    Button select;
    Button back;
    VBox vboxSelect;
    VBox vboxBack;


    public SelectionRoot() throws IOException {
        super();

        selectionRoot = new VBox();

        firstClick = true;
        select = new Button();
        select.setPrefSize(100, 30);
        select.getStylesheets().add(this.getClass().getResource("css/select-back-btns.css").toExternalForm());
        select.getStyleClass().add("select");

        back = new Button();
        back.getStylesheets().add(this.getClass().getResource("css/select-back-btns.css").toExternalForm());
        back.getStyleClass().add("back");
        back.setPrefSize(100, 30);

        selectionRoot.setStyle("-fx-background-image: url('views/bg_imgs/selectionBG.png');");

        championButtons = new HBox(-110);

        selectionRoot.setAlignment(Pos.BOTTOM_CENTER);

        Game.loadAbilities("Abilities.csv");
        Game.loadChampions("Champions.csv");

        ArrayList<Champion> availableChampions = Game.getAvailableChampions();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(323);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");
        scrollPane.setFitToHeight(true);

        selectionLabel = new Label();

        hboxStats = new BorderPane();
        hboxStats.setPrefSize(1200, 620);
        VBox.setVgrow(hboxStats, Priority.ALWAYS);

        int i = 0;


        for (Champion c : availableChampions) {

            Button button = new Button();
            buttonList.add(button);
            int finalI = i;

            button.setOnAction(e -> onStatsButton(finalI));
            button.getStylesheets().add(this.getClass().getResource("css/selection-btns.css").toExternalForm());
            button.getStyleClass().add(c.getName().substring(0, 2));
            championButtons.getChildren().add(button);
            i++;
        }

        selectionRoot.getChildren().add(hboxStats);
        vboxBack = new VBox();
        vboxBack.setAlignment(Pos.BOTTOM_CENTER);

        vboxSelect = new VBox();
        vboxSelect.setAlignment(Pos.BOTTOM_CENTER);

        hboxStats.setLeft(vboxBack);
        hboxStats.setRight(vboxSelect);

        scrollPane.setContent(championButtons);
        selectionRoot.getChildren().add(scrollPane);

        this.getChildren().add(selectionRoot);

    }
}
