package enemies.zombies;

import enemies.Enemy;
import main.CollisionHandler;
import main.GamePanel;
import main.SoundManager;
import main.TextureManager;

public class ZombiePawn extends Enemy {
    public ZombiePawn(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 10;
        this.speed = 1;
        this.health = 50;
        this.baseSkin = textureManager.enemyPawnImage;
        this.hurtSkin = textureManager.enemyPawnHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 160;
    }
}
