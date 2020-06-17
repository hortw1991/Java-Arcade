package cs1302.arcade;

import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.animation.PathTransition;
import javafx.scene.shape.Path;
import cs1302.arcade.ReversiScene;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.CubicCurveTo;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.RotateTransition; 
import javafx.animation.FadeTransition;
import java.util.Timer;
import java.util.TimerTask;

import cs1302.arcade.CentipedeGame;
import cs1302.arcade.CentipedeScene;
import javafx.scene.text.Font;


/**
 * Application subclass for {@code ArcadeApp}.
 * @version 2019.fa
 */
public class ArcadeApp extends Application {
/**
 * 
 */
    Timeline tl;
    KeyFrame kf;
    Text welcome;
    Text controls;
    Label reversiLabel;
    Label centipedeLabel;
    Circle ball1 = new Circle(20, Color.YELLOW);
    Canvas c;
    Group group = new Group();           // main container
    Random rng = new Random();           // random number generator
    Rectangle r = new Rectangle(200, 100); // some rectangle
    Rectangle a = new Rectangle(200, 100, Color.BLUE);
    Scene defaultScene;
    Scene animatedScene;
    public Stage stage;
    BorderPane pane = new BorderPane();
    VBox boxy = new VBox(8);

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;

        // Basic hbox setup
        reversiLabel = new Label("Reversi");
        centipedeLabel = new Label("Centipede");
        boxy.setAlignment(Pos.CENTER); 
        boxy.getChildren().addAll(reversiLabel, r, centipedeLabel, a);
        
        c = new Canvas(250, 250);
        group.getChildren().addAll(c, ball1);

        // Mouse handlers
        EventHandler<? super MouseEvent> reversiHandler = e -> {
            setReversiScene();

        };
        r.setOnMouseClicked(reversiHandler); // clicks on the rectangle move it randomly

        EventHandler<? super MouseEvent> centipedeHandler = o -> {
            setCentipedeScene();
        };
        a.setOnMouseClicked(centipedeHandler);

        // Text explanations
        welcome = new Text();
        welcome.setText("\t\tWelcome to the Arcade App!\nSelect Reversi or Centipede below!");
        welcome.setFont(new Font(20));
        controls = new Text();
        String name = "";
        name += "\t\tThomas Horn & Stephanie Breed";
        name += "\n\t\t\tTeam ST";
        name += "\n(Please use the 'z' key to fire bullets in Centipede)";
        controls.setText(name);
        
        // Add to the borderpane
        pane.setCenter(boxy);
        BorderPane.setAlignment(boxy, Pos.CENTER);
        pane.setTop(welcome);
        BorderPane.setAlignment(welcome, Pos.CENTER);
        pane.setBottom(controls);
        BorderPane.setAlignment(controls, Pos.CENTER);
        pane.setRight(group);

        long time = System.currentTimeMillis();
        long end = time + 10000;
        while (time < end) {
            time = System.currentTimeMillis();
        }
        startTransitions();

        // Now call the main scene

        // the group must request input focus to receive key events
        // @see https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#requestFocus--
        group.requestFocus();


    } // start

    /**
     * Starts the transitions for the main screen.
     */
    private void startTransitions() {
        // Animations
        FadeTransition ft = new FadeTransition(Duration.millis(15000), boxy);
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

        FadeTransition ft2 = new FadeTransition(Duration.millis(7000), controls);
        ft2.setFromValue(0);
        ft2.setToValue(1.0);
        ft2.setCycleCount(1);
        ft2.setAutoReverse(false);
        ft2.play();

        FadeTransition ft3 = new FadeTransition(Duration.millis(7000), welcome);
        ft3.setFromValue(0);
        ft3.setToValue(1.0);
        ft3.setCycleCount(1);
        ft3.setAutoReverse(false);
        ft3.play();

        Path path = new Path();
        ball1.setCenterX(10);
        ball1.setCenterY(10);

        path.getElements().add (new MoveTo (150f, 70f));  
        path.getElements().add (new CubicCurveTo (240f, 230f, 500f, 340f, 600, 50f));  
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(1000));
        pt.setNode(ball1);
        pt.setPath(path);
        pt.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount(10);
        pt.play();

        
        defaultScene = new Scene(pane, 640, 480);
        stage.setTitle("cs1302-arcade!");
        stage.sizeToScene();
        setMainScene();
        stage.show();

    }


    /**
     * Changes the scene to the first scene.
     */
    public void setMainScene() {
        stage.setScene(defaultScene);
    }

    /**
     * Changes the scene to the main reversi scene.
     */
    public void setReversiScene() {
        stage.setScene(new ReversiScene(this));
    }

    /**
     * Sets the centipede Scene.
     */
    public void setCentipedeScene() {
        CentipedeGame g = new CentipedeGame(this);
        CentipedeScene a = new CentipedeScene(g);
        stage.setScene(a);

        a.setOnKeyPressed(g.createKeyHandler());
    }

 
} // ArcadeApp
