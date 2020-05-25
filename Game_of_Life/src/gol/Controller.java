/**
 *
 * @Author : S18610
 *
 */

package gol;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.geometry.Point2D;


public class Controller {

    // main UI elements:
    @FXML
    private Button nextMoveButton;
    @FXML
    private Button clearButton;
    @FXML
    private RadioButton drawModeButton;
    @FXML
    private RadioButton eraseModeButton;
    @FXML
    private Canvas canvas;
    @FXML
    private Label aliveCounter;
    @FXML
    private Label coordinates;

    // important objects
    private Affine affine;
    private Automata automata;

    public Controller() {
        this.affine = new Affine();

        this.affine.appendScale(400 / 10f, 400 / 10f);

        this.automata = new Automata(10,10);
    }

    public void nextMove(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(nextMoveButton)) {
            automata.move();
            drawBoard();
        }
    }

    public void clear(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(clearButton)) {

            for (int i = 0; i < this.automata.width; i++) {
                for (int j = 0; j < this.automata.height; j++) {
                    automata.setDead(i,j);
                    drawBoard();
                }
            }
            countAliveCells();
            System.out.println("board cleared\n");
        }
    }

    public void drawMode(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(drawModeButton)) {
            drawModeButton.setSelected(true);
            eraseModeButton.setSelected(false);

            System.out.println("drawing mode activated\n");
            drawErase("draw");
        }
    }

    public void eraseMode(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(eraseModeButton)) {
            eraseModeButton.setSelected(true);
            drawModeButton.setSelected(false);

            System.out.println("erasing mode activated\n");
            drawErase("erase");
        }
    }

    public void drawBoard() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,400,400);

        g.setFill(Color.BLACK);
        for (int i = 0; i < this.automata.width; i++) {
            for (int j = 0; j < this.automata.height; j++) {
                if (this.automata.getState(i, j) == 1) {
                    g.fillRect(i, j, 1, 1);
                }
            }
        }

        // grid
        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05f);
        for (int i = 0; i <= this.automata.width; i++) {
            g.strokeLine(i, 0, i, 10);
        }

        for (int j = 0; j <= this.automata.height; j++) {
            g.strokeLine(0, j, 10, j);
        }
        
    }

    public void drawErase(String decision) {
        this.canvas.setOnMousePressed(mouseEvent -> {

            try {
                Point2D coords = this.affine.inverseTransform(mouseEvent.getX(), mouseEvent.getY());

                int x = (int)coords.getX();
                int y = (int)coords.getY();

                if (decision.equals("draw")) {
                    this.automata.setAlive(x, y);
                    drawBoard();

                } else if (decision.equals("erase")) {
                    this.automata.setDead(x, y);
                    drawBoard();
                }

                countAliveCells();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }


    public void showPos() {
        this.canvas.setOnMouseMoved(mouseEvent -> {

            try {
                Point2D coords = this.affine.inverseTransform(mouseEvent.getX(), mouseEvent.getY());

                String str = ("x: " + (int)coords.getX() + ", y:" + (int)coords.getY());
                coordinates.setText(str);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public void countAliveCells() {
        int counter = 0;

        for (int i = 0; i < this.automata.width; i++) {
            for (int j = 0; j < this.automata.height; j++) {
                if (this.automata.getState(i, j) == 1) {
                   counter++;
                }
            }
        }

        aliveCounter.setText("alive: " + counter);
    }


    @FXML
    public void initialize() {
        drawBoard();
        drawModeButton.setSelected(true);
        drawErase("draw");
        showPos();
        countAliveCells();
    }

}
