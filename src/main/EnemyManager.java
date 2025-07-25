package main;

import enemies.bosses.KingBoss;
import enemies.bosses.PawnBoss;
import enemies.bosses.RookBoss;
import enemies.ghosts.GhostPawn;
import enemies.ghosts.GhostRook;
import enemies.regular.EnemyBishop;
import enemies.regular.EnemyPawn;
import enemies.regular.EnemyRook;
import enemies.zombies.ZombiePawn;
import enemies.zombies.ZombieRook;

public class EnemyManager {
    GamePanel gamePanel;
    private int lastSpawnCounter;
    private int spawnCoolDown = 240;
    private int difficultyScalar = 0;
    private final int size = 4 * 32;


    // Special logic for enemy king
    private int kingsX;
    private int kingsY;
    private int kingsSize;
    private boolean shouldSpawnGuard = false;
    private final int rookDamage;
    private final int bishopDamage;

    private final GameMode gameMode;
    private final GameMode difficulty;
    private double zombieSpawnChance = 0.1;
    private double ghostSpawnChance = 0.12;

    public EnemyManager(GamePanel gamePanel, GameMode gameMode, GameMode difficulty){
        this.gamePanel = gamePanel;
        this.gameMode = gameMode;
        this.difficulty = difficulty;

        switch (difficulty){
            case HARD -> {
                rookDamage = 50;
                bishopDamage = 75;
                ghostSpawnChance = 0.1;
                zombieSpawnChance = 0.15;
            }
            case MEDIUM -> {
                rookDamage = 40;
                bishopDamage = 55;
                ghostSpawnChance = 0.08;
                zombieSpawnChance = 0.1;
            }
            default -> {
                rookDamage = 25;
                bishopDamage = 40;
                ghostSpawnChance = 0.05;
                zombieSpawnChance = 0.08;
            }
        }
    }

    void spawnEnemy(int x, int y, int width, int height, PieceType type){
       boolean spawnGhost = Math.random() < ghostSpawnChance;
       boolean spawnZombie = Math.random() < zombieSpawnChance;
       if (gameMode != GameMode.SPOOKY){
            spawnGhost = false;
            spawnZombie = false;
       }

        if (spawnGhost) {
            switch (type) {
                case PieceType.PAWN ->
                        gamePanel.enemies.add(new GhostPawn(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height));
                case PieceType.ROOK ->
                        gamePanel.enemies.add(new GhostRook(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height, rookDamage));
                case PieceType.BISHOP ->
                        gamePanel.enemies.add(new EnemyBishop(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height, bishopDamage));
                case PieceType.KING ->
                        gamePanel.enemies.add(new KingBoss(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height));
            }
        } else if (spawnZombie){
            switch (type) {
               case PieceType.PAWN   -> gamePanel.enemies.add(new ZombiePawn(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height));
               case PieceType.ROOK   -> gamePanel.enemies.add(new ZombieRook(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height, rookDamage));
               case PieceType.BISHOP -> gamePanel.enemies.add(new EnemyBishop(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height, bishopDamage));
               case PieceType.KING   -> gamePanel.enemies.add(new KingBoss(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height));
            }
       } else {
           switch (type) {
               case PieceType.PAWN   -> gamePanel.enemies.add(new EnemyPawn(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height));
               case PieceType.ROOK   -> gamePanel.enemies.add(new EnemyRook(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height, rookDamage));
               case PieceType.BISHOP -> gamePanel.enemies.add(new EnemyBishop(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height, bishopDamage));
               case PieceType.KING   -> gamePanel.enemies.add(new KingBoss(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height));
           }
       }

        if (shouldSpawnGuard){
            shouldSpawnGuard = false;
            gamePanel.enemies.add(new EnemyPawn(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, kingsX, kingsY, kingsSize, kingsSize));
            gamePanel.enemies.add(new EnemyPawn(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, kingsX, kingsY + 128, kingsSize, kingsSize));
            //gamePanel.soundManager.playClip(gamePanel.soundManager.summonClip);
            gamePanel.soundManager.playClip("summon");
        }
    }
    public void spawnKingsGuard(int x, int y, int size){
        kingsX = x;
        kingsY = y;
        kingsSize = size;
        shouldSpawnGuard = true;
    }
    public void spawnPawnBoss(){
        int X = Main.WIDTH;
        int randomY = (int)(Math.random() * ((Main.HEIGHT-size) / size) ) * size;
        gamePanel.enemies.add(new PawnBoss(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, X, randomY, gamePanel.pieceWidth, gamePanel.pieceHeight));
    }
    public void spawnRookBoss(){
        int X = Main.WIDTH;
        int randomY = (int)(Math.random() * ((Main.HEIGHT-size) / size) ) * size;
        gamePanel.enemies.add(new RookBoss(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, X, randomY, gamePanel.pieceWidth, gamePanel.pieceHeight));
    }

    void updateSpawner(){
        if (lastSpawnCounter < spawnCoolDown) {
            lastSpawnCounter++;
        } else {
            lastSpawnCounter = 0;
            difficultyScalar ++;
            int X = Main.WIDTH;
            //int randomY = (int) (Math.random() * Main.HEIGHT);
            int randomY = (int)(Math.random() * (Main.HEIGHT / size)) * size;

            spawnEnemy(X,randomY, size, size, PieceType.PAWN);
            if (difficultyScalar % 10 == 0){
                spawnEnemy(X, randomY, size, size, PieceType.ROOK);
            }
        }
        switch (difficulty){
            case HARD -> adjustDifficultyHard();
            case MEDIUM -> adjustDifficultyMedium();
            default -> adjustDifficultyEasy();
        }
    }

    public void spawnKing(){
        int X = Main.WIDTH;
        int randomY = (int)(Math.random() * ((Main.HEIGHT-size) / size)) * size;
        spawnEnemy(X, randomY, size, size, PieceType.KING);
    }

    private int difficultyThreshold = 3;

    private void adjustDifficultyHard() {
        if (difficultyScalar > difficultyThreshold) {
            difficultyThreshold += 2;

            // Reduce cooldown by 4%, with a minimum floor
            spawnCoolDown *= 0.96;

            if (spawnCoolDown < 110) {
                spawnCoolDown = 110; // Set a lower bound
            }
        }
    }

    private void adjustDifficultyMedium() {
        if (difficultyScalar > difficultyThreshold) {
            difficultyThreshold += 2;

            // Reduce cooldown by 2%, with a minimum floor
            spawnCoolDown *= 0.98;

            if (spawnCoolDown < 120) {
                spawnCoolDown = 120; // Set a lower bound
            }
        }
    }

    private void adjustDifficultyEasy() {
        if (difficultyScalar > difficultyThreshold) {
            difficultyThreshold += 2;

            // Reduce cooldown by 1%, with a minimum floor
            spawnCoolDown *= 0.99;

            if (spawnCoolDown < 130) {
                spawnCoolDown = 130; // Set a lower bound
            }
        }
    }
}
