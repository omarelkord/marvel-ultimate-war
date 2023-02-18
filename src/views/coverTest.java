package views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class coverTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        VBox vBox = new VBox();

        DamageableBtn damageableBtn = new DamageableBtn(500, 1000, "icons", "Dr");
        damageableBtn.setPrefSize(200,200);
        damageableBtn.setMaxSize(200,200);

        damageableBtn.button.setOnAction(e ->{
            System.out.println("pressed");
        });

        vBox.getChildren().add(damageableBtn);

        Scene scene = new Scene(vBox);
        stage.setWidth(1000);
        stage.setHeight(500);

        stage.show();
        stage.setScene(scene);
    }
}
