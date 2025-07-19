package enemies.ghosts;

import enemies.Enemy;
import main.CollisionHandler;
import main.GamePanel;
import main.SoundManager;
import main.TextureManager;

public class GhostPawn extends Enemy {
    public GhostPawn(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 10;
        this.baseSpeed = 5;
        this.speed = baseSpeed;
        this.maxHealth = 1;
        this.health = maxHealth;
        this.baseSkin = textureManager.ghostPawnImage;
        this.hurtSkin = textureManager.ghostPawnHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 40;
    }
}
