package projectiles;

import main.Direction;
import main.GamePanel;
import main.TextureManager;

import java.awt.image.BufferedImage;

public class KnightSmash extends Projectile {
    // Specialized constructor
    public KnightSmash(GamePanel gamePanel, TextureManager textureManager, int x, int y, int size, int speed, int decay, int damage, Direction direction, BufferedImage skin) {
        super(gamePanel, textureManager, x, y, size, size);
        this.direction = direction;
        this.speed = speed;
        this.health = decay;
        this.damage = damage;
        this.skin = skin;
    }

    @Override
    public void move() {
        int spreadSpeed = speed/4;
        x -= spreadSpeed/2;
        y -= spreadSpeed/2;

        width += spreadSpeed;
        height += spreadSpeed;
    }

}
