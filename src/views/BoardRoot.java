
package views;

import com.sun.tools.javac.Main;
import engine.Game;
import engine.PriorityQueue;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import javafx.animation.FadeTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.abilities.Ability;
import model.world.Champion;
import model.world.Damageable;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.Collections;

import static views.Controller.*;

public class BoardRoot extends StackPane {


    HBox abilityButtons;
    VBox firstTeam;
    VBox secondTeam;
    HBox actions;
    HBox turnOrder;
    VBox actionInfo;
    VBox abilities;
    HBox champInfo;

    HBox effectInfo;

    HBox abilityInfo;

    ArrayList<Button> firstTeamButtons = new ArrayList<>();
    ArrayList<Button> secondTeamButtons = new ArrayList<>();

    HBox hp;
    GridPane board;
    GridPane gifGridpane;

    StackPane center;
    DamageableBtn[][] buttonBoard = new DamageableBtn[5][5];
    Button instruction; //hover to show game instructions
    String mapChosen;

    Label firstHPLabel, firstManaLabel, secondHPLabel, secondManaLabel;

    ProgressBar firstPlayerBar1, firstPlayerBar2;
    HBox firstPlayerBar3, secondPlayerBar3;
    ProgressBar secondPlayerBar1, secondPlayerBar2;


    ArrayList<Button> firstAbilityButtons;
    ArrayList<Button> secondAbilityButtons;


    VBox firstPlayerVBox;
    VBox secondPlayerVBox;

    Button firstPlayerIcon, secondPlayerIcon;

    BorderPane borderPane;
    ImageView blackImg;
    HBox popUpStats;


    ColorAdjust darknessEffect;
    ColorAdjust normalEffect;


    HBox firstAPRow, secondAPRow;
    HBox firstPlayerAbilities, secondPlayerAbilities;

