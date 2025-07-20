package projectiles;

import main.Direction;
import main.Entity;
import main.GamePanel;
import main.TextureManager;

import java.awt.image.BufferedImage;

public abstract class Projectile extends Entity {
    public int speed;
    public int health = 100;
    public Direction direction;
    public boolean isDead = false;
    public int damage;
    public boolean diesOnHit = false;
    public boolean destroyable = false;
    public BufferedImage skin;

    public Projectile(GamePanel gamePanel, TextureManager textureManager, int x, int y, int width, int height){
        super(gamePanel, null, textureManager, null, x, y, width, height);
    }

    public void move() {
        switch (direction) {
            case UP -> y -= speed;
            case DOWN -> y += speed;
            case LEFT -> x -= speed;
            default -> x += speed;
        }
    }

    @Override
    public void update(){
        checkAlive();
        move();
    }

    public void checkAlive(){
        if (health <= 0){
            isDead = true;
        } else {
            health--;
        }
    }

    public void checkCollision(){

    }

    public void updateCooldowns(){

    }
}
