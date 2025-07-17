package main;

import enemies.*;

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

    private final String difficulty;

    public EnemyManager(GamePanel gamePanel, String difficulty){
        this.gamePanel = gamePanel;
        this.difficulty = difficulty;

        switch (difficulty){
            case "hard" -> {
                rookDamage = 50;
                bishopDamage = 75;
            }
            case "medium" -> {
                rookDamage = 40;
                bishopDamage = 55;
            }
            default -> {
                rookDamage = 25;
                bishopDamage = 40;
            }
        }
    }

    void spawnEnemy(int x, int y, int width, int height, PieceType type){
        switch (type){
            case PieceType.PAWN   -> gamePanel.enemies.add(new EnemyPawn(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height));
            case PieceType.ROOK   -> gamePanel.enemies.add(new EnemyRook(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height, rookDamage));
            case PieceType.BISHOP -> gamePanel.enemies.add(new EnemyBishop(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height, bishopDamage));
            case PieceType.KING   -> gamePanel.enemies.add(new EnemyKing(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, width, height));
        }

        if (shouldSpawnGuard){
            shouldSpawnGuard = false;
            gamePanel.enemies.add(new EnemyPawn(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, kingsX, kingsY, kingsSize, kingsSize));
            gamePanel.enemies.add(new EnemyPawn(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, kingsX, kingsY + 128, kingsSize, kingsSize));
            gamePanel.soundManager.playClip(gamePanel.soundManager.summonClip);
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
            case "hard" -> adjustDifficultyHard();
            case "medium" -> adjustDifficultyMedium();
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
