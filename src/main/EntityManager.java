package main;

import allies.AllyPawn;
import projectiles.*;
import allies.player.Player;

import java.awt.image.BufferedImage;

public class EntityManager {
    GamePanel gamePanel;
    SoundManager soundManager;
    TextureManager textureManager;
    KeyHandler keyHandler;
    Player player;

    final int DEFAULT_CANNONBALL_SPEED = 12;
    final int DEFAULT_ENEMY_CANNONBALL_SPEED = 10;
    final int DEFAULT_LANCE_SPEED = 8;
    final int DEFAULT_QUEEN_PARTICLE_SPEED = 20;

    final int DEFAULT_TIME_TO_DECAY_QUEEN = 30;
    final int DEFAULT_TIME_TO_DECAY_KNIGHT = 40;

    final int DEFAULT_CANNON_BALL_DMG = 50;
    final int DEFAULT_SLICE_DMG= 50;
    final int DEFAULT_SLAM_DMG= 100;
    final int DEFAULT_LANCE_DMG= 75;


    public EntityManager(GamePanel gamePanel, KeyHandler keyHandler, SoundManager soundManager, TextureManager textureManager, Player player) {
       this.gamePanel = gamePanel;
       this.soundManager = soundManager;
       this.textureManager = textureManager;
       this.keyHandler = keyHandler;
       this.player = player;
    }

    int CANNON_BALL_SIZE = 64;
    public void spawnCannonBall(int x, int y, Direction direction) {
        if (textureManager.rookImage != null) {
            int size = CANNON_BALL_SIZE; // size of the cannonball
            // spawn at top‐center of the rook
            int bx = x + (gamePanel.pieceWidth - size) / 2;
            // Append balls to the list of balls
            gamePanel.projectiles.add(new CannonBall(gamePanel, textureManager, bx, y, size, DEFAULT_CANNONBALL_SPEED, DEFAULT_CANNON_BALL_DMG, direction));
            //soundManager.playClip(soundManager.shootClip);
            soundManager.playClip("shoot");
        }
    }

    public void spawnEnemyCannonBall(int x, int y, int damage){
        BufferedImage skin;
        if (gamePanel.map == Map.SNOW) {
           skin = textureManager.cannonBallSnowEnemyImage;
        } else {
            skin = textureManager.cannonBallEarthEnemyImage;
        }


       if (skin != null) {
           int size = CANNON_BALL_SIZE;
           int bx = x + (gamePanel.pieceWidth - size) / 2;
           gamePanel.enemyBalls.add(new EnemyCannonBall(gamePanel, textureManager, bx, y, size, DEFAULT_ENEMY_CANNONBALL_SPEED, damage, skin));
           //soundManager.playClip(soundManager.shootClip);
           soundManager.playClip("shoot");
       }
    }
    public void spawnBossCannonBall(int x, int y){
        BufferedImage skin;
        if (gamePanel.map == Map.SNOW){
            skin = textureManager.cannonBallSnowEnemyImage;
        } else {
            skin = textureManager.cannonBallEarthEnemyImage;
        }

        if (skin != null) {
            int size = CANNON_BALL_SIZE * 2;
            int bx = x + (gamePanel.pieceWidth - size) / 2;
            gamePanel.enemyBalls.add(new EnemyCannonBall(gamePanel, textureManager, bx, y, size, DEFAULT_CANNONBALL_SPEED, DEFAULT_CANNON_BALL_DMG * 2, skin));
            //soundManager.playClip(soundManager.shootClip);
            soundManager.playClip("shoot");
        }
    }

    public void spawnBossLance(int x, int y){
        if (textureManager.enemyBishopImage != null){
            int size = 96;
            int bx = x + (gamePanel.pieceWidth - size) / 2;
            gamePanel.enemyBalls.add(new EnemyLance(gamePanel, textureManager, bx, y, size, DEFAULT_CANNONBALL_SPEED, DEFAULT_CANNON_BALL_DMG * 2, textureManager.bishopParticleImageDownLeft , Direction.DOWN_LEFT));
            //soundManager.playClip(soundManager.shootClip);
            soundManager.playClip("shoot");
        }
    }

    public void spawnExplosion(int x, int y){
        if (textureManager.explosionImage != null){
            int size = CANNON_BALL_SIZE;
            int bx = x + (gamePanel.pieceWidth - size) / 2;
            gamePanel.effects.add(new Explosion(gamePanel, textureManager, bx, y, size));
            //soundManager.playClip(soundManager.shootClip);
            soundManager.playClip("shoot");
        }

    }

    public void spawnLance(int x, int y, Direction facingDirection, BufferedImage skin){
        if (textureManager.bishopImage != null) {
            int size = 96; // size of the cannonball
            // spawn at top‐center of the rook
            int bx = x + (gamePanel.pieceWidth - size) / 2;
            int by = y + (gamePanel.pieceHeight - size) / 2;
            // Append balls to the list of balls
            gamePanel.projectiles.add(new BishopLance(gamePanel, textureManager, bx, by, size, DEFAULT_LANCE_SPEED ,DEFAULT_LANCE_DMG, skin, facingDirection));
            //soundManager.playClip(soundManager.holyClip);
            soundManager.playClip("holy");
        }
    }
    public void spawnQueenParticles(BufferedImage skin) {
        if (textureManager.queenImage != null) {
            int size = 128; // size of the cannonball
            int bx = player.x + (gamePanel.pieceWidth  - size) / 2;
            int by = player.y + (gamePanel.pieceHeight - size) / 2;
            gamePanel.projectiles.add(new QueenSlice(gamePanel, textureManager, bx, by, size, DEFAULT_QUEEN_PARTICLE_SPEED, DEFAULT_TIME_TO_DECAY_QUEEN, DEFAULT_SLICE_DMG, skin, player.facingDirection));
            //soundManager.playClip(soundManager.sliceClip);
            soundManager.playClip("slice");
        }
    }

    public void spawnKnightParticles() {
        BufferedImage skin;
        if (gamePanel.map == Map.SNOW){
           skin = textureManager.knightSnowParticleImage;
        } else {
            skin = textureManager.knightEarthParticleImage;
        }


       if (skin != null) {
           int size = 128; // size of the cannonball
           // spawn at top‐center of the rook
           int bx = player.x + (gamePanel.pieceWidth  - size) / 2;
           int by = player.y + (gamePanel.pieceHeight - size) / 2;

           gamePanel.projectiles.add(new KnightSmash(gamePanel, textureManager, bx, by, size, DEFAULT_QUEEN_PARTICLE_SPEED, DEFAULT_TIME_TO_DECAY_KNIGHT, DEFAULT_SLAM_DMG, player.facingDirection, skin));
           //soundManager.playClip(soundManager.smashClip);
           soundManager.playClip("smash");
       }
    }

    public void spawnPawns(int x, int y){
        if (textureManager.pawnImage != null){
            int size = gamePanel.pieceHeight;
            gamePanel.allies.add(new AllyPawn(gamePanel, soundManager, textureManager, gamePanel.collisionHandler, x, y, size, size, true));
        }
    }
}
