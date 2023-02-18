package views;

import engine.Game;
import exceptions.UnallowedMovementException;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.world.Champion;
import model.world.Damageable;
import model.world.Hero;

import java.io.IOException;
import java.util.ArrayList;


public class AbilityPopUpTest extends Application {

    public static HBox getStatsPopUp(Champion c){
        //HP
        MyProgressBar hpBar = new MyProgressBar("HP" , c.getCurrentHP(),c.getMaxHP());

        //SPEED
        MyProgressBar speedBar = new MyProgressBar("SPEED" , c.getSpeed(),c.getMaxSpeed());

        //RANGE
        MyProgressBar rangeBar = new MyProgressBar("RANGE" , c.getAttackRange(),c.getMaxAttackRange());

        //DAMAGE
        MyProgressBar damageBar = new MyProgressBar("DAMAGE" , c.getAttackDamage(),c.getMaxAttackDamage());

        VBox bars1 = new VBox(40);
        bars1.setAlignment(Pos.CENTER);
        bars1.setPrefWidth(bars1.getMaxWidth());

        VBox bars2 = new VBox(40);
        bars2.setAlignment(Pos.CENTER);
        bars2.setPrefWidth(bars2.getMaxWidth());


        bars1.getChildren().addAll(hpBar,damageBar);
        bars2.getChildren().addAll(speedBar,rangeBar);

        Rectangle champ = new Rectangle(100,100);


        HBox root = new HBox(50);
        root.setMaxSize(800,250);
        root.setAlignment(Pos.CENTER);


        root.setStyle("-fx-background-color: aqua;");
        root.getChildren().addAll(champ,bars1,bars2);

        return root;
    }

    public void start(Stage stage) throws IOException {

        DamageableBtn damageableBtn = new DamageableBtn(100,100, "icons", "Dr");



        damageableBtn.setPrefSize(150,150);
        damageableBtn.setMaxSize(150,150);

        StackPane stackPane = new StackPane();
        ImageView imageView = new ImageView(new Image("views/redGif3.png"));

        imageView.setOpacity(0);
        stackPane.getChildren().addAll(imageView, damageableBtn);

        Button button = new Button();

        button.setOnMouseEntered(e -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(400), imageView);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(1);
            fadeTransition1.play();
        });

        button.setOnMouseExited(e -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(400), imageView);

            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

        });

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(stackPane, button);

        Scene scene = new Scene(root);
        stage.setWidth(1000);
        stage.setHeight(500);
        stage.setScene(scene);
        stage.show();
    }

//    public void start2(Stage stage) throws Exception {
//
//        //HP
//        MyProgressBar hpBar = new MyProgressBar("HP" , 1000,1500);
//
//        //SPEED
//        MyProgressBar speedBar = new MyProgressBar("SPEED" , 500,500);
//
//        //RANGE
//        MyProgressBar rangeBar = new MyProgressBar("RANGE" , 5,8);
//
//        //DAMAGE
//        MyProgressBar damageBar = new MyProgressBar("DAMAGE" , 1200,2000);
//
//        VBox bars1 = new VBox(40);
//        bars1.setAlignment(Pos.CENTER);
//        bars1.setPrefWidth(bars1.getMaxWidth());
//
//        VBox bars2 = new VBox(40);
//        bars2.setAlignment(Pos.CENTER);
//        bars2.setPrefWidth(bars2.getMaxWidth());
//
//
//        bars1.getChildren().addAll(hpBar,damageBar);
//        bars2.getChildren().addAll(speedBar,rangeBar);
//
//        HBox root = new HBox(50);
//        root.setAlignment(Pos.CENTER);
//
//        Rectangle champ = new Rectangle(100,100);
//        root.getChildren().addAll(champ,bars1,bars2);
//        Scene scene = new Scene(root, 1000, 700);
//
//
//        stage.setScene(scene);
//        stage.show();
//    }

    public static void main(String[] args) {
        launch(args);
    }

}
