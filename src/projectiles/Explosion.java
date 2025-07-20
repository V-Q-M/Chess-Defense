package projectiles;

import main.GamePanel;
import main.TextureManager;

public class Explosion extends Projectile{

    public Explosion(GamePanel gamePanel, TextureManager textureManager, int x, int y, int size) {
        super(gamePanel, textureManager, x, y, size, size);
        this.health = 30;
        this.skin = textureManager.explosionImage;
        this.speed = 4;
    }

    @Override
    public void move() {
        int spreadSpeed = speed;
        x -= spreadSpeed/2;
        y -= spreadSpeed/2;

        width += spreadSpeed;
        height += spreadSpeed;
    }
}
