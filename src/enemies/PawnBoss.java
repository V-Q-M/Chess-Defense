package enemies;

import main.CollisionHandler;
import main.GamePanel;
import main.SoundManager;
import main.TextureManager;

public class PawnBoss extends Enemy{

    public PawnBoss(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 20;
        this.speed = 1;
        this.maxHealth = 500;
        this.health = maxHealth;
        this.baseSkin = textureManager.enemyPawnImage;
        this.hurtSkin = textureManager.enemyPawnHurtImage;
        this.skin = baseSkin;
        this.width = width * 2;
        this.height = height * 2;
        this.attackCoolDown = 80;
        this.isBoss = true;
    }
    @Override
    void checkAlive(){
        if (health <= 0){
            this.isDead = true;
            gamePanel.score+=maxHealth;
            gamePanel.pawnBossSlain = true;
            soundManager.playClip(soundManager.deathClip);

        }
    }
}
