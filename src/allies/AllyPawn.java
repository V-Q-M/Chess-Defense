package allies;

import main.CollisionHandler;
import main.GamePanel;
import main.SoundManager;
import main.TextureManager;
import mapObjects.ImmovableObject;

public class AllyPawn extends Ally {

    public AllyPawn(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height, boolean canMove) {
        super(gamePanel, soundManager, textureManager, collisionHandler, x, y, width, height);
        this.damage = 10;
        this.baseSpeed = 3;
        this.speed = baseSpeed;
        this.health = 100;
        this.baseSkin = textureManager.pawnImage;
        this.hurtSkin = textureManager.pawnHurtImage;
        this.skin = baseSkin;
        this.attackCoolDown = 80;
        this.canMove = canMove;
    }

    @Override
    void checkCollision() {
        boolean isSlowed = false;
        for (ImmovableObject mapObject : gamePanel.mapObjects) {
            if (collisionHandler.mapObjectMovementCollision(mapObject, this)) {
                isSlowed = true;
                break;
            }
        }
        if (isSlowed) {
            if (speed == baseSpeed) {
                speed = speed / 2;
            }
        } else {
            if (speed < baseSpeed) {
                speed = baseSpeed;
            }
        }
    }
}