    public BoardRoot(Game game, String mapChosen) {
        super();

        borderPane = new BorderPane();

        blackImg = new ImageView(new Image("views/black-img.png"));

        darknessEffect = new ColorAdjust();
        darknessEffect.setBrightness(-0.5);
        darknessEffect.setSaturation(-1);
        darknessEffect.setContrast(.5);

        normalEffect = new ColorAdjust();
        normalEffect.setBrightness(0);
        normalEffect.setSaturation(0);

        this.mapChosen = mapChosen;

        this.setStyle("-fx-background-image: url('views/" + mapChosen + "BG" + ".jpg');");

        abilityInfo = new HBox(10);
        abilityButtons = new HBox(10);
        abilityButtons.setAlignment(Pos.TOP_CENTER);
        abilities = new VBox(10);
        abilities.setAlignment(Pos.TOP_CENTER);
        champInfo = new HBox(10);
        champInfo.setAlignment(Pos.TOP_CENTER);

        hp = new HBox(5);
        hp.setPrefSize(400, 100);

        turnOrder = new HBox(-16);
        turnOrder.setPadding(new Insets(0,0,0,0));
        turnOrder.setPrefSize(2000, 150);
        turnOrder.setAlignment(Pos.CENTER);

//        turnOrder.setBackground(new Background(new BackgroundFill(Color.FIREBRICK, CornerRadii.EMPTY, Insets.EMPTY)));

//        turnOrder.setStyle("-fx-background-color: #ADD8E6");
//


        Champion firstChampion = null;
        Champion secondChampion = null;
        PriorityQueue turnOrderPQ = game.getTurnOrder();

        ArrayList<Champion> temp = new ArrayList<>();

        if(game.getFirstPlayer().getTeam().contains((Champion) turnOrderPQ.peekMin()))
            firstChampion = (Champion) turnOrderPQ.peekMin();
        else
            secondChampion = (Champion) turnOrderPQ.peekMin();

        while(!turnOrderPQ.isEmpty()){

            Champion removed = (Champion) turnOrderPQ.remove();

            if(firstChampion == null && game.getFirstPlayer().getTeam().contains(removed))
                firstChampion = removed;
            else if(secondChampion == null && game.getSecondPlayer().getTeam().contains(removed))
                secondChampion = removed;

            temp.add(removed);
        }

        for(Champion c : temp){
            turnOrderPQ.insert(c);
        }


        HBox top = new HBox();

        //FIRST PLAYER BARS


        firstHPLabel = new Label(firstChampion.getCurrentHP() + "/" + firstChampion.getMaxHP());

        firstHPLabel.getStylesheets().add(BigTest.class.getResource("game-font.css").toExternalForm());
        firstHPLabel.getStyleClass().add("red");
        firstHPLabel.setPadding(new Insets(0,0,2,10));


        firstPlayerBar1 = new ProgressBar();
        firstPlayerBar1.setPrefSize(265,20);
        firstPlayerBar1.setProgress(1);
        firstPlayerBar1.getStylesheets().add(BoardRoot.class.getResource("fonts/orange-button.css").toExternalForm());
        firstPlayerBar1.getStyleClass().add("shiny-green");

        StackPane firstHPStackpane = new StackPane();

        firstHPStackpane.setAlignment(Pos.CENTER_LEFT);
        firstHPStackpane.getChildren().addAll(firstPlayerBar1, firstHPLabel);

        ImageView firstPlusImg = new ImageView(new Image("views/plus6.png"));

        HBox firstHPRow = new HBox(5);
        firstHPRow.getChildren().addAll(firstPlusImg, firstHPStackpane);

        //

        firstManaLabel = new Label(firstChampion.getMana() + "/" + firstChampion.getMaxMana());
        firstManaLabel.getStylesheets().add(BigTest.class.getResource("game-font.css").toExternalForm());
        firstManaLabel.getStyleClass().add("blue");

        firstManaLabel.setPadding(new Insets(0,0,2,10));

        firstPlayerBar2 = new ProgressBar();
        firstPlayerBar2.setPrefSize(215,20);
        firstPlayerBar2.setProgress(1);
        firstPlayerBar2.getStylesheets().add(BigTest.class.getResource("fonts/orange-button.css").toExternalForm());
        firstPlayerBar2.getStyleClass().add("shiny-blue");


        ImageView firstManaImg = new ImageView(new Image("views/mana_sign.png"));

        StackPane firstManaStackpane = new StackPane();

        firstManaStackpane.setAlignment(Pos.CENTER_LEFT);
        firstManaStackpane.getChildren().addAll(firstPlayerBar2, firstManaLabel);

        HBox firstManaRow = new HBox(5);
        firstManaRow.getChildren().addAll(firstManaImg, firstManaStackpane);

        //

        ImageView firstLightningImg = new ImageView(new Image("views/lightning_sign2.png"));

        firstPlayerBar3 = createAPBar(firstChampion.getCurrentActionPoints(), firstChampion.getMaxActionPointsPerTurn(), "L");

        firstAPRow = new HBox(5);
        firstAPRow.getChildren().addAll(firstLightningImg, firstPlayerBar3);


        VBox firstPlayerBars = new VBox();
        firstPlayerBars.setPadding(new Insets(15,0,0,0));
        firstPlayerBars.getChildren().setAll(firstHPRow, firstManaRow, firstAPRow);
        firstPlayerBars.getStylesheets().add(getClass().getResource("progress.css").toExternalForm());


        firstPlayerIcon = new Button();
        firstPlayerIcon.getStylesheets().add(getClass().getResource("topLeftIcons.css").toExternalForm());
        firstPlayerIcon.getStyleClass().add(firstChampion.getName().substring(0,2));


        HBox firstPlayerHbox = new HBox(0);

        firstPlayerHbox.getChildren().addAll(firstPlayerIcon, firstPlayerBars);
        firstPlayerHbox.setPadding(new Insets(0,0,0,0));


        Button firstAbility1 = new Button();
        firstAbility1.setPrefSize(35,35);
        firstAbility1.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        firstAbility1.getStyleClass().add(firstChampion.getName().substring(0,2) + "-Ability-1");

        Button firstAbility2 = new Button();
        firstAbility2.setPrefSize(35,35);
        firstAbility2.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        firstAbility2.getStyleClass().add(firstChampion.getName().substring(0,2) + "-Ability-2");


        Button firstAbility3 = new Button();
        firstAbility3.setPrefSize(35,35);
        firstAbility3.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        firstAbility3.getStyleClass().add(firstChampion.getName().substring(0,2) + "-Ability-3");

        firstPlayerAbilities = new HBox(-5);
        firstPlayerAbilities.setPadding(new Insets(0,0,0,100));
        firstPlayerAbilities.getChildren().addAll(firstAbility1, firstAbility2, firstAbility3);


        firstPlayerVBox = new VBox(-15);
        firstPlayerVBox.setPrefWidth(2000);
        firstPlayerVBox.getChildren().addAll(firstPlayerHbox, firstPlayerAbilities);
        firstPlayerVBox.setPadding(new Insets(10, 0, 0, 0));


        firstAbilityButtons = new ArrayList<>();
        Collections.addAll(firstAbilityButtons, firstAbility1, firstAbility2, firstAbility3);


        //SECOND PLAYER BARS
        secondHPLabel = new Label(secondChampion.getCurrentHP() + "/" + secondChampion.getMaxHP());

        secondHPLabel.getStylesheets().add(BigTest.class.getResource("game-font.css").toExternalForm());
        secondHPLabel.getStyleClass().add("red");
        secondHPLabel.setPadding(new Insets(0,10,0,0));


        secondPlayerBar1 = new ProgressBar();
        secondPlayerBar1.setPrefSize(265,20);
        secondPlayerBar1.setProgress(1);
        secondPlayerBar1.getStylesheets().add(BigTest.class.getResource("fonts/orange-button.css").toExternalForm());
        secondPlayerBar1.getStyleClass().add("shiny-green");
        secondPlayerBar1.setRotate(180);

        StackPane secondHPStackpane = new StackPane();

        secondHPStackpane.setAlignment(Pos.CENTER_RIGHT);
        secondHPStackpane.getChildren().addAll(secondPlayerBar1, secondHPLabel);

        ImageView secondPlusImg = new ImageView(new Image("views/plus6.png"));


        HBox secondHPRow = new HBox(5);
        secondHPRow.setAlignment(Pos.CENTER_RIGHT);
        secondHPRow.getChildren().addAll(secondHPStackpane, secondPlusImg);

        //

        secondManaLabel = new Label(secondChampion.getMana() + "/" + secondChampion.getMaxMana());
        secondManaLabel.getStylesheets().add(BigTest.class.getResource("game-font.css").toExternalForm());
        secondManaLabel.getStyleClass().add("blue");

        secondManaLabel.setPadding(new Insets(0,10,0,0));

        secondPlayerBar2 = new ProgressBar();
        secondPlayerBar2.setPrefSize(215,20);
        secondPlayerBar2.setProgress(1);
        secondPlayerBar2.getStylesheets().add(BigTest.class.getResource("fonts/orange-button.css").toExternalForm());
        secondPlayerBar2.getStyleClass().add("shiny-blue");
        secondPlayerBar2.setRotate(180);

        StackPane secondManaStackpane = new StackPane();
        secondManaStackpane.setAlignment(Pos.CENTER_RIGHT);
        secondManaStackpane.getChildren().addAll(secondPlayerBar2, secondManaLabel);

        ImageView secondManaImg = new ImageView(new Image("views/mana_sign.png"));

        HBox secondManaRow = new HBox(5);
        secondManaRow.setAlignment(Pos.CENTER_RIGHT);
        secondManaRow.getChildren().addAll(secondManaStackpane, secondManaImg);

        //

        secondPlayerBar3 = createAPBar(secondChampion.getCurrentActionPoints(), secondChampion.getMaxActionPointsPerTurn(), "R");

        ImageView secondLightningImg = new ImageView(new Image("views/lightning_sign2.png"));


        secondAPRow = new HBox(5);
        secondAPRow.setAlignment(Pos.CENTER_RIGHT);
        secondAPRow.getChildren().addAll(secondPlayerBar3, secondLightningImg);



        VBox secondPlayerBars = new VBox();
        secondPlayerBars.setPadding(new Insets(15,0,0,0));
        secondPlayerBars.getChildren().setAll(secondHPRow, secondManaRow, secondAPRow);
        secondPlayerBars.getStylesheets().add(getClass().getResource("progress.css").toExternalForm());


        secondPlayerIcon = new Button();
        secondPlayerIcon.getStylesheets().add(getClass().getResource("topRightIcons.css").toExternalForm());
        secondPlayerIcon.getStyleClass().add(secondChampion.getName().substring(0,2));


        HBox secondPlayerHbox = new HBox(0);
        secondPlayerHbox.setAlignment(Pos.CENTER_RIGHT);
        secondPlayerHbox.getChildren().addAll(secondPlayerBars, secondPlayerIcon);
        secondPlayerHbox.setPadding(new Insets(0,0,0,0));


        Button secondAbility1 = new Button();
        secondAbility1.setPrefSize(35,35);
        secondAbility1.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        secondAbility1.getStyleClass().add(secondChampion.getName().substring(0,2) + "-Ability-1");

        Button secondAbility2 = new Button();
        secondAbility2.setPrefSize(35,35);
        secondAbility2.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        secondAbility2.getStyleClass().add(secondChampion.getName().substring(0,2) + "-Ability-2");

        Button secondAbility3 = new Button();
        secondAbility3.setPrefSize(35,35);
        secondAbility3.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        secondAbility3.getStyleClass().add(secondChampion.getName().substring(0,2) + "-Ability-3");

        secondPlayerAbilities = new HBox(-5);
        secondPlayerAbilities.setAlignment(Pos.CENTER_RIGHT);
        secondPlayerAbilities.setPadding(new Insets(0,100,0,0));
        secondPlayerAbilities.getChildren().addAll(secondAbility1, secondAbility2, secondAbility3);


        secondPlayerVBox = new VBox(-15);
        secondPlayerVBox.setPrefWidth(2000);
        secondPlayerVBox.getChildren().addAll(secondPlayerHbox, secondPlayerAbilities);
        secondPlayerVBox.setPadding(new Insets(10, 0, 0, 0));

        secondAbilityButtons = new ArrayList<>();
        Collections.addAll(secondAbilityButtons, secondAbility1, secondAbility2, secondAbility3);

        setUpAbilityBtns(firstChampion, game);
        setUpAbilityBtns(secondChampion, game);

        top.getChildren().addAll(firstPlayerVBox, turnOrder, secondPlayerVBox);




 // setting the contrast of the color







        actions = new HBox();
//        actions.setPrefHeight(150);
//        actions.setStyle("-fx-background-color: #ADD8E6");

        actionInfo = new VBox(10);
        actionInfo.setPrefHeight(185);
        actionInfo.setAlignment(Pos.TOP_CENTER);
        actionInfo.getChildren().add(actions);

        secondTeam = new VBox(30);
        secondTeam.setPrefWidth(400);
//        secondTeam.setStyle("-fx-background-color: #ADD8E6");
        firstTeam = new VBox(30);
        firstTeam.setPrefWidth(400);
//        firstTeam.setStyle("-fx-background-color: #ADD8E6");

        Button moveButton = new Button();
        moveButton.getStylesheets().add(this.getClass().getResource("ActionButtons.css").toExternalForm());
        moveButton.getStyleClass().add("move");

        moveButton.setOnAction(e -> {
            onClick();
            this.setOnKeyPressed(f -> onMoveButton(f.getCode()));
            actionInfo.getChildren().remove(abilities);

        });

        Button attackButton = new Button();
        attackButton.getStylesheets().add(this.getClass().getResource("ActionButtons.css").toExternalForm());
        attackButton.getStyleClass().add("attack");

        attackButton.setOnAction(e -> {
            onClick();
            this.setOnKeyPressed(f -> onAttackButton(f.getCode()));
            actionInfo.getChildren().remove(abilities);

        });

        attackButton.setOnMouseEntered(e -> {
            addRings(game.getPotentialAttackTargets());
        });

        attackButton.setOnMouseExited(e -> {
            gifGridpane.getChildren().clear();
            center.getChildren().remove(mainFrame.boardRoot.gifGridpane);
            borderPane.setCenter(mainFrame.boardRoot.center);
        });


        Button castButton = new Button();
        castButton.getStylesheets().add(this.getClass().getResource("ActionButtons.css").toExternalForm());
        castButton.getStyleClass().add("cast");

        castButton.setOnAction(e -> {
            onClick();
//            secondStackpane.setEffect(darknessEffect);
//            addAbilitiesByButtons();
        });

        Button endTurnButton = new Button();
        endTurnButton.getStylesheets().add(this.getClass().getResource("ActionButtons.css").toExternalForm());
        endTurnButton.getStyleClass().add("end");

        endTurnButton.setOnAction(e -> {
            onClick();
            onEndTurn();
            actionInfo.getChildren().remove(abilities);
        });


        Button leaderAbility = new Button();
        leaderAbility.getStylesheets().add(this.getClass().getResource("ActionButtons.css").toExternalForm());
        leaderAbility.getStyleClass().add("leader");

        leaderAbility.setOnAction(e -> onLeaderAbility());

        Tooltip t = new Tooltip();

        String text;
        if (game.getCurrentChampion() == game.getFirstPlayer().getLeader()) {
            if (game.isFirstLeaderAbilityUsed())
                text = "Leader Ability used";
            else
                text = "Leader Ability not used";
        } else if (game.getCurrentChampion() == game.getSecondPlayer().getLeader()) {
            if (game.isSecondLeaderAbilityUsed())
                text = "Leader Ability used";
            else
                text = "Leader Ability not used";
        } else
            text = "Current champion is not a leader";
        t.setText(text);
        t.setStyle("-fx-font: normal bold 11 Langdon;" + "-fx-base: #AE3522;" + "-fx-text-fill: orange;");
        leaderAbility.setTooltip(t);


        actions.setAlignment(Pos.CENTER);
        actions.getChildren().addAll(moveButton, attackButton, castButton, endTurnButton, leaderAbility);

        Label firstPlayerName = new Label(game.getFirstPlayer().getName() + "'s Team");
        firstPlayerName.setStyle("-fx-font: normal bold 14 Langdon;" + "-fx-text-fill: white;");

        Label secondPlayerName = new Label(game.getSecondPlayer().getName() + "'s Team");
        secondPlayerName.setStyle("-fx-font: normal bold 14 Langdon;" + "-fx-text-fill: white;");

        firstTeam.getChildren().add(firstPlayerName);
        secondTeam.getChildren().add(secondPlayerName);

        for (Champion champion : game.getFirstPlayer().getTeam()) {
            Button button = new Button();

            button.getStylesheets().add(this.getClass().getResource("sideIcon.css").toExternalForm());
            button.getStyleClass().add(champion.getName().substring(0, 2));

            button.setOnAction(e -> {
                popUpStats = Controller.getStatsPopUp(champion);

                this.getChildren().add(blackImg);
                this.getChildren().add(popUpStats);


                FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(500), blackImg);
                FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(500), popUpStats);
                fadeTransition1.setFromValue(0);
                fadeTransition1.setToValue(1);
                fadeTransition1.play();

                fadeTransition2.setFromValue(0);
                fadeTransition2.setToValue(1);
                fadeTransition2.play();
            });

