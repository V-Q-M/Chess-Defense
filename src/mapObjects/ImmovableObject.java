package mapObjects;

import projectiles.Projectile;
import main.CollisionHandler;
import main.GamePanel;
import main.SoundManager;
import main.TextureManager;

import java.awt.image.BufferedImage;

public abstract class ImmovableObject {
    public GamePanel gamePanel;
    public SoundManager soundManager;
    public TextureManager textureManager;
    public CollisionHandler collisionHandler;

    public int x, y, width, height;
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


    public ImmovableObject(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        this.gamePanel = gamePanel;
        this.soundManager = soundManager;
        this.textureManager = textureManager;
        this.collisionHandler = collisionHandler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.recoveryTime = 41;
    }

    public void update(){
        checkAlive();
        checkProjectileCollision();
        updateCooldowns();
    }

    void checkAlive(){
        if (health <= 0){
            this.isDead = true;
            //soundManager.playClip(soundManager.deathClip);
            soundManager.playClip("death");
        }
    }

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

    void updateCooldowns(){
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
