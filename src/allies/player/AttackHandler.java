package allies.player;

import main.Direction;
import main.GamePanel;
import main.Main;
import main.TextureManager;

import java.awt.image.BufferedImage;

public class AttackHandler {

    GamePanel gamePanel;
    TextureManager textureManager;
    Player player;
    int size;

    public AttackHandler(GamePanel gamePanel, TextureManager textureManager, Player player){
        this.gamePanel = gamePanel;
        this.textureManager = textureManager;
        this.player = player;
        this.size = gamePanel.PIECE_HEIGHT;
    }

    protected void rookAttack(Direction facingDirection){
        gamePanel.entityManager.spawnCannonBall(player.x, player.y, facingDirection);
    }

    protected void bishopAttack(Direction facingDirection){
        BufferedImage skin;
        switch(facingDirection){
            case UP_LEFT -> {
                skin = textureManager.bishopParticleImageUpLeft;
            }
            case UP_RIGHT -> {
                skin = textureManager.bishopParticleImageUpRight;
            }
            case DOWN_LEFT -> {
                skin = textureManager.bishopParticleImageDownLeft;
            }
            default -> {
                skin = textureManager.bishopParticleImageDownRight;
            }
        }


        gamePanel.entityManager.spawnLance(player.x, player.y,facingDirection, skin);
    }

    protected void queenAttack(Direction facingDirection){
        BufferedImage skin;

        player.queenDashing = true;
        player.isInvulnerable = true;

        switch(facingDirection){
            case UP_LEFT -> {
                player.targetY = Math.max(player.targetY - 3 * size, 0);
                player.targetX = Math.max(player.targetX - 3 * size, 0);
                skin = textureManager.queenParticleImageUp;
                player.speed = 12;
            }
            case UP_RIGHT -> {
                player.targetY = Math.max(player.targetY - 3 * size, 0);
                player.targetX = Math.min(player.targetX + 3 * size, Main.WIDTH - size);
                skin = textureManager.queenParticleImageUp;
                player.speed = 12;
            }
            case DOWN_LEFT -> {
                player.targetY = Math.min(player.targetY + 3 * size, Main.HEIGHT - size - 56);
                player.targetX = Math.max(player.targetX - 3 * size, 0);
                skin = textureManager.queenParticleImageDown;
                player.speed = 12;
            }
            case DOWN_RIGHT -> {
                player.targetY = Math.min(player.targetY + 3 * size, Main.HEIGHT - size - 56);
                player.targetX = Math.min(player.targetX + 3 * size, Main.WIDTH - size);
                skin = textureManager.queenParticleImageDown;
                player.speed = 12;
            }
            case UP -> {
                player.targetY = Math.max(player.targetY - 3 * size, 0);
                skin = textureManager.queenParticleImageUp;
                player.speed = player.DASH_SPEED;
            }
            case DOWN -> {
                player.targetY = Math.min(player.targetY + 3 * size, Main.HEIGHT - size - 56);
                skin = textureManager.queenParticleImageDown;
                player.speed = player.DASH_SPEED;
            }
            case LEFT -> {
                player.targetX = Math.max(player.targetX - 3 * size, 0);
                skin = textureManager.queenParticleImageLeft;
                player.speed = player.DASH_SPEED;
            }
            default -> {
                player.targetX = Math.min(player.targetX + 3 * size, Main.WIDTH - size);
                skin = textureManager.queenParticleImageRight;
                player.speed = player.DASH_SPEED;
            }
        }
        gamePanel.entityManager.spawnQueenParticles(skin);
    }


    protected void knightAttack(Direction facingDirection){

        switch (facingDirection){
            case UP_LEFT -> {
                player.targetY = Math.max(player.targetY - 2 * size, 0);
                player.targetX = Math.max(player.targetX - 2 * size, 0);
                player.y = player.targetY;
                player.x = player.targetX;
            }
            case UP_RIGHT -> {
                player.targetY = Math.max(player.targetY - 2 * size, 0);
                player.targetX = Math.min(player.targetX + 2 * size, Main.WIDTH - size);
                player.y = player.targetY;
                player.x = player.targetX;
            }
            case DOWN_LEFT -> {
                player.targetY = Math.min(player.targetY + 2 * size, Main.HEIGHT - size - 56);
                player.targetX = Math.max(player.targetX - 2 * size, 0);
                player.y = player.targetY;
                player.x = player.targetX;
            }
            case DOWN_RIGHT -> {
                player.targetY = Math.min(player.targetY + 2 * size, Main.HEIGHT - size - 56);
                player.targetX = Math.min(player.targetX + 2 * size, Main.WIDTH - size);
                player.y = player.targetY;
                player.x = player.targetX;
            }
            case UP -> {
                player.targetY = Math.max(player.targetY - 2 * size, 0);
                player.y = player.targetY;
            }
            case DOWN -> {
                player.targetY = Math.min(player.targetY + 2 * size, Main.HEIGHT - size - 56);
                player.y = player.targetY;
            }
            case LEFT -> {
                player.targetX = Math.max(player.targetX - 2 * size, 0);
                player.x = player.targetX;
            }
            default -> {
                player.targetX = Math.min(player.targetX + 2 * size, Main.WIDTH - size);
                player.x = player.targetX;
            }
        }
        gamePanel.entityManager.spawnKnightParticles();
    }

    protected void kingAttack(){
        player.soundManager.playClip(player.soundManager.summonClip);
        gamePanel.entityManager.spawnPawns(player.x , player.y - size);
        gamePanel.entityManager.spawnPawns(player.x + gamePanel.pieceWidth, player.y);
        gamePanel.entityManager.spawnPawns(player.x , player.y + size);
    }
}
