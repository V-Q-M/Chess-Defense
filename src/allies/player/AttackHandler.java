package allies.player;

import main.Direction;
import main.GamePanel;
import main.Main;

import java.awt.image.BufferedImage;

public class AttackHandler {

    GamePanel gamePanel;
    Player player;

    public AttackHandler(GamePanel gamePanel, Player player){
        this.gamePanel = gamePanel;
        this.player = player;
    }

    protected void rookAttack(Direction facingDirection){
        gamePanel.entityManager.spawnCannonBall(player.x, player.y, facingDirection);
    }

    protected void bishopAttack(Direction facingDirection){
        BufferedImage skin;
        switch(facingDirection){
            case UP_LEFT -> {
                skin = gamePanel.bishopParticleImageUpLeft;
            }
            case UP_RIGHT -> {
                skin = gamePanel.bishopParticleImageUpRight;
            }
            case DOWN_LEFT -> {
                skin = gamePanel.bishopParticleImageDownLeft;
            }
            default -> {
                skin = gamePanel.bishopParticleImageDownRight;
            }
        }


        gamePanel.entityManager.spawnLance(skin);
    }

    protected void queenAttack(Direction facingDirection){
        BufferedImage skin = gamePanel.queenParticleImageRight;

        player.speed = player.DASH_SPEED;
        player.queenDashing = true;
        player.isInvulnerable = true;
        switch(facingDirection){
            case UP -> {
                if (player.targetY - 3 * gamePanel.PIECE_HEIGHT >= 0) {
                    player.targetY -= gamePanel.PIECE_HEIGHT * 3;
                } else {
                    player.targetY = 0;
                }
                skin = gamePanel.queenParticleImageUp;
            }
            case DOWN -> {
                if (player.targetY + 3 * gamePanel.PIECE_HEIGHT < Main.HEIGHT - gamePanel.PIECE_HEIGHT - 56) {
                    player.targetY += gamePanel.PIECE_HEIGHT * 3;
                } else {
                    player.targetY = Main.HEIGHT - gamePanel.PIECE_HEIGHT - 56;
                }
                skin = gamePanel.queenParticleImageDown;
            }
            case LEFT, UP_LEFT, DOWN_LEFT -> {
                if (player.targetX - 3 * gamePanel.pieceHeight >= 0) {
                    player.targetX -= gamePanel.PIECE_HEIGHT * 3;
                } else {
                    player.targetX = 0;
                }
                skin = gamePanel.queenParticleImageLeft;
            }
            default -> {
                if (player.targetX + 3 * gamePanel.pieceHeight < Main.WIDTH - gamePanel.pieceHeight) {
                    player.targetX += gamePanel.PIECE_HEIGHT * 3;
                } else {
                    player.targetX = Main.WIDTH - gamePanel.PIECE_HEIGHT;

                }
                skin = gamePanel.queenParticleImageRight;
            }
        }

        gamePanel.entityManager.spawnQueenParticles(skin);
    }

    protected void knightAttack(Direction facingDirection){

        switch (facingDirection){
            case UP -> {
                if (player.targetY - 2 * gamePanel.pieceHeight >= 0){
                    player.targetY -= 2 * gamePanel.pieceHeight;
                } else {
                    player.targetY = 0;
                }
                player.y = player.targetY;
            }
            case DOWN -> {
                if (player.targetY + 2 * gamePanel.pieceHeight < Main.HEIGHT - gamePanel.pieceHeight - 56){
                    player.targetY += 2 * gamePanel.pieceHeight;
                } else {
                    player.targetY = Main.HEIGHT - gamePanel.pieceHeight - 56;
                }
                player.y = player.targetY;
            }
            case LEFT -> {
                if (player.targetX - 2 * gamePanel.pieceHeight >= 0){
                    player.targetX -= 2 * gamePanel.pieceHeight;
                } else {
                    player.targetX = 0;
                }
                player.x = player.targetX;
            }
            default -> {
                if (player.targetX + 2 * gamePanel.pieceHeight < Main.WIDTH - gamePanel.pieceHeight){
                    player.targetX += 2 * gamePanel.pieceHeight;
                } else {
                    player.targetX = Main.WIDTH - gamePanel.pieceHeight;
                }
                player.x = player.targetX;
            }
        }
        gamePanel.entityManager.spawnKnightParticles();
    }

    protected void kingAttack(){
        player.soundManager.playClip(player.soundManager.summonClip);
        gamePanel.entityManager.spawnPawns(player.x , player.y - gamePanel.pieceHeight);
        gamePanel.entityManager.spawnPawns(player.x + gamePanel.pieceWidth, player.y);
        gamePanel.entityManager.spawnPawns(player.x , player.y + gamePanel.pieceHeight);
    }
}
