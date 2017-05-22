/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animations;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


/**
 *
 * @author Vinicios
 */
public class SpriteAnimation extends Transition {
    private int c = 0;
    private int i = 0;
    private ImageView imageView;    
    private Image[] sprites;
    
    public SpriteAnimation(ImageView imageView, Image[] sprites, int duration) {
        this.imageView = imageView;
        this.sprites = sprites;
        setCycleDuration(Duration.seconds(duration));
    }
    
    @Override
    protected void interpolate(double frac) {
        if (c == 12) {            
            imageView.setImage(sprites[i%2]);
            c=0;
            i++;
        } else {
            c++;
        }
        
    }
}
