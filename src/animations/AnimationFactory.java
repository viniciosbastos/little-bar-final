/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animations;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author Vinicios
 */
public class AnimationFactory {
    
    public static Animation createAnimation(int toX, int toY, ImageView imageView, Image[] sprites) {
        TranslateTransition tt = new TranslateTransition();        
        tt.setByX(toX);
        tt.setByY(toY);
        tt.setCycleCount(1);
        tt.setDuration(Duration.seconds(5));
        tt.setNode(imageView);
        tt.setOnFinished(e -> {
            imageView.setX(imageView.getX() + imageView.getTranslateX());
            imageView.setY(imageView.getY() + imageView.getTranslateY());
            imageView.setTranslateX(0);
            imageView.setTranslateY(0);
        });
        
        SpriteAnimation sa = new SpriteAnimation(imageView, sprites);        
        sa.setCycleCount(1);      
        
        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(tt, sa);
        pt.setNode(imageView);
        pt.setCycleCount(1);
        
        return pt;
    }
    
}
