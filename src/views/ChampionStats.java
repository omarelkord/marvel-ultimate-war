package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.world.Champion;

public class ChampionStats extends HBox {

    Champion champion;
    MyRectangle rectangle;

//    public ChampionStats(Champion champion) {
//        this.champion = champion;
//        this.setAlignment(Pos.CENTER);
//        rectangle = new MyRectangle(champion.getCurrentHP());
//        this.getChildren().add(rectangle);
//        Label label = new Label("100");
//        label.setPadding(new Insets(10));
//        this.getChildren().add(label);
//    }

    public ChampionStats(Champion champion){
        this.champion = champion;
    }
}
