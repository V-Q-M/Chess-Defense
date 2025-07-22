package mapObjects;

import allies.Ally;
import enemies.Enemy;
import main.CollisionHandler;
import main.GamePanel;
import main.SoundManager;
import main.TextureManager;
import projectiles.Projectile;

public class Ice extends MapObject {
    private boolean hasCracked = false;
    public Ice(GamePanel gamePanel, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int x, int y, int width, int height){
        super(gamePanel, soundManager, textureManager, collisionHandler, x ,y ,width, height);
        this.baseSkin = textureManager.iceImage;
        this.hurtSkin = textureManager.iceImage;
        this.skin = baseSkin;
        this.maxHealth = 500;
        this.health = maxHealth;
    }

    @Override
    public void checkCollision(){
        for (Projectile projectile : gamePanel.projectiles){
            if (projectile.crushesIce){
                if (collisionHandler.projectileHitsMapObject(projectile, this) && !hasCracked){
                    hasCracked = true;
                    breakIce();
                }
            }
        }
        for (Enemy enemy : gamePanel.enemies){
            if(enemy.isBoss){
                if ((enemy.x <= x && enemy.y == y) && !hasCracked){
                   hasCracked = true;
                   breakIce();
                }
            }
        }
    }

    private void breakIce(){
        for (Enemy enemy : gamePanel.enemies){
            if (collisionHandler.entityHitsMapObject(enemy, this)){
                enemy.health = 0;
            }
        }
        for (Ally ally : gamePanel.allies){
            if (collisionHandler.entityHitsMapObject(ally, this)){
                ally.health = 0;
            }
        }
        if (collisionHandler.entityHitsMapObject(gamePanel.player, this)){
           switch(gamePanel.selectedPieceType) {
               case KNIGHT -> gamePanel.player.knightHealth = 0;
               case ROOK -> gamePanel.player.rookHealth = 0;
               case QUEEN -> gamePanel.player.queenHealth = 0;
               case KING -> gamePanel.player.kingHealth = 0;
               case BISHOP -> gamePanel.player.bishopHealth = 0;
           }
           gamePanel.player.targetX -= 256;
           gamePanel.player.x = gamePanel.player.targetX;
        }
        transformIntoLake();
    }

    private void transformIntoLake(){
        this.baseSkin = textureManager.lakeImage;
        this.hurtSkin = textureManager.lakeImage;
        this.skin = baseSkin;
        this.maxHealth = 500;
        this.health = maxHealth;
        
    }
}
