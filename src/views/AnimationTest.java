package views;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;


public class AnimationTest extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image("views/captainAmericaIcon.png");
        ImageView imageView = new ImageView(image);


        Button button = new Button("TEST");

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(button);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setByY(200);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        translateTransition.play();

        VBox vBox = new VBox();
        vBox.getChildren().add(button);


        Scene scene = new Scene(vBox, 500, 500);
        stage.setScene(scene);
        stage.show();

    }
}
