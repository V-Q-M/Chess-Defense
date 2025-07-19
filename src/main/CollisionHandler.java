package main;
import allies.Ally;
import enemies.Enemy;
import projectiles.Projectile;
import allies.player.Player;
import mapObjects.ImmovableObject;

public class CollisionHandler {
    GamePanel gamePanel;
    public CollisionHandler(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public boolean borderCollision(int playerX, int playerY, int playerWidth, int playerHeight, int speed, Direction direction){
        switch (direction){
            case UP_RIGHT -> {
                return (playerY - speed <= 0) || (playerX + playerWidth + speed >= Main.WIDTH);
            }
            case UP_LEFT -> {
                return (playerY - speed <= 0) || (playerX - speed <= 0);
            }
            case DOWN_RIGHT -> {
                return (playerY + playerHeight + speed >= Main.HEIGHT - 56) ||
                        (playerX + playerWidth + speed >= Main.WIDTH);
            }
            case DOWN_LEFT -> {
                return (playerY + playerHeight + speed >= Main.HEIGHT - 56) ||
                        (playerX - speed <= 0);
            }
            case UP -> {
                return (playerY - speed <= 0);
            }
            case DOWN -> {
                return (playerY + playerHeight + speed >= Main.HEIGHT - 56);
            }
            case LEFT -> {
                return (playerX - speed <= 0);
            }
            default -> {
                return (playerX + playerWidth + speed >= Main.WIDTH);
            }
        }
    }

    public boolean projectileCollision(LivingBeing enemy, Projectile projectile) {
        return projectile.x + projectile.width > enemy.x &&
            projectile.x < enemy.x + enemy.width &&
            projectile.y + projectile.height > enemy.y + 10 &&
            projectile.y < enemy.y + enemy.height - 10;
    }


    public boolean projectileEnemyCollision(Projectile projectile, Player player){
        return player.x + gamePanel.pieceWidth > projectile.x &&
                player.x < projectile.x + projectile.width &&
                player.y + gamePanel.pieceHeight > projectile.y &&
                player.y < projectile.y + projectile.height;
    }

    public boolean enemyCollision(Enemy enemy, Player player){
        return player.x + gamePanel.pieceWidth > enemy.x &&
            player.x < enemy.x + enemy.width &&
            player.y + gamePanel.pieceHeight > enemy.y &&
            player.y < enemy.y + enemy.height;
    }
    public boolean allyCollision(Enemy enemy, Ally pawn){
        return pawn.x + pawn.width > enemy.x &&
            pawn.x < enemy.x + enemy.width &&
            pawn.y + pawn.height > enemy.y + 10 &&
            pawn.y < enemy.y + enemy.height - 10;
    }

    public boolean mapObjectCollision(ImmovableObject mapObject, Projectile projectile){
        return projectile.x + projectile.width > mapObject.x &&
                projectile.x < mapObject.x + mapObject.width &&
                projectile.y + projectile.height > mapObject.y + 10 &&
                projectile.y < mapObject.y + mapObject.height - 10;
    }

    public boolean mapObjectMovementCollision(ImmovableObject mapObject, LivingBeing livingBeing){
        return livingBeing.x + livingBeing.width > mapObject.x &&
                livingBeing.x < mapObject.x + mapObject.width &&
                livingBeing.y + livingBeing.height > mapObject.y + 10 &&
                livingBeing.y < mapObject.y + mapObject.height - 10;
    }
}
