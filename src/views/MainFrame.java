
package views;


import engine.Game;
import engine.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Paths;

import static views.Controller.*;

public class MainFrame extends Application {

    Game game;
    Player firstPlayer;
    Player secondPlayer;
    SelectionRoot selectionRoot;
    StartingRoot startingRoot;
    BoardRoot boardRoot;

    LeaderRoot leaderRoot;


    MapSelectionRoot mapSelectionRoot;
    GameOverRoot gameOverRoot;

    InstructionsRoot instructionsRoot;


    public void start(Stage stage) throws IOException {
//        music();
        Controller controller = new Controller(this);

        startingRoot = new StartingRoot(10);
        startingRoot.playButton.setOnAction(e -> onStartButton());

        Scene scene = new Scene(startingRoot, 1000, 500);
//        MediaPlayer player = new MediaPlayer( new Media(getClass().getResource("marvelintro.mkv").toExternalForm()));
//        MediaView mediaView = new MediaView(player);
//
//        startingRoot.getChildren().add( mediaView);

        stage.getIcons().add(new Image("views/gen_imgs/taskBarIcon.jpg"));
        stage.setTitle("Marvel - Ultimate War!");
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
//        player.play();

    }

    MediaPlayer mediaPlayer;

    public void music() {

        String s = "rise of the enemy.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(10);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
