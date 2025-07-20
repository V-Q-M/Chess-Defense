package enemies;

import allies.Ally;
import projectiles.Projectile;
import main.*;
import mapObjects.MapObject;

public abstract class Enemy extends Entity {
    public int maxHealth = 100; // need to pass it in constructor soon
    public boolean isBoss = false;


    public Enemy(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.recoveryTime = 41;
    }

    public void checkAlive(){
        if (health <= 0){
            this.isDead = true;
            gamePanel.score+=maxHealth;
            soundManager.playClip("death");
        }
    }


    public void move(){
        if (x < 0){
            health = 0;
            gamePanel.castleHealth -= damage / 3;
            gamePanel.castleGotHit = true;
        } else {
            x -= speed;
        }

        if (y < 0){
            y += speed;
        } else if (y > 8 * gamePanel.squareSize){
            y = 8 * gamePanel.squareSize;
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
        if (hasAttacked && attackCoolDownCounter < attackCoolDown){
            attackCoolDownCounter++;
        } else {
            hasAttacked = false;
            attackCoolDownCounter = 0;
        }
    }

    public void checkCollision(){
        checkProjectileCollision();

        boolean isSlowed = false;
        for (MapObject mapObject : gamePanel.mapObjects) {
            if (collisionHandler.mapObjectMovementCollision(mapObject, this)) {
                isSlowed = true;
                break;
            }
        }
        if (isSlowed) {
            if (speed == baseSpeed) {
                speed =  slowedSpeed;
            }
        } else {
            if (speed < baseSpeed) {
                speed = baseSpeed;
            }
        }
        checkAllyCollision();
    }

    protected void checkProjectileCollision(){
        if (!isInvulnerable) {
            for (Projectile projectile : gamePanel.projectiles) {
                if (collisionHandler.projectileCollision(this, projectile)) {
                    isInvulnerable = true;
                    this.skin = hurtSkin;
                    //soundManager.playClip(soundManager.hitClip);
                    soundManager.playClip("hit");
                    health -= projectile.damage;

                    if (projectile.diesOnHit) {
                        gamePanel.entityManager.spawnExplosion(projectile.x, projectile.y);
                        projectile.isDead = true;
                    }
                }
            }
        }
    }

     protected void checkAllyCollision(){
        for (Ally pawn : gamePanel.allies){
            if (!pawn.isDead && collisionHandler.allyCollision(this, pawn)) {
                health -= 100;
                pawn.health = 0;
            }
        }
    }
}
