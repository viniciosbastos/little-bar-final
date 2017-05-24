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
import javafx.scene.control.ContentDisplay;
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

    public Label label;

    private Semaphore animation;

    public Client(int tc, int tb, String nome) throws URISyntaxException {
        this.tc = tc;
        this.tb = tb;
        this.nome = nome;

        this.imageView = new ImageView();

        this.tcLabel = new Label("" + this.tc);
        this.tbLabel = new Label("" + this.tb);
        this.nomeLabel = new Label("Nome: " + this.nome);
        this.statusLabel = new Label(this.status);

        this.imageView.setImage(downSpriteDefault);
        this.animation = new Semaphore(0);

        this.label = new Label();
        this.label.setText(this.nome);
        this.label.setGraphic(this.imageView);      //Define a imagem quem vai ficar na label
        this.label.setContentDisplay(ContentDisplay.RIGHT);
        this.label.setLayoutX(30);
        this.label.setLayoutY(10);
    }

    protected String getURI(String nome) throws URISyntaxException {
        return getClass().getResource(nome).toURI().toString();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                System.out.println("Cliente " + nome + " está na fila.");
                updateStatus("Na Fila");
                goToQueue();            // Método para animar o caminho até o inicio da fila
                animation.acquire();    // Semáforo para segurar a thread enquanto a animação nao termina, valor inicial 0. Assim que der acquire ela dorme

                LittleBarFinal.bar.sitDown();   //Tenta entrar no bar
                this.chair = LittleBarFinal.bar.getChair(); //Depois de entrar, pergunta ao bar qual cadeira está disponível
                System.out.println("Cliente " + nome + " está procurando cadeira.");
                updateStatus("Procurando Cadeira");
                goToBar();              // Anima até a cadeira
                animation.acquire();
                
                System.out.println("Cliente " + nome + " no bar.");
                updateStatus("No Bar");
                countTime(tb, tbLabel); // Método para substituir o Thread.sleep(), fazendo ele contar o tempo 
                updateLabel(tbLabel, tb);

                LittleBarFinal.bar.getUp(chair);
                System.out.println("Cliente " + nome + " saindo do bar.");
                updateStatus("Indo Embora");
                leave();        //anima até a saída
                animation.acquire();

                updateStatus("Em Casa");
                System.out.println("Cliente " + nome + " em casa.");
                countTime(tc, tcLabel);
                updateLabel(tcLabel, tc);
            } catch (InterruptedException ex) {
                System.out.println("Thread foi interrompida");
                Thread.currentThread().interrupt();
            }
        }
    }

    // Método que retorna a caixa de detalhes que aparece na tela.
    public VBox getDetails() {
        VBox vBox = new VBox();
        vBox.setStyle(
                  "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;"
                + "-fx-border-color: gray;"
                + "-fx-border-radius: 10px;"
                + "-fx-padding: 5px;"
                + "-fx-background-color: white;"
                + "-fx-background-radius: 10px;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

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
            this.label.setLayoutX(61);
            this.label.setLayoutY(15);
            SequentialTransition st = new SequentialTransition();
            st.getChildren().addAll(AnimationFactory.createAnimation(0, 180, label, imageView, downSprites, 5)
            );
            st.setOnFinished(e -> this.animation.release());
            st.play();
        });
    }

    private void goToBar() {
        Platform.runLater(() -> {
            SequentialTransition st = new SequentialTransition();
            if (chair != 9) {
                st.getChildren().addAll(AnimationFactory.createAnimation(89, 0, label, imageView, rightSprites, 2),
                        AnimationFactory.createAnimation(0, getYChairPsotion() - 195, label, imageView, upSprites, 2),
                        AnimationFactory.createAnimation(51, 0, label, imageView, rightSprites, 2)
                );
            } else {
                st.getChildren().addAll(AnimationFactory.createAnimation(78, 0, label, imageView, rightSprites, 2),
                        AnimationFactory.createAnimation(63, 0, label, imageView, rightSprites, 2)
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
                st.getChildren().addAll(AnimationFactory.createAnimation(-41, 0, label, imageView, leftSprites, 2),
                        AnimationFactory.createAnimation(0, 225 - getYChairPsotion(), label, imageView, downSprites, 2),
                        AnimationFactory.createAnimation(-136, 0, label, imageView, leftSprites, 2),
                        AnimationFactory.createAnimation(0, -210, label, imageView, upSprites, 2)
                );
            } else {
                st.getChildren().addAll(AnimationFactory.createAnimation(-41, 0, label, imageView, leftSprites, 2),
                        AnimationFactory.createAnimation(-136, 0, label, imageView, leftSprites, 2),
                        AnimationFactory.createAnimation(0, -210, label, imageView, upSprites, 2)
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
