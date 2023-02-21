package views;

import engine.Game;
import engine.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.world.Champion;

import java.util.ArrayList;
import java.util.Collections;

public class BoardTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Game.loadAbilities("Abilities.csv");
        Game.loadChampions("Champions.csv");

        ArrayList<Champion> champions = Game.getAvailableChampions();

        Player firstPlayer = new Player("Omar");

        Player secondPlayer = new Player("Sara");

        Collections.addAll(firstPlayer.getTeam(), champions.get(0), champions.get(2), champions.get(3));
        Collections.addAll(secondPlayer.getTeam(), champions.get(6), champions.get(8), champions.get(12));

        Game game = new Game(firstPlayer, secondPlayer);


        BoardRoot boardRoot = new BoardRoot(game, "snow");
        Controller.mainFrame = new MainFrame();
        Controller.mainFrame.boardRoot = boardRoot;
        Controller.mainFrame.game = game;
        Controller.startGrid();

        Scene scene = new Scene(boardRoot);

        stage.getIcons().add(new Image("views/gen_imgs/taskBarIcon.jpg"));
        stage.setTitle("Marvel - Ultimate War!");
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);

    }
}
