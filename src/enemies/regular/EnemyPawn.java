package enemies.regular;

import enemies.Enemy;
import main.CollisionHandler;
import main.GamePanel;
import main.SoundManager;
import main.TextureManager;

public class EnemyPawn extends Enemy {
    public EnemyPawn(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 10;
        this.baseSpeed = 3;
        this.speed = baseSpeed;
        this.slowedSpeed = 1;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.baseSkin = textureManager.enemyPawnImage;
        this.hurtSkin = textureManager.enemyPawnHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 80;
    }
}
