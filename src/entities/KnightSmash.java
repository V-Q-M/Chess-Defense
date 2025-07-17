package entities;

import main.Direction;
import main.GamePanel;
import main.Main;
import main.TextureManager;

public class KnightSmash extends Projectile {
    // Specialized constructor
    public KnightSmash(GamePanel gamePanel, TextureManager textureManager, int x, int y, int size, int speed, int decay, int damage, Direction direction) {
        super(gamePanel, textureManager, x, y, size, size);
        this.direction = direction;
        this.speed = speed;
        this.health = decay;
        this.damage = damage;
        this.skin = textureManager.knightParticleImage;
    }

    @Override
    public void moveProjectile(int speed) {
        int spreadSpeed = speed/4;
        x -= spreadSpeed/2;
        y -= spreadSpeed/2;

        width += spreadSpeed;
        height += spreadSpeed;
    }

}
