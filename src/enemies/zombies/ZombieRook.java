package enemies.zombies;

import enemies.Enemy;
import main.*;

public class ZombieRook extends Enemy {
    private int cannonDamage;
    private boolean hasTransformed = false;

    public ZombieRook(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height, int cannonDamage) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 15;
        this.cannonDamage = cannonDamage;
        this.baseSpeed = 2;
        this.speed = baseSpeed;
        this.maxHealth = 150;
        this.health = maxHealth;
        this.baseSkin = textureManager.enemyRookImage;
        this.hurtSkin = textureManager.enemyRookHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 150;
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
        gamePanel.entityManager.spawnEnemyCannonBall(x, y, cannonDamage);
    }

    @Override
    protected void checkAlive(){
        if (health <= 0 && !hasTransformed) {
            hasTransformed = true;
            transformIntoZombie();
        } else if (health <= 0) {
            this.isDead = true;
            gamePanel.score+=maxHealth;
            soundManager.playClip(soundManager.deathClip);
        }
    }

    private void transformIntoZombie(){
        soundManager.playClip(soundManager.zombieSpawnClip);
        this.damage = 15;
        this.cannonDamage = cannonDamage;
        this.baseSpeed = 1;
        this.speed = baseSpeed;
        this.maxHealth = 75;
        this.health = maxHealth;
        this.baseSkin = textureManager.zombieRookImage;
        this.hurtSkin = textureManager.zombieRookHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 400;
        this.attackCoolDownCounter = 0;
    }
}
