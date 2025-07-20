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

    private boolean hasTransformed = false;

    @Override
    protected void checkAlive(){
        if (health <= 0 && !hasTransformed) {
            hasTransformed = true;
            transformIntoZombie();
        } else if (health <= 0) {
            this.isDead = true;
            gamePanel.score+=maxHealth;
            //soundManager.playClip(soundManager.deathClip);
            soundManager.playClip("death");
        }
    }

    private void transformIntoZombie(){
        //soundManager.playClip(soundManager.zombieSpawnClip);
        soundManager.playClip("zombieSpawn");
        this.damage = 10;
        this.baseSpeed = 1;
        this.speed = baseSpeed;
        this.maxHealth = 50;
        this.health = maxHealth;
        this.baseSkin = textureManager.zombiePawnImage;
        this.hurtSkin = textureManager.zombiePawnHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 160;
    }
}
