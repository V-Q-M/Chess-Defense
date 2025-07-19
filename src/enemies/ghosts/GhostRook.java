package enemies.ghosts;

import enemies.Enemy;
import main.*;

public class GhostRook extends Enemy {
    private int cannonDamage;

    public GhostRook(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height, int cannonDamage) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 15;
        this.cannonDamage = cannonDamage;
        this.baseSpeed = 4;
        this.speed = baseSpeed;
        this.slowedSpeed = baseSpeed; // ghosts arent slowed
        this.maxHealth = 1;
        this.health = maxHealth;
        this.baseSkin = textureManager.ghostRookImage;
        this.hurtSkin = textureManager.ghostRookHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 50;
        this.attackCoolDownCounter = 0;
        soundManager.playClip(soundManager.ghostSpawnClip);
    }

    boolean allowAttack = false;
    @Override
    public void move(){
        if (x < Main.WIDTH - 508){
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
    public void update(){
        checkAlive();
        move();
        checkCollision();
        checkPawnWallCollision();
        updateCooldowns();
    }

    @Override
    protected void updateCooldowns(){

        if (isInvulnerable){
            if (invulnerableCounter >= recoveryTime){
                isInvulnerable = false;
                invulnerableCounter = 0;
            } else if (invulnerableCounter > recoveryMarkerTime) {
                this.skin = baseSkin;
            }
            invulnerableCounter ++;
        }

        if (attackCoolDownCounter > attackCoolDown){
            performAttack();
            hasAttacked = false;
            attackCoolDownCounter = 0;
        } else {
            attackCoolDownCounter++;
        }
    }

    private void performAttack() {
        gamePanel.entityManager.spawnEnemyCannonBall(x, y, cannonDamage);
    }
}
