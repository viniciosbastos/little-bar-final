/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animations;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author Vinicios
 */
public class AnimationFactory {
    
    public static Animation createAnimation(int toX, int toY, Label label, ImageView imageView, Image[] sprites, int duration) {
        TranslateTransition tt = new TranslateTransition();        
        tt.setByX(toX);
        tt.setByY(toY);
        tt.setCycleCount(1);
        tt.setDuration(Duration.seconds(duration));
        tt.setNode(label);
        tt.setOnFinished(e -> {
            label.setLayoutX(label.getLayoutX() + label.getTranslateX());
            label.setLayoutY(label.getLayoutY() + label.getTranslateY());
            label.setTranslateX(0);
            label.setTranslateY(0);
        });
        
        SpriteAnimation sa = new SpriteAnimation(imageView, sprites, duration);        
        sa.setCycleCount(1);      
        
        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(tt, sa);
        pt.setNode(imageView);
        pt.setCycleCount(1);
        
        return pt;
    }
    
}
