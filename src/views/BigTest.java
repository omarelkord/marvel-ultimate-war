package views;

import engine.Game;
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.abilities.Ability;
import model.world.Champion;

import java.io.IOException;

import static views.Controller.createTopRightIconsStylesheet;

// shows a progress bar whose bar changes color depending on the amount of progress.
public class BigTest extends Application {

    private static final String RED_BAR    = "red-bar";
    private static final String YELLOW_BAR = "yellow-bar";
    private static final String ORANGE_BAR = "orange-bar";
    private static final String GREEN_BAR  = "green-bar";
    private static final String[] barColorStyleClasses = { RED_BAR, ORANGE_BAR, YELLOW_BAR, GREEN_BAR };



    public static HBox createAPBar(int n){
        HBox root = new HBox();

        for(int i=0; i<n; i++){
            ProgressBar bar = new ProgressBar();
            bar.setProgress(1);
            bar.setPrefSize(15, 15);
            bar.getStylesheets().add(BigTest.class.getResource("fonts/orange-button.css").toExternalForm());
            bar.getStyleClass().add("shiny-orange");

            root.getChildren().add(bar);
        }

        return  root;
    }

    public void start2(Stage stage){

        VBox root = new VBox();

        Label label = new Label("20/600");
//        label.setFont(new Font(24));
        label.getStylesheets().add(BigTest.class.getResource("game-font.css").toExternalForm());
        label.getStyleClass().add("shiny-orange");

        //Bruce Forever
        label.setPadding(new Insets(0,0,2,10));

        //W
//        label.setPadding(new Insets(7,180,0,0));


        StackPane stackPane = new StackPane();

        HBox hBox = new HBox();
        ImageView imageView = new ImageView(new Image("views/plus_sign.png"));


        ProgressBar HPbar = new ProgressBar();
        HPbar.setPrefSize(265,20);
        HPbar.setProgress(0.4);
        HPbar.getStylesheets().add(BigTest.class.getResource("fonts/orange-button.css").toExternalForm());
        HPbar.getStyleClass().add("shiny-blue");

//        double i = 265 - (bar.getProgress() * 265);

        hBox.getChildren().add(imageView);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(0,0,1.5,240));
        hBox.setMaxSize(25,25);



//        stackPane.setStyle("-fx-background-color: red;");
        stackPane.setMaxHeight(stackPane.getMinHeight());
        stackPane.setAlignment(Pos.CENTER_LEFT);
//        stackPane.setAlignment(Pos.CENTER_RIGHT);
        stackPane.getChildren().addAll(HPbar, hBox, label);

        Button button = new Button("Click me");
        button.getStylesheets().add(BigTest.class.getResource("fonts/orange-button.css").toExternalForm());
        button.getStyleClass().add("shiny-orange");

        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(stackPane, button);


        stage.setHeight(500);
        stage.setWidth(1500);
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void startShinyGreen(Stage stage){

        VBox root = new VBox();

        Label label = new Label("400");
//        label.setFont(new Font(24));
        label.getStylesheets().add(BigTest.class.getResource("game-font.css").toExternalForm());

        //Bruce Forever
        label.setPadding(new Insets(0,0,1,10));

        //W
//        label.setPadding(new Insets(7,180,0,0));


        StackPane stackPane = new StackPane();

        HBox hBox = new HBox();
        ImageView imageView = new ImageView(new Image("views/plus_sign.png"));


        ProgressBar HPbar = new ProgressBar();
        HPbar.setPrefSize(265,20);
        HPbar.setProgress(0.2);
        HPbar.getStylesheets().add(BigTest.class.getResource("fonts/orange-button.css").toExternalForm());
        HPbar.getStyleClass().add("shiny-green");

//        double i = 265 - (bar.getProgress() * 265);

        hBox.getChildren().add(imageView);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(0,0,1.5,240));
        hBox.setMaxSize(25,25);


//        stackPane.setStyle("-fx-background-color: red;");
        stackPane.setMaxHeight(stackPane.getMinHeight());
        stackPane.setAlignment(Pos.CENTER_LEFT);
//        stackPane.setAlignment(Pos.CENTER_RIGHT);
        stackPane.getChildren().addAll(HPbar, hBox, label);

        Button button = new Button("Click me");
        button.getStylesheets().add(BigTest.class.getResource("fonts/orange-button.css").toExternalForm());
        button.getStyleClass().add("shiny-orange");

        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(stackPane, button);


