package views;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MyBoard extends Pane {

    ImageView f1;
    ImageView f2;
    ImageView f3;

    ImageView s1;
    ImageView s2;
    ImageView s3;


    public MyBoard(){
        super();

        f1 = new ImageView(new Image("views/captainAmericaIcon.png"));
        this.getChildren().add(f1);
        f1.setX(300);
        f1.setY(50);

        Button button = new Button("MOVE DOWN");
        button.setLayoutX(300);
        button.setLayoutY(150);
        button.setOnAction(e -> moveDown());

        Button button2 = new Button("MOVE RIGHT");
        button2.setLayoutX(500);
        button2.setLayoutY(150);
        button2.setOnAction(e -> moveRight());


        this.getChildren().add(button);
        this.getChildren().add(button2);
    }

    public void moveDown(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(f1);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setByY(200);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        translateTransition.play();
    }

    public void moveRight(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(f1);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setByX(200);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        translateTransition.play();
    }

}
