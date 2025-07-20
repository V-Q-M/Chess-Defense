package allies;

import main.CollisionHandler;
import main.GamePanel;
import main.SoundManager;
import main.TextureManager;
import mapObjects.MapObject;

public class AllyPawn extends Ally {

    public AllyPawn(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height, boolean canMove) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 10;
        this.baseSpeed = 3;
        this.speed = baseSpeed;
        this.slowedSpeed = 1;
        this.health = 100;
        this.baseSkin = textureManager.pawnImage;
        this.hurtSkin = textureManager.pawnHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 80;
        this.canMove = canMove;
    }

    @Override
    public void checkCollision() {
        boolean isSlowed = false;
        for (MapObject mapObject : gamePanel.mapObjects) {
            if (collisionHandler.entityHitsMapObject(this, mapObject)) {
                isSlowed = true;
                break;
            }
        }
        if (isSlowed) {
            if (speed == baseSpeed) {
                speed = slowedSpeed;
            }
        } else {
            if (speed < baseSpeed) {
                speed = baseSpeed;
            }
        }
        checkProjectileCollision();
    }
}
