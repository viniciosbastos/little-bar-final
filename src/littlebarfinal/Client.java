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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    private int offset;

    public Client(int tc, int tb, String nome) throws URISyntaxException {
        this.tc = tc;
        this.tb = tb;
        this.nome = nome;

        this.imageView = new ImageView();
        this.imageView.setX(7);
        this.imageView.setY(30);
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
            this.imageView.setX(100);
            this.imageView.setY(10);
        });
    }

    private void goBar() {
        Platform.runLater(() -> {
            this.imageView.setX(200);
            this.imageView.setY(100 + (14 * this.offset));
        });
    }

    private void goHome() {
        Platform.runLater(() -> {
            this.imageView.setX(100 + (14 * this.offset));
            this.imageView.setY(200);
        });
    }

    @Override
    public void run() {
        while (true) {
            try {
                enterQueue();
                this.offset = LittleBarFinal.bar.sitDown();
                System.out.println(this.nome + " sentou na cadeira " + this.offset);
                goBar();
                Thread.sleep(tb * 1000);
                LittleBarFinal.bar.getUp(this.offset);
                goHome();
                Thread.sleep(tc * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
