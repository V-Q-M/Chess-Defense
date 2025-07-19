package projectiles;

import main.GamePanel;
import main.TextureManager;

import java.awt.image.BufferedImage;

public class EnemyCannonBall extends Projectile {

    public EnemyCannonBall(GamePanel gamePanel, TextureManager textureManager, int x, int y, int size, int speed, int damage, BufferedImage skin) {
        super(gamePanel, textureManager, x, y, size, size);
        this.health = 200;
        this.speed = speed;
        this.damage = damage;
        this.skin = skin;
    }

    @Override
    public void update(){
        checkAlive();
        animateBall();
        moveProjectile(speed);
    }
    int animateCounter = 0;

    public void animateBall(){
        if (animateCounter > 30) {
            animateCounter = 0;
            this.y += 12;
        } else {
            animateCounter++;
        }
    }
    @Override
    public void moveProjectile(int speed) {
        x -= speed;
    }

    @Override
    public void checkAlive(){
        if (health <= 0){
            isDead = true;
        } else {
            health--;
        }
        if (x < 0){ // Has hit the castle
            isDead = true;
            gamePanel.castleHealth -= damage/5;
            gamePanel.castleGotHit = true;
        }
    }
}
