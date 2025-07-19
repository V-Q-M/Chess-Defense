package enemies.regular;

import enemies.Enemy;
import main.*;

public class EnemyBishop extends Enemy {

    private int lanceDamage;

    public EnemyBishop(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height, int lanceDamage) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 10;
        this.lanceDamage = lanceDamage;
        this.baseSpeed = 2;
        this.speed = baseSpeed;
        this.health = 150;
        this.maxHealth = 150;
        this.baseSkin = textureManager.enemyRookImage;
        this.hurtSkin = textureManager.enemyRookHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 300;
        this.attackCoolDownCounter = 0;
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
        gamePanel.entityManager.spawnEnemyCannonBall(x, y, lanceDamage);
    }
}
