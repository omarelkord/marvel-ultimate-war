package views;

import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.world.Champion;


public class MySideIcon extends StackPane {

    Button button;
    Champion champion;

    public MySideIcon(Champion c){
        super();
        this.setPrefSize(100,150);

        this.champion = c;
        button = new Button();

        button.getStylesheets().add(this.getClass().getResource("css/side-icons.css").toExternalForm());
        button.getStyleClass().add(c.getName().substring(0, 2));

        this.getChildren().add(button);
    }


    public void addRedCross(){

        ImageView crossImg = new ImageView(new Image("views/gen_imgs/red-cross.png"));

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);

        button.setEffect(colorAdjust);
//        button.setDisable(true);

        this.getChildren().add(crossImg);
    }

}
