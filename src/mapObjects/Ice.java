package mapObjects;

import main.CollisionHandler;
import main.GamePanel;
import main.SoundManager;
import main.TextureManager;

public class Ice extends ImmovableObject{
    public Ice(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height){
        super(gamePanel, soundManager, textureManager, collisionHandler, x ,y ,width, height);
        this.baseSkin = textureManager.iceImage;
        this.hurtSkin = textureManager.iceImage;
        this.skin = baseSkin;
        this.maxHealth = 500;
        this.health = maxHealth;
    }

    @Override
    public void checkProjectileCollision(){

    }
}
