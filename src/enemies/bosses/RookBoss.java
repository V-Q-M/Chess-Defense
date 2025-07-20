package enemies.bosses;

import enemies.Enemy;
import main.*;

public class RookBoss extends Enemy {
    public RookBoss(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 20;
        this.baseSpeed = 1;
        this.speed = baseSpeed;
        this.health = 400;
        this.maxHealth = 400;
        this.baseSkin = textureManager.enemyRookImage;
        this.hurtSkin = textureManager.enemyRookHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 600;
        this.attackCoolDownCounter = 0;
        this.width = width * 2;
        this.height = height * 2;
        this.isBoss = true;
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
        gamePanel.entityManager.spawnBossCannonBall(x, y);
    }

    @Override
    protected void checkAlive(){
        if (health <= 0){
            this.isDead = true;
            gamePanel.score+=maxHealth;
            //soundManager.playClip(soundManager.deathClip);
            soundManager.playClip("death");
            gamePanel.rookBossSlain = true;
        }
    }
}
