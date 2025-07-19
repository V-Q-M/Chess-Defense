package allies;

import entities.Projectile;
import main.*;

public abstract class Ally extends LivingBeing {
    public int maxHealth = 100; // need to pass it in constructor soon
    public boolean canMove = false;


    public Ally(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        this.gamePanel = gamePanel;
        this.soundManager = soundManager;
        this.textureManager = textureManager;
        this.collisionHandler = collisionHandler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update(){
        checkAlive();
        move();
        updateCooldowns();
        checkCollision();
        checkProjectileCollision();
    }

    void checkAlive(){
        if (health <= 0){
            this.isDead = true;
            soundManager.playClip(soundManager.deathClip);
        }
    }

    void checkCollision(){

    }

    void checkProjectileCollision(){
        for (Projectile projectile : gamePanel.enemyBalls){
            if (collisionHandler.projectileCollision(this, projectile)){
                health -= projectile.damage;
                projectile.isDead = true;
                this.isInvulnerable = true;
                this.skin = hurtSkin;
                gamePanel.entityManager.spawnExplosion(projectile.x, projectile.y);
                soundManager.playClip(soundManager.hitClip);
            }
        }
    }


    public void move(){
        if (canMove){
            if (x > Main.WIDTH){
                health = 0;
            } else {
                x += speed;
            }
        }
    }

    void updateCooldowns(){
        if (isInvulnerable){
            if (invulnerableCounter >= recoveryTime){
                isInvulnerable = false;
                invulnerableCounter = 0;
            } else if (invulnerableCounter > recoveryMarkerTime){
                this.skin = baseSkin;
            }
            invulnerableCounter++;
        }

        if (hasAttacked && attackCoolDownCounter < attackCoolDown){
            attackCoolDownCounter++;
        } else {
            hasAttacked = false;
            attackCoolDownCounter = 0;
        }
    }
}
