//I worked on the homework assignment alone, using only course materials.
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.scene.control.ColorPicker;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.shape.StrokeLineCap;

/**
* This class represents an CSPaint class.
* @author Rashmi Athavale
* @version 1.0
*/
public class CSPaint extends Application {
    private final URL resource = getClass().getResource("eightiesJam.wav");
    private final AudioClip clip = new AudioClip(resource.toString());
    private Image image = new Image(getClass().getResource("play.png").toExternalForm(), 20, 20, true, true);
    private Image image2 = new Image(getClass().getResource("stop.png").toExternalForm(), 23, 23, true, true);
    private Paint p = new Paint();
    private ColorPicker cp = new ColorPicker();
    private AtomicInteger shapeCount = new AtomicInteger();
    private HBox hBox = new HBox(10);
    private VBox vBox = new VBox(10);
    private Canvas canvas = new Canvas(650, 450);
    private Text text2 = new Text();
    private Button btn2 = new Button("Play", new ImageView(image));
    private Button btn3 = new Button("Stop", new ImageView(image2));
    private Button btn = new Button("Clear Canvas");
    private Label label = new Label("Click enter to change color");
    private Pane tempPane = new Pane();
    private ToggleGroup group = new ToggleGroup();
    private RadioButton rbDraw = new RadioButton("Draw");
    private RadioButton rbErase = new RadioButton("Erase");
    private RadioButton rbCircle = new RadioButton("Circle");
    private RadioButton rbRect = new RadioButton("Rectangle");
    private BorderPane border = new BorderPane();
    private TextField text = new TextField();
    private Text text1 = new Text("Number of Shape: " + shapeCount.get());
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private boolean firstTime = true;
    private boolean erased;
    private Color previous;
    /**
    * @param primaryStage represents the primary window of this JavaFX application
    */
    public void start(Stage primaryStage) {
        clip.setCycleCount(Animation.INDEFINITE);
        tempPane.setStyle("-fx-background-color: white;");
        tempPane.getChildren().add(canvas);
        vBox.getChildren().addAll(rbDraw, rbErase, rbCircle, rbRect, text, label, cp, btn2, btn3, btn);
        border.setLeft(vBox);
        border.setCenter(tempPane);
        rbDraw.setToggleGroup(group);
        rbErase.setToggleGroup(group);
        rbCircle.setToggleGroup(group);
        rbRect.setToggleGroup(group);
        text.setOnAction(
            e -> {
                try {
                    if (text.getText().isEmpty()) {
                        firstTime = false;
                        gc.setStroke(Color.BLACK);
                        previous = Color.BLACK;
                        gc.setFill(Color.BLACK);
                    } else {
                        gc.setStroke(Color.valueOf(text.getText()));
                        firstTime = false;
                        previous = Color.valueOf(text.getText());
                        gc.setFill(Color.valueOf(text.getText()));
                    }
                } catch (Exception ex) {
                    p.alert();
                }
            }
        );
        tempPane.setOnMousePressed(
            e -> {
                if (rbDraw.isSelected()) {
                    if (firstTime) {
                        gc.setStroke(Color.BLACK);
                        previous = Color.BLACK;
                        firstTime = false;
                    }
                    if (erased) {
                        gc.setStroke(previous);
                        erased = false;
                    }
                    gc.setLineCap(StrokeLineCap.ROUND);
                    gc.setLineWidth(4);
                    gc.beginPath();
                    gc.moveTo(e.getX(), e.getY());
                    gc.lineTo(e.getX(), e.getY());
                    gc.stroke();
                } else if (rbErase.isSelected()) {
                    erased = true;
                    gc.setLineWidth(20);
                    gc.setLineCap(StrokeLineCap.ROUND);
                    gc.beginPath();
                    gc.setStroke(cp.getValue());
                    gc.moveTo(e.getX(), e.getY());
                    gc.lineTo(e.getX(), e.getY());
                    gc.stroke();
                }
            });
        tempPane.setOnMouseDragged(
            te -> {
                if (rbDraw.isSelected()) {
                    gc.lineTo(te.getX(), te.getY());
                    gc.stroke();
                } else if (rbErase.isSelected()) {
                    gc.lineTo(te.getX(), te.getY());
                    gc.stroke();
                }
            });
        tempPane.setOnMouseMoved(
            pe -> {
                text2.setText("(" + pe.getX() + ", " + pe.getY() + ")");
            });
        btn2.setOnAction(
            e -> {
                clip.play();
            });
        btn3.setOnAction(
            e -> {
                clip.stop();
            });
        btn.setOnAction(
            e -> {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                shapeCount.set(0);
                text1.setText("Number of Shape: " + shapeCount.get());
            });
        cp.setOnAction(
            c -> {
                Color col = cp.getValue();
                String str = col.toString();
                str = str.substring(2, str.length());
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                shapeCount.set(0);
                text1.setText("Number of Shape: " + shapeCount.get());
                tempPane.setStyle("-fx-background-color: #" + str + ";");
            });
        tempPane.setOnMouseClicked(
            ae -> {
                if (rbRect.isSelected()) {
                    gc.fillRect(ae.getX() - 25, ae.getY() - 12.5, 50, 25);
                    shapeCount.getAndIncrement();
                    text1.setText("Number of Shape: " + shapeCount.get());
                } else if (rbCircle.isSelected()) {
                    gc.fillOval(ae.getX() - 15, ae.getY() - 15, 30, 30);
                    shapeCount.getAndIncrement();
                    text1.setText("Number of Shape: " + shapeCount.get());
                }
            }
        );
        hBox.getChildren().add(text2);
        hBox.getChildren().add(text1);
        border.setBottom(hBox);
        Scene scene = new Scene(border);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setTitle("CSPaint");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
