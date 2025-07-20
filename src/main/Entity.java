package main;

import java.awt.image.BufferedImage;

public abstract class Entity {
    public GamePanel gamePanel;
    public SoundManager soundManager;
    public TextureManager textureManager;
    public CollisionHandler collisionHandler;

    public int x, y, width, height;
    public boolean isDead, isInvulnerable, hasAttacked = false;
    public int invulnerableCounter, attackCoolDownCounter = 0;
    public int attackCoolDown;

    public BufferedImage skin, baseSkin, hurtSkin;

    public int recoveryTime = 30;
    public int recoveryMarkerTime = 15;

    public int speed, baseSpeed, slowedSpeed;
    public int damage, health;

    public Entity(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height){
       this.gamePanel = gamePanel;
       this.soundManager = soundManager;
       this.textureManager = textureManager;
       this.collisionHandler = collisionHandler;
       this.x = x;
       this.y = y;
       this.width = width;
       this.height = height;
    }
}
