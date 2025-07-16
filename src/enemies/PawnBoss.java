package enemies;

import main.CollisionHandler;
import main.GamePanel;
import main.SoundManager;

public class PawnBoss extends Enemy{

    public PawnBoss(GamePanel gamePanel, SoundManager soundManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        super(gamePanel, soundManager, collisionHandler, x, y, width, height);
        this.damage = 20;
        this.speed = 1;
        this.maxHealth = 500;
        this.health = maxHealth;
        this.baseSkin = gamePanel.enemyPawnImage;
        this.hurtSkin = gamePanel.enemyPawnHurtImage;
        this.skin = baseSkin;
        this.width = width * 2;
        this.height = height * 2;
        this.attackCoolDown = 80;
    }
    @Override
    void checkAlive(){
        if (health <= 0){
            this.isDead = true;
            gamePanel.score+=maxHealth;
            gamePanel.rebuildOnePawn();
            soundManager.playClip(soundManager.deathClip);

        }
    }
}
