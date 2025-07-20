package mapObjects;

import main.CollisionHandler;
import main.GamePanel;
import main.SoundManager;
import main.TextureManager;

public class Rock extends MapObject {
    public Rock(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height){
       super(gamePanel, soundManager, textureManager, collisionHandler, x ,y ,width, height);
       this.baseSkin = textureManager.rockImage;
       this.hurtSkin = textureManager.rockImage;
       this.skin = baseSkin;
       this.maxHealth = 500;
       this.health = maxHealth;
    }

}
