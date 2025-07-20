package allies;

import projectiles.Projectile;
import main.*;

public abstract class Ally extends Entity {
    public int maxHealth = 100; // need to pass it in constructor soon
    public boolean canMove = false;


    public Ally(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
    }

    public void checkAlive(){
        if (health <= 0){
            this.isDead = true;
            soundManager.playClip("death");
        }
    }

    public void checkCollision(){
        checkProjectileCollision();
    }

    void checkProjectileCollision(){
        for (Projectile projectile : gamePanel.enemyBalls){
            if (collisionHandler.projectileHitsEntity(projectile, this)){
                health -= projectile.damage;
                projectile.isDead = true;
                this.isInvulnerable = true;
                this.skin = hurtSkin;
                gamePanel.entityManager.spawnExplosion(projectile.x, projectile.y);
                soundManager.playClip("hit");
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

    public void updateCooldowns(){
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
