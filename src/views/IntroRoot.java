package views;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.nio.file.Paths;

public class IntroRoot extends VBox{
    MediaPlayer mediaPlayer;
    MediaView mediaView;

    public IntroRoot(){
        super();
        introvid();
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(mediaView);
    }


    public void introvid() {

        this.setStyle("-fx-background-color: black");
        String s = "marvel-intro.mp4";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setAutoPlay(true);

        //mediaPlayer2.setVolume(10);
        mediaView = new MediaView(mediaPlayer);


        final DoubleProperty width = mediaView.fitWidthProperty();
        final DoubleProperty height = mediaView.fitHeightProperty();

        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

        mediaView.setPreserveRatio(true);


        //mediaPlayer2.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();

    }
}
