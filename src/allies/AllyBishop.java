package allies;

import main.*;

import java.awt.image.BufferedImage;

public class AllyBishop extends Ally{
    private boolean facingDown;

    public AllyBishop(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height, boolean facingDown) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 10;
        this.speed = 3;
        this.health = 150;
        this.maxHealth = 150;
        this.baseSkin = textureManager.bishopImage;
        this.hurtSkin = textureManager.bishopHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 200;
        this.attackCoolDownCounter = (int) (Math.random() * 100); // Makes it so that the rooks arent shooting in sync (looks goofy)
        this.facingDown = facingDown;
    }

    @Override
    void updateCooldowns(){
        if (isInvulnerable){
            if (invulnerableCounter >= recoveryTime){
                isInvulnerable = false;
                invulnerableCounter = 0;
            } else if (invulnerableCounter > recoveryMarkerTime){
                this.skin = baseSkin;
            }
            invulnerableCounter++;
        }

        if (attackCoolDownCounter > attackCoolDown){
            performAttack();
            hasAttacked = false;
            attackCoolDownCounter = 0;
        } else {
            attackCoolDownCounter++;
        }
    }

    private void performAttack(){
        BufferedImage particleSkin;
        Direction facingDirection;
        if (facingDown){
            particleSkin = textureManager.bishopParticleImageDownRight;
            facingDirection = Direction.DOWN_RIGHT;
        } else {
            particleSkin = textureManager.bishopParticleImageUpRight;
            facingDirection = Direction.UP_RIGHT;
        }
        gamePanel.entityManager.spawnLance(x ,y ,facingDirection , particleSkin);
    }
}
