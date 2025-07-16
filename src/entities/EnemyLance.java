package entities;

import main.Direction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class EnemyLance extends Projectile{
    // Specialized constructor
    public EnemyLance(GamePanel gamePanel, int x, int y, int size, int speed, int damage, BufferedImage skin, Direction direction) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.width = size;
        this.height = size;
        this.direction = direction;
        this.speed = speed;
        this.damage = damage;
        this.skin = skin;
    }

    // Moves the ball
    @Override
    public void moveProjectile(int speed) {
        if (direction == Direction.UP_LEFT){
           y -= speed;
        } else {
            y += speed;
        }
        x -= speed;
    }
}