        stage.setHeight(500);
        stage.setWidth(1500);
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void start3(Stage stage){


        VBox root = new VBox(20);

        //FIRST PLAYER GROUP
        ProgressBar firstPlayerBar1 = new ProgressBar(0.7);
        ProgressBar firstPlayerBar2 = new ProgressBar(0.4);
        ProgressBar firstPlayerBar3 = new ProgressBar(0.3);
        firstPlayerBar1.setPrefSize(265,20);
        firstPlayerBar2.setPrefSize(215, 20);
        firstPlayerBar3.setPrefSize(150, 20);


        VBox firstPlayerBars = new VBox();
        firstPlayerBars.setPadding(new Insets(15,0,0,0));
        firstPlayerBars.getChildren().setAll(firstPlayerBar1, firstPlayerBar2, createAPBar(5));
        firstPlayerBars.getStylesheets().add(getClass().getResource("progress.css").toExternalForm());



        Text hpLabel = new Text("HP 800/1000");
        hpLabel.setStyle("-fx-font: normal bold 11 Langdon;");
        hpLabel.setFill(Color.WHITE);

        Text manaLabel = new Text("MANA 500/700");
        manaLabel.setStyle("-fx-font: normal bold 11 Langdon;");
        manaLabel.setFill(Color.WHITE);

        Text apLabel = new Text("AP 3/5");
        apLabel.setStyle("-fx-font: normal bold 11 Langdon;");
        apLabel.setFill(Color.WHITE);

        VBox barStats = new VBox(2);
        barStats.getChildren().addAll(hpLabel, manaLabel);
        barStats.setPadding(new Insets(17,0,0,20));



        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(firstPlayerBars, barStats);


        Button firstPlayerIcon = new Button();
        firstPlayerIcon.getStylesheets().add(getClass().getResource("topIcons.css").toExternalForm());
        firstPlayerIcon.getStyleClass().add("Th");


        HBox firstPlayerHbox = new HBox(-340);

        firstPlayerHbox.getChildren().addAll(stackPane, firstPlayerIcon);
        firstPlayerHbox.setPadding(new Insets(0,0,0,100));



        Button b1 = new Button();
        b1.setPrefSize(35,35);
        b1.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        b1.getStyleClass().add("Th-Ability-1");

        Button b2 = new Button();
        b2.setPrefSize(35,35);
        b2.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        b2.getStyleClass().add("Th-Ability-2");

        Button b3 = new Button();
        b3.setPrefSize(35,35);
        b3.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        b3.getStyleClass().add("Th-Ability-3");

        HBox firstPlayerAbilities = new HBox(-5);
        firstPlayerAbilities.setPadding(new Insets(0,0,0,100));
        firstPlayerAbilities.getChildren().addAll(b1,b2,b3);


        VBox vBox = new VBox(-15);
        vBox.getChildren().addAll(firstPlayerHbox, firstPlayerAbilities);

        //        firstPlayerBars.getChildren().add(firstPlayerAbilities);


        root.getChildren().add(vBox);


        //SECOND PLAYER GROUP

       ProgressBar secondPlayerBar1 = new ProgressBar(0.3);
       ProgressBar secondPlayerBar2 = new ProgressBar(0.4);
       ProgressBar secondPlayerBar3 = new ProgressBar(0.7);
       secondPlayerBar1.setPrefWidth(150);
       secondPlayerBar2.setPrefWidth(170);
       secondPlayerBar3.setPrefWidth(200);


        Circle secondPlayerCircle = new Circle(45);

        VBox secondPlayerBars = new VBox();
        secondPlayerBars.setPadding(new Insets(50,0,0,0));
        secondPlayerBars.getChildren().setAll(secondPlayerBar1, secondPlayerBar2, secondPlayerBar3);
        secondPlayerBars.getStylesheets().add(getClass().getResource("progress.css").toExternalForm());

        secondPlayerBars.setRotate(180);

        HBox secondPlayerHbox = new HBox(-20);

        secondPlayerHbox.getChildren().addAll(secondPlayerBars, secondPlayerCircle);
        secondPlayerHbox.setPadding(new Insets(0,0,0,100));

//        Rectangle r11 = new Rectangle(20,20);
//        Rectangle r12 = new Rectangle(20,20);
//        Rectangle r13 = new Rectangle(20,20);
//
//        HBox secondPlayerAbilities = new HBox(10);
//        secondPlayerAbilities.setPadding(new Insets(10,0,0,15));
//        secondPlayerAbilities.getChildren().addAll(r11,r12,r13);
//
//        secondPlayerBars.getChildren().add(secondPlayerAbilities);


//        root.getChildren().add(secondPlayerHbox);


        Button button = new Button();

        ColorAdjust darknessEffect = new ColorAdjust();
        darknessEffect.setBrightness(-0.4);
        darknessEffect.setSaturation(-1);


        button.setOnAction(e -> {
            stackPane.setEffect(darknessEffect);
        });

        root.getChildren().add(button);

        stage.setHeight(500);
        stage.setWidth(1500);
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void start4(Stage stage){

        HBox root = new HBox(500);


        Label firstHPLabel = new Label("20/600");

        firstHPLabel.getStylesheets().add(BigTest.class.getResource("game-font.css").toExternalForm());
        firstHPLabel.getStyleClass().add("red");
        firstHPLabel.setPadding(new Insets(0,0,2,10));


        ProgressBar firstPlayerBar1 = new ProgressBar();
        firstPlayerBar1.setPrefSize(265,20);
        firstPlayerBar1.setProgress(0.7);
        firstPlayerBar1.getStylesheets().add(BigTest.class.getResource("fonts/orange-button.css").toExternalForm());
        firstPlayerBar1.getStyleClass().add("shiny-red");

        StackPane firstHPStackpane = new StackPane();

        firstHPStackpane.setAlignment(Pos.CENTER_LEFT);
        firstHPStackpane.getChildren().addAll(firstPlayerBar1, firstHPLabel);

        ImageView firstPlusImg = new ImageView(new Image("views/plus6.png"));

        HBox firstHPRow = new HBox(5);
        firstHPRow.getChildren().addAll(firstPlusImg, firstHPStackpane);

        //

        Label firstManaLabel = new Label("20/600");
        firstManaLabel.getStylesheets().add(BigTest.class.getResource("game-font.css").toExternalForm());
        firstManaLabel.getStyleClass().add("blue");

        firstManaLabel.setPadding(new Insets(0,0,2,10));

        ProgressBar firstPlayerBar2 = new ProgressBar();
        firstPlayerBar2.setPrefSize(215,20);
        firstPlayerBar2.setProgress(0.8);
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

        HBox firstAPRow = new HBox(5);
        firstAPRow.getChildren().addAll(firstLightningImg, createAPBar(5));


//        Button button = new Button("Click me");
//        button.getStylesheets().add(BigTest.class.getResource("fonts/orange-button.css").toExternalForm());
//        button.getStyleClass().add("shiny-orange");
//
//        root.setAlignment(Pos.CENTER);
//        root.getChildren().addAll(apStackpane);


        VBox firstPlayerBars = new VBox();
        firstPlayerBars.setPadding(new Insets(15,0,0,0));
        firstPlayerBars.getChildren().setAll(firstHPRow, firstManaRow, firstAPRow);
        firstPlayerBars.getStylesheets().add(getClass().getResource("progress.css").toExternalForm());


        Button firstPlayerIcon = new Button();
        firstPlayerIcon.getStylesheets().add(getClass().getResource("topIcons.css").toExternalForm());
        firstPlayerIcon.getStyleClass().add("Th");


        HBox firstPlayerHbox = new HBox(0);

        firstPlayerHbox.getChildren().addAll(firstPlayerIcon, firstPlayerBars);
        firstPlayerHbox.setPadding(new Insets(0,0,0,0));



        Button firstAbility1 = new Button();
        firstAbility1.setPrefSize(35,35);
        firstAbility1.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        firstAbility1.getStyleClass().add("Th-Ability-1");

        Button firstAbility2 = new Button();
        firstAbility2.setPrefSize(35,35);
        firstAbility2.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        firstAbility2.getStyleClass().add("Th-Ability-2");

        Button firstAbility3 = new Button();
        firstAbility3.setPrefSize(35,35);
        firstAbility3.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        firstAbility3.getStyleClass().add("Th-Ability-3");

        HBox firstPlayerAbilities = new HBox(-5);
        firstPlayerAbilities.setPadding(new Insets(0,0,0,100));
        firstPlayerAbilities.getChildren().addAll(firstAbility1, firstAbility2, firstAbility3);


        VBox firstPlayerVBox = new VBox(-15);
        firstPlayerVBox.getChildren().addAll(firstPlayerHbox, firstPlayerAbilities);




        //SECOND PLAYER GROUP
        Label secondHPLabel = new Label("20/600");

        secondHPLabel.getStylesheets().add(BigTest.class.getResource("game-font.css").toExternalForm());
        secondHPLabel.getStyleClass().add("red");
        secondHPLabel.setPadding(new Insets(0,0,2,10));


        ProgressBar secondPlayerBar1 = new ProgressBar();
        secondPlayerBar1.setPrefSize(265,20);
        secondPlayerBar1.setProgress(0.7);
        secondPlayerBar1.getStylesheets().add(BigTest.class.getResource("fonts/orange-button.css").toExternalForm());
        secondPlayerBar1.getStyleClass().add("shiny-red");
        secondPlayerBar1.setRotate(180);

        StackPane secondHPStackpane = new StackPane();

        secondHPStackpane.setAlignment(Pos.CENTER_RIGHT);
        secondHPStackpane.getChildren().addAll(secondPlayerBar1, secondHPLabel);

        ImageView secondPlusImg = new ImageView(new Image("views/plus6.png"));


        HBox secondHPRow = new HBox(5);
        secondHPRow.setAlignment(Pos.CENTER_RIGHT);
        secondHPRow.getChildren().addAll(secondHPStackpane, secondPlusImg);

        //

        Label secondManaLabel = new Label("20/600");
        secondManaLabel.getStylesheets().add(BigTest.class.getResource("game-font.css").toExternalForm());
        secondManaLabel.getStyleClass().add("blue");

        secondManaLabel.setPadding(new Insets(0,0,2,10));

        ProgressBar secondPlayerBar2 = new ProgressBar();
        secondPlayerBar2.setPrefSize(215,20);
        secondPlayerBar2.setProgress(0.8);
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

        ImageView secondLightningImg = new ImageView(new Image("views/lightning_sign2.png"));


        HBox secondAPRow = new HBox(5);
        secondAPRow.setAlignment(Pos.CENTER_RIGHT);
        secondAPRow.getChildren().addAll(createAPBar(5), secondLightningImg);

        VBox secondPlayerBars = new VBox();
        secondPlayerBars.setPadding(new Insets(15,0,0,0));
        secondPlayerBars.getChildren().setAll(secondHPRow, secondManaRow, secondAPRow);
        secondPlayerBars.getStylesheets().add(getClass().getResource("progress.css").toExternalForm());


        Button secondPlayerIcon = new Button();
        secondPlayerIcon.getStylesheets().add(getClass().getResource("topIcons.css").toExternalForm());
        secondPlayerIcon.getStyleClass().add("Th");


        HBox secondPlayerHbox = new HBox(0);

        secondPlayerHbox.getChildren().addAll(secondPlayerBars, secondPlayerIcon);
        secondPlayerHbox.setPadding(new Insets(0,0,0,0));



        Button secondAbility1 = new Button();
        secondAbility1.setPrefSize(35,35);
        secondAbility1.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        secondAbility1.getStyleClass().add("Th-Ability-1");

        Button secondAbility2 = new Button();
        secondAbility2.setPrefSize(35,35);
        secondAbility2.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        secondAbility2.getStyleClass().add("Th-Ability-2");

        Button secondAbility3 = new Button();
        secondAbility3.setPrefSize(35,35);
        secondAbility3.getStylesheets().add(getClass().getResource("abilities.css").toExternalForm());
        secondAbility3.getStyleClass().add("Th-Ability-3");

        HBox secondPlayerAbilities = new HBox(-5);
        secondPlayerAbilities.setPadding(new Insets(0,0,0,100));
        secondPlayerAbilities.getChildren().addAll(secondAbility1, secondAbility2, secondAbility3);


        VBox secondPlayerVBox = new VBox(-15);
        secondPlayerVBox.getChildren().addAll(secondPlayerHbox, secondPlayerAbilities);


        root.getChildren().addAll(firstPlayerVBox, secondPlayerVBox);



        stage.setHeight(500);
        stage.setWidth(1500);
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void start(Stage stage){

        StackPane root = new StackPane();
        root.setAlignment(Pos.BOTTOM_CENTER);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setContrast(0.4);
        colorAdjust.setBrightness(-0.5);
        colorAdjust.setSaturation(1);
//        colorAdjust.setHue(-0.8);

        ImageView bgImg = new ImageView("views/snowMap.png");
        ImageView healGif = new ImageView("views/gifs/healTrial3.gif");
        healGif.setEffect(colorAdjust);
        healGif.setBlendMode(BlendMode.SCREEN);

        ImageView healGif2 = new ImageView("views/gifs/healTrial2.gif");
        healGif2.setEffect(colorAdjust);
        healGif2.setBlendMode(BlendMode.SCREEN);

        root.getChildren().addAll(bgImg, healGif);


        stage.setHeight(500);
        stage.setWidth(1500);
        stage.setScene(new Scene(root));
        stage.show();
    }



    public static void createAbilitiesStyleSheet() throws IOException {

        Game.loadAbilities("Abilities.csv");
        Game.loadChampions("Champions.csv");

        String stylesheet = "";

        for(Champion c : Game.getAvailableChampions()){
            for(int i=1; i<=c.getAbilities().size(); i++){
                Ability a = c.getAbilities().get(i-1);

                String abilityName = a.getName().toLowerCase().replace(" ","-");

                String s =
                        "." + c.getName().substring(0,2) + "-Ability-" + i + "{\n" +
                        "   -fx-background-color: transparent;\n" +
                        "   -fx-graphic: url('ability_imgs/" + abilityName + ".png');\n" +
                        "   -fx-cursor: hand;\n" +
                        "}\n" +

                        "."  + c.getName().substring(0,2) + "-Ability-" + i + ":hover {\n" +
                        "   -fx-background-color: transparent;\n" +
                        "   -fx-graphic: url('ability_imgs/" + abilityName + "-hover.png');\n" +
                        "   -fx-cursor: hand;\n" +
                        "}\n" +

                        "."  + c.getName().substring(0,2) + "-Ability-" + i + ":pressed {\n" +
                        "   -fx-background-color: transparent;\n" +
                        "   -fx-graphic: url('ability_imgs/" + abilityName + ".png');\n" +
                        "   -fx-cursor: hand;\n" +
                        "}\n\n";


                stylesheet += s + "\n";
            }
        }

        System.out.println(stylesheet);
    }

    public static void createTopIconsStylesheet() throws IOException {

        Game.loadAbilities("Abilities.csv");
        Game.loadChampions("Champions.csv");

        String stylesheet = "";

        for(Champion c : Game.getAvailableChampions()){

            String champName = c.getName().toLowerCase().replace(" ","-");

            String s =
                    "." + c.getName().substring(0,2) + "{\n" +
                            "   -fx-background-color: transparent;\n" +
                            "   -fx-graphic: url('top_imgs/" + champName + "-top-icon.png');\n" +
                            "   -fx-cursor: hand;\n" +
                            "}\n" +

                            "."  + c.getName().substring(0,2) + ":hover {\n" +
                            "   -fx-background-color: transparent;\n" +
                            "   -fx-graphic: url('top_imgs/" + champName + "-top-icon-hover.png');\n" +
                            "   -fx-cursor: hand;\n" +
                            "}\n" +

                            "."  + c.getName().substring(0,2) + ":pressed {\n" +
                            "   -fx-background-color: transparent;\n" +
                            "   -fx-graphic: url('top_imgs/" + champName + "-top-icon.png');\n" +
                            "   -fx-cursor: hand;\n" +
                            "}\n\n";


            stylesheet += s + "\n";

        }

        System.out.println(stylesheet);
    }
    public static void createTopRightIconsStylesheet() throws IOException {

        Game.loadAbilities("Abilities.csv");
        Game.loadChampions("Champions.csv");

        String stylesheet = "";

        for (Champion c : Game.getAvailableChampions()) {

            String champName = c.getName().toLowerCase().replace(" ", "-");

            String s =
                    "." + c.getName().substring(0, 2) + "{\n" +
                            "   -fx-background-color: transparent;\n" +
                            "   -fx-graphic: url('top_imgs/right/" + champName + "-top-icon.png');\n" +
                            "   -fx-cursor: hand;\n" +
                            "}\n" +

                            "." + c.getName().substring(0, 2) + ":hover {\n" +
                            "   -fx-background-color: transparent;\n" +
                            "   -fx-graphic: url('top_imgs/right/" + champName + "-top-icon-hover.png');\n" +
                            "   -fx-cursor: hand;\n" +
                            "}\n";

            stylesheet += s + "\n";

        }
        System.out.println(stylesheet);
    }



    public static void main(String[] args) throws IOException {
        launch(args);
//        createTopRightIconsStylesheet();
    }



}