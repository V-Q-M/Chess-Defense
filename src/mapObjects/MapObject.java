package mapObjects;

import main.*;
import projectiles.Projectile;

import java.awt.image.BufferedImage;

public abstract class MapObject extends Entity {
    public boolean isDead, isInvulnerable, hasAttacked = false;
    public int invulnerableCounter, attackCoolDownCounter = 0;
    public int attackCoolDown;

    public BufferedImage skin;
    public BufferedImage baseSkin;
    public BufferedImage hurtSkin;

    public int recoveryTime = 30;
    public int recoveryMarkerTime = 15;

    public int damage;
    public int maxHealth = 100;
    public int health = maxHealth;


    public MapObject(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.recoveryTime = 41;
    }

    @Override
    public void update(){
        checkAlive();
        checkCollision();
        updateCooldowns();
    }

    public void checkAlive(){
        if (health <= 0){
            this.isDead = true;
            //soundManager.playClip(soundManager.deathClip);
            soundManager.playClip("death");
        }
    }

    public void move(){

    };

    public void checkCollision(){
        checkProjectileCollision();
    };

    void checkProjectileCollision(){
        if (!isInvulnerable) {
            for (Projectile projectile : gamePanel.projectiles) {
                if (collisionHandler.mapObjectCollision(this, projectile)) {
                    isInvulnerable = true;
                    this.skin = hurtSkin;
                    //soundManager.playClip(soundManager.hitClip);
                    soundManager.playClip("hit");
                    health -= projectile.damage;

                    if (projectile.diesOnHit) {
                        gamePanel.entityManager.spawnExplosion(projectile.x, projectile.y);
                        projectile.isDead = true;
                    } else if (projectile.destroyable) {
                        projectile.isDead = true;
                    }
                }
            }
            for (Projectile projectile : gamePanel.enemyBalls){
                if (collisionHandler.mapObjectCollision(this, projectile)){
                    health -= projectile.damage;
                    projectile.isDead = true;
                    this.isInvulnerable = true;
                    this.skin = hurtSkin;
                    gamePanel.entityManager.spawnExplosion(projectile.x, projectile.y);
                    //soundManager.playClip(soundManager.hitClip);
                    soundManager.playClip("hit");
                }
            }
        }
    }

    public void updateCooldowns(){
        if (isInvulnerable){
            if (invulnerableCounter >= recoveryTime){
                isInvulnerable = false;
                invulnerableCounter = 0;
            }  else if (invulnerableCounter > recoveryMarkerTime) {
                this.skin = baseSkin;
            }
            invulnerableCounter++;
        }
    }
}
