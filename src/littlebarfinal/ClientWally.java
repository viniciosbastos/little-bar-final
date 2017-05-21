/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlebarfinal;

import java.net.URISyntaxException;
import javafx.scene.image.Image;

/**
 *
 * @author Vinicios
 */
public class ClientWally extends Client{
    
    public ClientWally(int tc, int tb, String nome) throws URISyntaxException {
        super(tc, tb, nome);
        
        this.upSprites = new Image[]{new Image(getURI("/images/3/up-1.png")), new Image(getURI("/images/3/up-2.png"))};
        this.downSprites = new Image[]{new Image(getURI("/images/3/down-1.png")), new Image(getURI("/images/3/down-2.png"))};
        this.leftSprites = new Image[]{new Image(getURI("/images/3/left-0.png")), new Image(getURI("/images/3/left-1.png"))};
        this.rightSprites = new Image[]{new Image(getURI("/images/3/right-0.png")), new Image(getURI("/images/3/right-1.png"))};

        upSpriteDefault = new Image(getURI("/images/3/up-0.png"));
        downSpriteDefault = new Image(getURI("/images/3/down-0.png"));
        rightSpriteDefault = new Image(getURI("/images/3/left-0.png"));
        leftSpriteDefault = new Image(getURI("/images/3/right-0.png"));
        
        this.imageView.setImage(downSpriteDefault);
    }
    
}
