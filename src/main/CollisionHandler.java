package main;
import allies.Ally;
import enemies.Enemy;
import projectiles.Projectile;
import allies.player.Player;
import mapObjects.MapObject;

public class CollisionHandler {
    GamePanel gamePanel;

    public CollisionHandler(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public boolean borderCollision(Entity entity, int speed, Direction direction){
        int nextX = entity.x;
        int nextY = entity.y;

        switch (direction){
            case UP_RIGHT -> {
                nextY -= speed;
                nextX += speed;
            }
            case UP_LEFT -> {
                nextY -= speed;
                nextX -= speed;
            }
            case DOWN_RIGHT -> {
                nextY += speed;
                nextX += speed;
            }
            case DOWN_LEFT -> {
                nextY += speed;
                nextX -= speed;
            }
            case UP -> nextY -= speed;
            case DOWN -> nextY += speed;
            case LEFT -> nextX -= speed;
            default -> nextX += speed;
        }

        return nextX < 0 || nextY < 0 || nextX + entity.width > Main.WIDTH || nextY + entity.height > Main.HEIGHT - 56;
    }

    public boolean checkCollision(Entity a, Entity b) {
        return  a.x + a.width > b.x &&
                a.x < b.x + b.width &&
                a.y + a.height > b.y &&
                a.y < b.y + b.height;
    }

    public boolean checkCollisionWithPadding(Entity a, Entity b, int padding) {
        return  a.x + a.width > b.x + padding &&
                a.x < b.x + b.width - padding &&
                a.y + a.height > b.y + padding &&
                a.y < b.y + b.height - padding;
    }

    public boolean projectileHitsEntity(Projectile projectile, Entity target){
        return checkCollisionWithPadding(projectile, target, 10);
    }

    public boolean projectileHitsPlayer(Projectile projectile, Entity player){
        return checkCollision(projectile, player);
    }

    public boolean entityHitsEntity(Entity a, Entity b){
        return checkCollision(a, b);
    }

    public boolean entityHitsMapObject(Entity entity, MapObject mapObject){
        return checkCollisionWithPadding(entity, mapObject, 10);
    }

    public boolean projectileHitsMapObject(Projectile projectile, MapObject mapObject){
        return checkCollisionWithPadding(projectile, mapObject, 10);
    }
}
