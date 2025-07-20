package enemies.bosses;

import enemies.Enemy;
import main.*;

public class KingBoss extends Enemy {
    public KingBoss(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        super(gamePanel, soundManager, textureManager,  collisionHandler, x, y, width, height);
        this.damage = 10;
        this.speed = 2;
        this.slowedSpeed = 1;
        this.maxHealth = 400;
        this.health = 400;
        this.baseSkin = textureManager.enemyKingImage;
        this.hurtSkin = textureManager.enemyKingHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 600;
        this.width = width * 2;
        this.height = height * 2;
        this.attackCoolDownCounter = 150;
        this.isBoss = true;
    }


    @Override
    public void checkAlive(){
        if (health <= 0){
            this.isDead = true;
            gamePanel.score+=maxHealth;
            gamePanel.enemyKingSlain = true;
            //soundManager.playClip(soundManager.deathClip);
            soundManager.playClip("death");
        }
    }

    boolean allowAttack = false;
    @Override
    public void move(){
        if (x < Main.WIDTH - 382){
            allowAttack = true;
        } else {
            x -= speed;
        }

        if (y < 0){
            y += speed;
        } else if (y > 8 * gamePanel.squareSize){
            y = 8 * gamePanel.squareSize;
        }
    }


    @Override
    public void updateCooldowns(){

        if (isInvulnerable){
            if (invulnerableCounter >= recoveryTime){
                isInvulnerable = false;
                invulnerableCounter = 0;
            } else if (invulnerableCounter > recoveryMarkerTime) {
                this.skin = baseSkin;
            }
            invulnerableCounter ++;
        }

        if (this.attackCoolDownCounter > this.attackCoolDown){
            performAttack();
            hasAttacked = false;
            this.attackCoolDownCounter = 0;
        } else {
            this.attackCoolDownCounter++;
        }
    }

    private void performAttack() {

        gamePanel.enemyManager.spawnKingsGuard(x-128,y,width/2);
    }

}
