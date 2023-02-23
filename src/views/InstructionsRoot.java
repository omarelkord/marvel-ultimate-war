package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;



public class InstructionsRoot extends StackPane {

    public InstructionsRoot(int i){

        VBox root = new VBox(i);

        root.setStyle("-fx-background-image: url('views/bg_imgs/selectionBG.png');");
        root.setAlignment(Pos.CENTER);

        Label title = new Label("GAME RULES");
        title.setPadding(new Insets(0,0,0,0));

        title.getStylesheets().add(this.getClass().getResource("css/game-font.css").toExternalForm());
        title.getStyleClass().add("instruction-title");

        VBox instructions = new VBox(20);

        Label move = new Label("1. To move your champion press the \"MOVE\" button to activate your character's movement.\n" +
                "  Once the movement is activated, use the following keys to select the direction you want to move:\n" +
                "  \"W\" to move up\n" +
                "  \"A\" to move left\n" +
                "  \"S\" to move down\n" +
                "  \"D\" to move right");

        Label attack = new Label("2. To attack press the \"ATTACK\" button to activate your attack.\n" +
                "    Use the WASD keys to select the direction you want to attack:");

        Label ability = new Label("3. Look at the top of your screen where you will see the ability buttons.\n" +
                "   Click on the corresponding button to activate that ability.\n" +
                "   Hover on the corresponding button to view the ability's details.\n" +
                "   Directional Abilities: use WASD keys to indicate direction. \n" +
                "   Single Target Abilities: Click on the champion on the board");

        Label leaderAbility = new Label("4. To activate your leader ability press the \"LEADER\" button.");

        Label champInfo = new Label("5. Press on the champions side icons to view their stats");

        Label endturn = new Label("7. To end your turn press the \"END TURN\" button.");

        Label coversHP = new Label("6. Hover on the covers on yhe board to view their current HP");


        move.getStylesheets().add(this.getClass().getResource("css/game-font.css").toExternalForm());
        move.getStyleClass().add("instructions");

        ability.getStylesheets().add(this.getClass().getResource("css/game-font.css").toExternalForm());
        ability.getStyleClass().add("instructions");

        attack.getStylesheets().add(this.getClass().getResource("css/game-font.css").toExternalForm());
        attack.getStyleClass().add("instructions");

        leaderAbility.getStylesheets().add(this.getClass().getResource("css/game-font.css").toExternalForm());
        leaderAbility.getStyleClass().add("instructions");

        champInfo.getStylesheets().add(this.getClass().getResource("css/game-font.css").toExternalForm());
        champInfo.getStyleClass().add("instructions");

        endturn.getStylesheets().add(this.getClass().getResource("css/game-font.css").toExternalForm());
        endturn.getStyleClass().add("instructions");

        coversHP.getStylesheets().add(this.getClass().getResource("css/game-font.css").toExternalForm());
        coversHP.getStyleClass().add("instructions");

        instructions.getChildren().addAll( move,attack,ability, leaderAbility , champInfo, coversHP,endturn);
        instructions.setAlignment(Pos.CENTER_LEFT);
        instructions.setPadding(new Insets(0,0,0,280));

        HBox next = new HBox();
        Button nextBtn = new Button();

        next.getChildren().add(nextBtn);
        next.setAlignment(Pos.BOTTOM_RIGHT);
        next.setPadding(new Insets(0,25,0,0));
        nextBtn.setOnAction(e->{
            Controller.onSwitchToMap();
        });
        nextBtn.getStylesheets().add(this.getClass().getResource("css/instructions.css").toExternalForm());
        nextBtn.getStyleClass().add("next");

        root.getChildren().addAll(title,instructions, next);

        ImageView border = new ImageView(new Image("views/gen_imgs/borderInstructions-img.png"));

        this.getChildren().addAll(root,border);

    }
}
