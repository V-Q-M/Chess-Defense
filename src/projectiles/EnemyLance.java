package projectiles;

import main.Direction;
import main.GamePanel;
import main.TextureManager;

import java.awt.image.BufferedImage;

public class EnemyLance extends Projectile{
    // Specialized constructor
    public EnemyLance(GamePanel gamePanel, TextureManager textureManager, int x, int y, int size, int speed, int damage, BufferedImage skin, Direction direction) {
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
        if (direction == Direction.UP_LEFT){
           y -= speed;
        } else {
            y += speed;
        }
        x -= speed;
    }
}
