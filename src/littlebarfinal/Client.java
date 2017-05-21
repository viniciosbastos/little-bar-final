/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlebarfinal;

import animations.AnimationFactory;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
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

    private int offset;
    
    private Label tcLabel, tbLabel, nomeLabel, statusLabel;
       

    public Client(int tc, int tb, String nome) throws URISyntaxException {
        this.tc = tc;
        this.tb = tb;
        this.nome = nome;

        this.imageView = new ImageView();
        this.imageView.setX(7);
        this.imageView.setY(30);
        this.label = new Label();
        this.label.setText(this.nome);
        this.label.setGraphic(this.imageView);
        this.label.setContentDisplay(ContentDisplay.TOP);
        
        this.tcLabel = new Label("" + this.tc);
        this.tbLabel = new Label("" + this.tb);         
        this.nomeLabel = new Label("Nome: " + this.nome);
        this.statusLabel = new Label(this.status);
    }

    protected String getURI(String nome) throws URISyntaxException {
        return getClass().getResource(nome).toURI().toString();
    }

    private void animate() {
        Platform.runLater(() -> {
            SequentialTransition st = new SequentialTransition();
            st.getChildren().addAll(
                    AnimationFactory.createAnimation(0, 100, imageView, downSprites),
                    AnimationFactory.createAnimation(100, 0, imageView, rightSprites)
            );
            st.play();
        });
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
            this.label.setLayoutY(30 + (40 * this.offset));
        });
    }

    private void goHome() {
        Platform.runLater(() -> {
            this.label.setLayoutX(100 + (16 * this.offset));
            this.label.setLayoutY(200);
        });
    }

    @Override
    public void run() {
        while (true) {
            try {                
                enterQueue();
                updateStatus("Na Fila");

                this.offset = LittleBarFinal.bar.sitDown();
                updateStatus("No Bar");
                System.out.println(nome + " no bar");
                goBar();
                //countTime(tb, tbLabel);
                Thread.sleep(1000*tb);
                updateLabel(tbLabel, tb);

                LittleBarFinal.bar.getUp(offset);
                updateStatus("Em Casa");
                System.out.println(nome + " em casa");
                goHome();
                //countTime(tc, tcLabel);
                Thread.sleep(1000*tc);
                updateLabel(tcLabel, tc);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public VBox getDetails() {
        VBox vBox = new VBox();
        vBox.setStyle("-fx-border-style: solid inside;" + 
                      "-fx-border-width: 1;" +
                      "-fx-border-color: gray;");
        
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
            while ((System.currentTimeMillis() - start) != 1000){}
            updateLabel(toUpdate, i-1);
        }
    }
}