            button.setPrefSize(100, 150);
            firstTeamButtons.add(button);
        }

        for (Champion champion : game.getSecondPlayer().getTeam()) {
            Button button = new Button(champion.getName());

            button.getStylesheets().add(this.getClass().getResource("sideIcon.css").toExternalForm());
            button.getStyleClass().add(champion.getName().substring(0, 2));

            button.setOnAction(e -> {

                popUpStats = Controller.getStatsPopUp(champion);

                this.getChildren().add(blackImg);
                this.getChildren().add(popUpStats);


                FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(500), blackImg);
                FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(500), popUpStats);
                fadeTransition1.setFromValue(0);
                fadeTransition1.setToValue(1);
                fadeTransition1.play();

                fadeTransition2.setFromValue(0);
                fadeTransition2.setToValue(1);
                fadeTransition2.play();

            });



            button.setPrefSize(100, 150);
            secondTeamButtons.add(button);
        }

        firstTeam.setAlignment(Pos.CENTER);
        secondTeam.setAlignment(Pos.CENTER);


        firstTeam.getChildren().addAll(firstTeamButtons);
        secondTeam.getChildren().addAll(secondTeamButtons);


        abilities.getChildren().add(abilityButtons);

        borderPane.setTop(top);
        borderPane.setBottom(actionInfo);
        borderPane.setLeft(firstTeam);
        borderPane.setRight(secondTeam);


        board = new GridPane();


        center = new StackPane();

        board.setStyle("-fx-background-color: #018571");
        borderPane.setCenter(center);

        this.getChildren().add(borderPane);
    }

    public void setUpAbilityBtns(Champion c, Game game) {


        for (int i = 0; i < c.getAbilities().size(); i++) {
            Ability a = c.getAbilities().get(i);
            Tooltip t = new Tooltip();

            Button firstBtn = firstAbilityButtons.get(i);
            Button secondBtn = secondAbilityButtons.get(i);

            if (game.getFirstPlayer().getTeam().contains(c)) {
                firstBtn.setTooltip(t);
                String text = getAbilityStatsToolTip(a);
                t.setText(text);
                firstBtn.setOnAction(e -> onCastAbility(a.getName()));

//                firstPlayerAbilities.setSpacing(7);
            } else {
                secondBtn.setTooltip(t);
                String text = getAbilityStatsToolTip(a);
                t.setText(text);
                secondBtn.setOnAction(e -> onCastAbility(a.getName()));

//                secondPlayerAbilities.setSpacing(7);
            }
        }
    }
}


