package views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DamageableBtn extends VBox {

    Button button;
    StackPane stackPane;
    private static final String RED_BAR = "red-bar";
    private static final String YELLOW_BAR = "yellow-bar";
    private static final String ORANGE_BAR = "orange-bar";
    private static final String GREEN_BAR  = "green-bar";
    private static final String[] barColorStyleClasses = { RED_BAR, ORANGE_BAR, YELLOW_BAR, GREEN_BAR };

    public DamageableBtn(int currValue, double maxValue, String stylesheet, String styleClass){
        super();

        stackPane = new StackPane();

        this.setAlignment(Pos.CENTER);
        this.setMaxSize(500,500);
        this.setPrefSize(500,500);

        ProgressBar hpBar = new ProgressBar(currValue/maxValue);
        hpBar.setPrefSize(85,12);
        hpBar.getStylesheets().add(getClass().getResource("css/progress.css").toExternalForm());
        hpBar.getStyleClass().add("green-bar");


        double progress = (double) currValue/maxValue;

        if (progress < 0.3) {
            setBarStyleClass(hpBar, RED_BAR);
        } else if (progress < 0.5) {
            setBarStyleClass(hpBar, ORANGE_BAR);
        } else {
            setBarStyleClass(hpBar, GREEN_BAR);
        }

        hpBar.setOpacity(0);

        button = new Button();

        button.getStylesheets().add(this.getClass().getResource("css/" + stylesheet + ".css").toExternalForm());
        button.getStyleClass().add(styleClass);

        button.setOnMouseEntered(e -> {
            hpBar.setOpacity(1);
        });

        button.setOnMouseExited(e -> {
            hpBar.setOpacity(0);
        });


        this.getChildren().addAll(hpBar, button);
    }

    public static void setBarStyleClass(ProgressBar bar, String barStyleClass) {
        bar.getStyleClass().removeAll(barColorStyleClasses);
        bar.getStyleClass().add(barStyleClass);
    }

}
