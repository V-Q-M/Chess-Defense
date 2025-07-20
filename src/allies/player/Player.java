package allies.player;

import enemies.Enemy;
import projectiles.Projectile;
import main.*;
import mapObjects.MapObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player extends Entity {
    // Player specific handlers
    KeyHandler keyHandler;
    AttackHandler attackHandler;
    MovementHandler movementHandler;

    public boolean isDead = false;
    // Is used to avoid being attacked a billion times at once
    public boolean isInvulnerable;
    private int invulnerableCounter;

    final int BASE_MOVE_SPEED = 6;
    public final int DASH_SPEED = 16;
    public final int LEAP_SPEED = 8;

    // Ability Cooldowns
    private final int ROOK_ABILITY_COOLDOWN = 40;
    private final int BISHOP_ABILITY_COOLDOWN = 55;
    private final int KNIGHT_ABILITY_COOLDOWN = 150;
    private final int QUEEN_ABILITY_COOLDOWN = 35;
    private final int KING_ABILITY_COOLDOWN = 250;

    // Initializes it
    public int abilityCoolDown = ROOK_ABILITY_COOLDOWN;

    // Used for the queen ability
    public boolean queenDashing = false;
    private int queenDashingCounter = 0;

    // CastleHealth
    public int health = 100;

    // Are the pieces alive?
    public boolean rookAlive = true;
    public boolean bishopAlive = true;
    public boolean knightAlive = true;
    public boolean queenAlive = true;
    public boolean kingAlive = true;

    // Base health constants
    public final int ROOK_BASE_HEALTH = 25;
    public final int KNIGHT_BASE_HEALTH = 50;
    public final int BISHOP_BASE_HEALTH = 20;
    public final int QUEEN_BASE_HEALTH = 50;
    public final int KING_BASE_HEALTH = 30;
    public final int PAWN_BASE_HEALTH = 10;

    // Mutable health values
    public int rookHealth = ROOK_BASE_HEALTH;
    public int knightHealth = KNIGHT_BASE_HEALTH;
    public int bishopHealth = BISHOP_BASE_HEALTH;
    public int queenHealth = QUEEN_BASE_HEALTH;
    public int kingHealth = KING_BASE_HEALTH;
    public int pawnHealth = PAWN_BASE_HEALTH;

    // Used in the swap feature
    public int swapCounter = 0;

    //public String facingDirection = "right";
    public Direction facingDirection = Direction.RIGHT;

    private boolean hasAttacked = false;
    public int attackCoolDownCounter = 0;

    // Very important. Manages the grid based walking
    public int targetX;
    public int targetY;
    private boolean isMoving = false;
    
    // Used in the swap feature
    Random random = new Random();
    ArrayList<PieceType> availablePieces = new ArrayList<>(List.of(
            PieceType.ROOK,
            PieceType.KNIGHT,
            PieceType.BISHOP,
            PieceType.QUEEN,
            PieceType.KING
    ));
    PieceType lastPiece;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, SoundManager soundManager, TextureManager textureManager, CollisionHandler collisionHandler, int startPositionX, int startPositionY){
        super(gamePanel, soundManager, textureManager, collisionHandler, startPositionX, startPositionY, 128, 128);
        this.keyHandler = keyHandler;
        this.attackHandler = new AttackHandler(gamePanel, textureManager, this);
        this.movementHandler = new MovementHandler(gamePanel, this);
        this.targetX = startPositionX;
        this.targetY = startPositionY;
        this.speed = BASE_MOVE_SPEED;
    }

    @Override
    public void update(){
        super.update();
        prepareForcedSwap();
    }

    private void forceSwap() {

        if (lastPiece != null && !availablePieces.contains(lastPiece)){
            availablePieces.add(lastPiece);
        }
        PieceType randomValue;
        do {
            int index=0;
            if (availablePieces.size() > 1){
                index = random.nextInt(availablePieces.size());
            }
            randomValue = availablePieces.get(index);
            //randomValue = availablePieces.get(0);
        } while (availablePieces.size() > 1 && randomValue == lastPiece);

        lastPiece = randomValue;
        System.out.println("Random Enum: " + randomValue);

        if (availablePieces.size() > 2) {
            availablePieces.remove(randomValue); // Temporarily remove it
        }

        selectPiece(randomValue);
        swapCounter = 0;
        attackCoolDownCounter = 0;
        hasAttacked = false;
    }

    private void prepareForcedSwap(){
        if (swapCounter >= 600) {
            forceSwap();
        } else if (swapCounter >= 570){
            gamePanel.swapSoon = false;
            swapCounter++;
        } else if (swapCounter >= 540){
            gamePanel.swapSoon = true;
            swapCounter++;
        } else if (swapCounter >= 510){
            gamePanel.swapSoon = false;
            swapCounter++;
        } else if (swapCounter >= 480){
            gamePanel.swapSoon = true;
            swapCounter++;
        } else {
            swapCounter++;
        }
    }

    public void revivePieces(){
        //soundManager.playClip(soundManager.healClip);
        soundManager.playClip("heal");
        rookAlive = true;
        queenAlive = true;
        bishopAlive = true;
        knightAlive = true;
        rookHealth = ROOK_BASE_HEALTH;
        bishopHealth = BISHOP_BASE_HEALTH;
        knightHealth = KNIGHT_BASE_HEALTH;
        queenHealth = QUEEN_BASE_HEALTH;
        if (!availablePieces.contains(PieceType.ROOK)){
            availablePieces.add(PieceType.ROOK);
        }
        if (!availablePieces.contains(PieceType.QUEEN)){
            availablePieces.add(PieceType.QUEEN);
        }
        if (!availablePieces.contains(PieceType.KNIGHT)){
            availablePieces.add(PieceType.KNIGHT);
        }
        if (!availablePieces.contains(PieceType.BISHOP)){
            availablePieces.add(PieceType.BISHOP);
        }
    }

    public void selectPiece(PieceType changePiece) {
        gamePanel.selectedPieceType = changePiece;
        swapCounter = 0;
        //soundManager.playClip(soundManager.swapClip);
        soundManager.playClip("swap");
        gamePanel.swapSoon = false;
        switch (changePiece) {
            case PieceType.ROOK -> {

                //gamePanel.selectedPiece = gamePanel.rookImage;
                this.baseSkin = textureManager.rookImage;
                this.hurtSkin = textureManager.rookHurtImage;

                abilityCoolDown = ROOK_ABILITY_COOLDOWN;
            }
            case PieceType.QUEEN -> {
                //gamePanel.selectedPiece = gamePanel.queenImage;
                abilityCoolDown = QUEEN_ABILITY_COOLDOWN;
                this.baseSkin = textureManager.queenImage;
                this.hurtSkin = textureManager.queenHurtImage;
            }
            case PieceType.KING -> {
                //gamePanel.selectedPiece = gamePanel.kingImage;
                abilityCoolDown = KING_ABILITY_COOLDOWN;
                this.baseSkin = textureManager.kingImage;
                this.hurtSkin = textureManager.kingHurtImage;
            }
            case PieceType.KNIGHT -> {
                //gamePanel.selectedPiece = gamePanel.knightImage;
                abilityCoolDown = KNIGHT_ABILITY_COOLDOWN;
                this.baseSkin = textureManager.knightImage;
                this.hurtSkin = textureManager.knightHurtImage;
            }
            case PieceType.BISHOP -> {
                //gamePanel.selectedPiece = gamePanel.bishopImage;
                abilityCoolDown = BISHOP_ABILITY_COOLDOWN;
                this.baseSkin = textureManager.bishopImage;
                this.hurtSkin = textureManager.bishopHurtImage;
            }
        }
        this.skin = baseSkin;
    }


    private boolean reachedBorder(){
        return collisionHandler.borderCollision(x, y, gamePanel.pieceWidth, gamePanel.pieceHeight, speed, facingDirection);
    }

    @Override
    public void move() {
        if (!isMoving) {
            if (gamePanel.selectedPieceType == PieceType.BISHOP){ // Is extra because he can only move diagonally
                analyzeInputBishop();
            } else {
                analyzeInput();
            }
        }

        // Grid based movement
        if (x == targetX && y == targetY) {
            isMoving = false;
        } else {
            isMoving = true;

            if (y < targetY) y = Math.min(y + speed, targetY);
            if (y > targetY) y = Math.max(y - speed, targetY);
            if (x < targetX) x = Math.min(x + speed, targetX);
            if (x > targetX) x = Math.max(x - speed, targetX);
        }
    }
    private void analyzeInput() {
        int deltaX = 0;
        int deltaY = 0;
        // Prioritize diagonal movement
        boolean up = keyHandler.goingUp;
        boolean down = keyHandler.goingDown;
        boolean left = keyHandler.goingLeft;
        boolean right = keyHandler.goingRight;

        if (up && left) {
            deltaY -= height;
            deltaX -= width;
            facingDirection = Direction.UP_LEFT;
        } else if (up && right) {
            deltaY -= height;
            deltaX += width;
            facingDirection = Direction.UP_RIGHT;
        } else if (down && left) {
            deltaY += height;
            deltaX -= width;
            facingDirection = Direction.DOWN_LEFT;
        } else if (down && right) {
            deltaY += height;
            deltaX += width;
            facingDirection = Direction.DOWN_RIGHT;
        } else if (up) {
            deltaY -= height;
            facingDirection = Direction.UP;
        } else if (down) {
            deltaY += height;
            facingDirection = Direction.DOWN;
        } else if (left){
            deltaX -= width;
            facingDirection = Direction.LEFT;
        } else if (right) {
            deltaX += width;
            facingDirection = Direction.RIGHT;
        }

        if ((deltaX != 0 || deltaY != 0) && !reachedBorder()) {
            targetX += deltaX;
            targetY += deltaY;
            isMoving = true;
        }

        // Handle attack input
        if (keyHandler.spacePressed) {
            keyHandler.spacePressed = false;
            if (!hasAttacked) {
                performAttack();
                hasAttacked = true;
            }
        }
    }

    // Bishop has diagonal only movement
    private void analyzeInputBishop() {
        int deltaX = 0;
        int deltaY = 0;

        boolean up = keyHandler.goingUp;
        boolean down = keyHandler.goingDown;
        boolean left = keyHandler.goingLeft;
        boolean right = keyHandler.goingRight;

        // Only allow diagonal movement (both one vertical and one horizontal key must be pressed)
        if (up && left) {
            deltaY -= height;
            deltaX -= width;
            facingDirection = Direction.UP_LEFT;
        } else if (up && right) {
            deltaY -= height;
            deltaX += width;
            facingDirection = Direction.UP_RIGHT;
        } else if (down && left) {
            deltaY += height;
            deltaX -= width;
            facingDirection = Direction.DOWN_LEFT;
        } else if (down && right) {
            deltaY += height;
            deltaX += width;
            facingDirection = Direction.DOWN_RIGHT;
        }

        // Only move if diagonal keys were pressed
        if ((deltaX != 0 && deltaY != 0) && !reachedBorder()) {
            targetX += deltaX;
            targetY += deltaY;
            isMoving = true;
        }

        // Handle attack input
        if (keyHandler.spacePressed) {
            keyHandler.spacePressed = false;
            if (!hasAttacked) {
                performAttack();
                hasAttacked = true;
            }
        }
    }

    public void updateCooldowns(){
        if (isInvulnerable){
            if (invulnerableCounter >= recoveryTime){
                isInvulnerable = false;
                invulnerableCounter = 0;
            } else if (invulnerableCounter > recoveryMarkerTime) {
                this.skin = baseSkin;
            }
            invulnerableCounter ++;
        }

        if (queenDashing){
           if (queenDashingCounter <= 30) {
               queenDashingCounter ++;
           } else {
               queenDashing = false;
               queenDashingCounter = 0;
               //isInvulnerable = false;
               speed = BASE_MOVE_SPEED;
           }
        }

        if (hasAttacked && attackCoolDownCounter < abilityCoolDown){
            attackCoolDownCounter++;
        } else {
            hasAttacked = false;
            attackCoolDownCounter = 0;
        }



    }

    private boolean slowed = false;
    public void checkCollision(){
        if (!isInvulnerable) {
            for (Enemy enemy : gamePanel.enemies) {
                if (collisionHandler.enemyCollision(enemy, this) && !enemy.hasAttacked) {
                    enemy.hasAttacked = true;
                    takeDamage(enemy.damage);
                    isInvulnerable = true;
                    this.skin = hurtSkin;
                    //soundManager.playClip(soundManager.hitClip);
                    soundManager.playClip("hit");
                }
            }
        }

        boolean isSlowed = false;
        for (MapObject mapObject : gamePanel.mapObjects) {
            if (collisionHandler.mapObjectMovementCollision(mapObject, this)) {
                isSlowed = true;
                break;
            }
        }
        if (isSlowed) {
            if (speed == BASE_MOVE_SPEED) {
                speed = Math.min(speed / 3, 1);
            }
        } else {
            if (speed < BASE_MOVE_SPEED) {
                speed = BASE_MOVE_SPEED;
            }
        }
        checkProjectileCollision();
    }

    private void checkProjectileCollision(){
        for (Projectile projectile : gamePanel.enemyBalls){
            if (collisionHandler.projectileEnemyCollision(projectile, this)){
                takeDamage(projectile.damage/3);
                projectile.isDead = true;
                gamePanel.entityManager.spawnExplosion(projectile.x, projectile.y);
                //soundManager.playClip(soundManager.hitClip);
                soundManager.playClip("hit");
            }
        }
    }

    private void takeDamage(int damageAmount){
        switch(gamePanel.selectedPieceType){
            case ROOK -> rookHealth -= damageAmount;
            case KNIGHT -> knightHealth -= damageAmount;
            case BISHOP -> bishopHealth -= damageAmount;
            case QUEEN -> queenHealth -= damageAmount;
            case KING -> kingHealth -= damageAmount;
            case PAWN -> pawnHealth -= damageAmount;
        }
    }

    public void checkAlive(){
        if (health <= 0){
            this.isDead = true;
            //soundManager.playClip(soundManager.deathClip);
            soundManager.playClip("death");
            gamePanel.gameOver = true;
        }

        if (rookHealth <= 0 && rookAlive){
            rookAlive = false;
            availablePieces.remove(PieceType.ROOK);
            lastPiece = null;
            forceSwap();
        }
        if (knightHealth <= 0 && knightAlive){
            knightAlive = false;
            availablePieces.remove(PieceType.KNIGHT);
            lastPiece = null;
            forceSwap();
        }
        if (bishopHealth <= 0 && bishopAlive){
            bishopAlive = false;
            availablePieces.remove(PieceType.BISHOP);
            lastPiece = null;
            forceSwap();
        }
        if (queenHealth <= 0 && queenAlive){
            queenAlive = false;
            availablePieces.remove(PieceType.QUEEN);
            lastPiece = null;
            forceSwap();
        }
        // specialCase - king dead
        if (kingHealth <= 0 && kingAlive){
            kingAlive = false;
            availablePieces.remove(PieceType.KING);
            lastPiece = null;
            health = 0;
        }
    }

    void performAttack() {
        switch (gamePanel.selectedPieceType) {
            // Add new characters here
            case ROOK   -> attackHandler.rookAttack(facingDirection);
            case BISHOP -> attackHandler.bishopAttack(facingDirection);
            case QUEEN  -> attackHandler.queenAttack(facingDirection);
            case KNIGHT -> attackHandler.knightAttack(facingDirection);
            case KING   -> attackHandler.kingAttack();
        }
    }

}
