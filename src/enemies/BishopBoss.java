package enemies;

import main.*;

public class BishopBoss extends Enemy{
    public BishopBoss(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 20;
        this.speed = 1;
        this.health = 400;
        this.maxHealth = 400;
        this.baseSkin = textureManager.enemyBishopImage;
        this.hurtSkin = textureManager.enemyBishopHurtImage;
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
        } else if (y > 8 * gamePanel.PIECE_HEIGHT){
            y = 8 * gamePanel.PIECE_HEIGHT;
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
        gamePanel.entityManager.spawnBossLance(x, y);
    }

    @Override
    void checkAlive(){
        if (health <= 0){
            this.isDead = true;
            gamePanel.score+=maxHealth;
            soundManager.playClip(soundManager.deathClip);
            gamePanel.rookBossSlain = true;
        }
    }
}
