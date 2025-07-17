package enemies;

import main.*;

public class EnemyRook extends Enemy{
    private int cannonDamage;

    public EnemyRook(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height, int cannonDamage) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 15;
        this.cannonDamage = cannonDamage;
        this.speed = 2;
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
        checkPlayerCollision();
        checkPawnWallCollision();
        updateCooldowns();
    }

    @Override
    void updateCooldowns(){

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
