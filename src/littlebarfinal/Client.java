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
public class Client extends Thread{
    
    public ImageView imageView;
    
    private Image[] upSprites;
    private Image[] downSprites;
    private Image[] rightSprites;
    private Image[] leftSprites;
    
    private Image upSpriteDefault;
    private Image downSpriteDefault;
    private Image rightSpriteDefault;
    private Image leftSpriteDefault;
    
    public Client() throws URISyntaxException {
        this.imageView = new ImageView();
        this.imageView.setX(7);
        this.imageView.setY(30);
        

        this.upSprites = new Image[] {new Image(getURI("/images/1/up-1.png")), new Image(getURI("/images/1/up-2.png"))};
        this.downSprites = new Image[] {new Image(getURI("/images/1/down-1.png")), new Image(getURI("/images/1/down-2.png"))};
        this.leftSprites = new Image[] {new Image(getURI("/images/1/left-0.png")), new Image(getURI("/images/1/left-1.png"))};
        this.rightSprites = new Image[] {new Image(getURI("/images/1/right-0.png")), new Image(getURI("/images/1/right-1.png"))};

        upSpriteDefault = new Image(getURI("/images/1/up-0.png"));
        downSpriteDefault = new Image(getURI("/images/1/down-0.png"));
        rightSpriteDefault = new Image(getURI("/images/1/left-0.png"));
        leftSpriteDefault = new Image(getURI("/images/1/right-0.png"));
    }
    
    private String getURI(String nome) throws URISyntaxException {
        return getClass().getResource(nome).toURI().toString();
    }
    
    @Override
    public void run() {
        Platform.runLater(() -> {
            SequentialTransition st = new SequentialTransition();
            st.getChildren().addAll(
                    AnimationFactory.createAnimation(0, 100, imageView, downSprites),
                    AnimationFactory.createAnimation(100, 0, imageView, rightSprites)
            );
            st.play();
        });
    }
}
