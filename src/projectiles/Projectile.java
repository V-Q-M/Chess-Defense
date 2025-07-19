package projectiles;

import main.Direction;
import main.GamePanel;
import main.TextureManager;

import java.awt.image.BufferedImage;

public abstract class Projectile {
    public GamePanel gamePanel;
    public TextureManager textureManager;
    public int x;
    public int y;
    public int width;
    public int height;
    public int speed;
    public int health = 100;
    public Direction direction;
    public boolean isDead = false;
    public int damage;
    public boolean diesOnHit = false;
    public boolean destroyable = false;
    public BufferedImage skin;

    public Projectile(GamePanel gamePanel, TextureManager textureManager, int x, int y, int width, int height){

        this.gamePanel = gamePanel;
        this.textureManager = textureManager;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void moveProjectile(int speed) {
        switch (direction) {
            case UP -> y -= speed;
            case DOWN -> y += speed;
            case LEFT -> x -= speed;
            default -> x += speed;
        }
    }

    public void update(){
        checkAlive();
        moveProjectile(speed);
    }

    public void checkAlive(){
        if (health <= 0){
            isDead = true;
        } else {
            health--;
        }
    }
}
