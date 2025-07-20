package projectiles;

import main.Direction;
import main.GamePanel;
import main.TextureManager;

import java.awt.image.BufferedImage;

public class BishopLance extends Projectile{

    // Specialized constructor
    public BishopLance(GamePanel gamePanel, TextureManager textureManager, int x, int y, int size, int speed, int damage, BufferedImage skin, Direction direction) {
        super(gamePanel, textureManager, x, y, size, size);
        this.direction = direction;
        this.speed = speed;
        this.damage = damage;
        this.skin = skin;
        this.destroyable = true;
    }

    // Moves the ball
    @Override
    public void move() {
        switch (direction) {
            case UP_LEFT -> {
                y -= speed;
                x -= speed;
            }
            case UP_RIGHT -> {
                y -= speed;
                x += speed;
            }
            case DOWN_LEFT -> {
                y += speed;
                x -= speed;
            }
            default -> {
                y += speed;
                x += speed;
            }
        }
    }
}
