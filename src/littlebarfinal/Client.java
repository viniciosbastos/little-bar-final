/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlebarfinal;

import animations.AnimationFactory;
import java.net.URISyntaxException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Vinicios
 */
public class Client extends Thread {

    public ImageView imageView;
    public Label label;

    protected Image[] upSprites;
    protected Image[] downSprites;
    protected Image[] rightSprites;
    protected Image[] leftSprites;

    protected Image upSpriteDefault;
    protected Image downSpriteDefault;
    protected Image rightSpriteDefault;
    protected Image leftSpriteDefault;

    private int tc, tb;
    private String nome;
    private String status;

    private int chair;

    private Label tcLabel, tbLabel, nomeLabel, statusLabel;
    
    private Semaphore animation;

    public Client(int tc, int tb, String nome) throws URISyntaxException {
        this.tc = tc;
        this.tb = tb;
        this.nome = nome;

        this.imageView = new ImageView();

        this.imageView.setX(213);
        this.imageView.setY(55);

        this.tcLabel = new Label("" + this.tc);
        this.tbLabel = new Label("" + this.tb);
        this.nomeLabel = new Label("Nome: " + this.nome);
        this.statusLabel = new Label(this.status);
        
        this.animation = new Semaphore(0);
    }

    protected String getURI(String nome) throws URISyntaxException {
        return getClass().getResource(nome).toURI().toString();
    }

    private void enterQueue() {
        Platform.runLater(() -> {
            this.label.setLayoutX(100);
            this.label.setLayoutY(10);
        });
    }

    private void goBar() {
        Platform.runLater(() -> {
            this.label.setLayoutX(200);
            this.label.setLayoutY(30 + (40 * this.chair));
        });
    }

    private void goHome() {
        Platform.runLater(() -> {
            this.label.setLayoutX(100 + (16 * this.chair));
            this.label.setLayoutY(200);
        });
    }

    @Override
    public void run() {
        while (true) {
            try {
                goToQueue();
                animation.acquire();
//                enterQueue();
                updateStatus("Na Fila");

                LittleBarFinal.bar.sitDown();
                this.chair = LittleBarFinal.bar.getChair();
                goToBar();
                animation.acquire();
                updateStatus("No Bar");
//                goBar();
                countTime(tb, tbLabel);
                updateLabel(tbLabel, tb);

                LittleBarFinal.bar.getUp(chair);
                leave();
                animation.acquire();
                updateStatus("Em Casa");
//                goHome();
                countTime(tc, tcLabel);
                updateLabel(tcLabel, tc);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public VBox getDetails() {
        VBox vBox = new VBox();
        vBox.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 1;"
                + "-fx-border-color: gray;");

        vBox.getChildren().add(this.nomeLabel);

        HBox hBox = new HBox();
        this.tcLabel.setPrefWidth(50);
        this.tbLabel.setPrefWidth(50);
        hBox.getChildren().addAll(new Label("Casa: "), this.tcLabel, new Label("Bar: "), this.tbLabel);

        vBox.getChildren().add(hBox);
        vBox.getChildren().add(this.statusLabel);
        return vBox;
    }

    private void updateLabel(Label l, int value) {
        Platform.runLater(() -> {
            l.setText("" + value);
        });
    }

    private void updateStatus(String status) {
        Platform.runLater(() -> {
            this.statusLabel.setText(status);
        });
    }

    private void countTime(int seconds, Label toUpdate) {
        for (int i = seconds; i > 0; i--) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 1000) {
            }
            updateLabel(toUpdate, i - 1);
        }
    }

    private void goToQueue() {
        Platform.runLater(() -> {
            this.imageView.setX(72);
            this.imageView.setY(15);
            SequentialTransition st = new SequentialTransition();
            st.getChildren().addAll(
                    AnimationFactory.createAnimation(0, 180, imageView, downSprites, 5)
            );
            st.setOnFinished(e -> this.animation.release());
            st.play();
        });
    }

    private void goToBar() {        
        Platform.runLater(() -> {
            SequentialTransition st = new SequentialTransition();
            if (chair != 9) {
                st.getChildren().addAll(
                        AnimationFactory.createAnimation(89, 0, imageView, rightSprites, 2),
                        AnimationFactory.createAnimation(0, getYChairPsotion() - 195, imageView, upSprites, 2),
                        AnimationFactory.createAnimation(52, 0, imageView, rightSprites, 2)
                );
            } else {
                st.getChildren().addAll(
                        AnimationFactory.createAnimation(89, 0, imageView, rightSprites, 2),
                        AnimationFactory.createAnimation(52, 0, imageView, rightSprites, 2)
                );
            }
            st.setOnFinished(e -> this.animation.release());
            st.play();
        });
    }

    private void leave() {
        Platform.runLater(() -> {
            SequentialTransition st = new SequentialTransition();
            if (chair != 9) {
                st.getChildren().addAll(
                        AnimationFactory.createAnimation(-52, 0, imageView, leftSprites, 2),
                        AnimationFactory.createAnimation(0, 225-getYChairPsotion(), imageView, downSprites, 2),
                        AnimationFactory.createAnimation(-127, 0, imageView, leftSprites, 2),
                        AnimationFactory.createAnimation(0, -210, imageView, upSprites, 2)
                );
            } else {
                st.getChildren().addAll(
                        AnimationFactory.createAnimation(-52, 0, imageView, leftSprites, 2),
                        AnimationFactory.createAnimation(-127, 0, imageView, leftSprites, 2),
                        AnimationFactory.createAnimation(0, -210, imageView, upSprites, 2)
                );
            }
            st.setOnFinished(e -> {
                this.animation.release();
                this.imageView.setY(-100);
            });
            st.play();
        });
    }

    private int getYChairPsotion() {
        int y = 0;
        switch (this.chair) {
            case 1:
                y = 55;
                break;
            case 2:
                y = 73;
                break;
            case 3:
                y = 90;
                break;
            case 4:
                y = 105;
                break;
            case 5:
                y = 122;
                break;
            case 6:
                y = 140;
                break;
            case 7:
                y = 160;
                break;
            case 8:
                y = 177;
                break;
            case 9:
                y = 195;
                break;
        }
        return y;
    }

}
