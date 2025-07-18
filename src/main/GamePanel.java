package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import allies.*;
import allies.player.Player;
import enemies.Enemy;
import entities.Projectile;
import mapObjects.ImmovableObject;
import mapObjects.Rock;

public class GamePanel extends JPanel implements Runnable{

  private Thread gameThread;
  private boolean running = false;
  private final int FPS = 60;
  private final long FRAME_TIME = 1000000000 / FPS;

  // Game variables
  private boolean DEBUG_MODE = false;
  public boolean gamePaused = false;
  public boolean castleGotHit = false;
  private boolean piecesGotRevived = false;
  private boolean wallRepaired = false;
  private boolean turretsRepaired = false;
  private boolean bishopsRevived = false;
  public int score = 0;
  public Map map = Map.EARTH;

  boolean gameStart = true;
  public boolean swapSoon = false;
  private final int CASTLE_MAX_HEALTH = 100;
  public int castleHealth = CASTLE_MAX_HEALTH;
  public boolean gameOver = false;

  private Font gameFont;
  private Font gameFontTiny;

  // text for localization
  private String startingText = "Starting in: ";
  private String swappingSoonText = "Swapping soon!";
  private String scoreText = "Score:";
  private String gameOverText = "Game Over!";
  private String quitGameText = "Quit Game?";
  private String restartText = "Restart?";
  private String resumeText = "Resume?";
  private String castleHealthText = "Castle health";

  private boolean redFlashScreen = false;
  private boolean blueFlashScreen = false;

  // uses the enum
  public PieceType selectedPieceType;
  public int pieceWidth = 128;
  public int pieceHeight = 128;

  public int squareSize = 128;

  // Builds the background

  // Im scaling 32x32 Textures so that they look nicer
  public final int SCALE = 8;

  // carries particle effects
  public final List<Projectile> projectiles = new ArrayList<>();
  public final List<Projectile> effects = new ArrayList<>();
  // Carry enemy projectiles separate so that the player doesn't have to loop through all projectiles
  public final List<Projectile> enemyBalls = new ArrayList<>();
  // carries enemies
  public final List<Enemy>  enemies = new ArrayList<>();
  // carries wall and allies
  public final List<Ally> allies = new ArrayList<>();
  public final List<Ally> wall = new ArrayList<>();
  public final List<Ally> turrets = new ArrayList<>();
  // carries other objects
  public final List<ImmovableObject> mapObjects = new ArrayList<>();

  // Necessary managers
  KeyHandler keyHandler = new KeyHandler(this);
  CollisionHandler collisionHandler = new CollisionHandler(this);
  SoundManager soundManager = new SoundManager(this);
  TextureManager textureManager = new TextureManager(this);

  // Start position at ca. center
  int startX = pieceWidth*4;
  int startY = pieceHeight*4;

  // Rest of managers and player
  Player player = new Player(this, keyHandler, soundManager, textureManager, collisionHandler, startX, startY);
  public EnemyManager enemyManager;
  public EntityManager entityManager = new EntityManager(this, keyHandler, soundManager, textureManager, player);

  // Upgrades. Available in the shop
  private final boolean turretUpgradeUnlocked = true;
  private final boolean priestUpgradeUnlocked = true;
  private final boolean kingUpgradeUnlocked = true;
  private final boolean queenUpgradeUnlocked = false;

  private String difficulty;

  public GamePanel(String difficulty) {
    // Window size
    setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
    setFocusable(true);
    requestFocusInWindow();
    addKeyListener(keyHandler);

    // setup
    this.difficulty = difficulty;
    System.out.println("DIFFICULTY: " + difficulty);
    enemyManager = new EnemyManager(this, difficulty);
    textureManager.loadImages(map);
    //this.loadFonts();
    gameFont = FontManager.gameFont80;
    gameFontTiny = FontManager.gameFont25;
    soundManager.loadSounds();
    getSettings();

    // Builds the pawnwall on the left
    buildWall();
    if (turretUpgradeUnlocked){
      spawnTurrets();
    }
    if (priestUpgradeUnlocked){
      spawnPriests();
    }

    player.selectPiece(PieceType.ROOK);

    mapObjects.add(new Rock(this, soundManager, textureManager, collisionHandler, 6*128, 6*128, 128, 128));

    running = true;
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run(){
    long lastTime = System.nanoTime();

    while(running){
      long now = System.nanoTime();
      long elapsed = now - lastTime;

      if(elapsed >= FRAME_TIME){
        update();
        repaint();
        lastTime = now;
      } else {
        try {
          long sleepTime = (FRAME_TIME - elapsed) / 1000000;
          if (sleepTime > 0) Thread.sleep(sleepTime);
        } catch (InterruptedException e){
          e.printStackTrace();
        }
      }
    }
  }




  private void getSettings(){
    if (SettingsManager.musicOff){
      soundManager.stopMusic();
      System.out.println("MUSIC OFF");
    } else {
      soundManager.startMusic();
      System.out.println("MUSIC ON");
    }
    if (SettingsManager.languageGerman){
      startingText = "Starte in: ";
      swappingSoonText = "Wechsele bald!";
      scoreText = "Punkte:";
      gameOverText = "Verloren!";
      quitGameText = "Spiel verlassen?";
      restartText = "Neustarten?";
      resumeText = "Fortfahren?";
      castleHealthText = "Schloss Zustand";
    }

    if (SettingsManager.debugMode){
      DEBUG_MODE = true;
    }
  }

  // The pawn wall on the left, including the two turrets /rooks
  private void buildWall(){
    for (int i = 0; i < 8; i++){
      AllyPawn pawnGuard = new AllyPawn(this, soundManager, textureManager, collisionHandler, squareSize, i * squareSize, squareSize, squareSize, false);
      allies.add(pawnGuard);
      wall.add(pawnGuard);
    }
  }
  private void spawnTurrets(){
    allies.add(new AllyRook(this, soundManager, textureManager, collisionHandler, 0, 0, squareSize, squareSize));
    allies.add(new AllyRook(this, soundManager, textureManager, collisionHandler, 0, 7 * squareSize, squareSize, squareSize));
  }

  private void spawnPriests(){
    allies.add(new AllyBishop(this, soundManager, textureManager, collisionHandler, 0, 2 * squareSize, squareSize, squareSize, true));
    allies.add(new AllyBishop(this, soundManager, textureManager, collisionHandler, 0, 5 * squareSize, squareSize, squareSize, false));
  }

  public void rebuildTurrets(){
    for (Ally turret : turrets){
      turret.isDead = true;
    }
    spawnTurrets();
  }

  public void rebuildPawnWall(){
    for (Ally pawn : wall){
      if (pawn.isDead){
        pawn.health = pawn.maxHealth;
        pawn.isDead = false;
        allies.add(pawn);
      }
    }
    soundManager.playClip(soundManager.healClip);
  }
  public void rebuildSomePawns(){
    int pawnsRebuild = 0;
    for (Ally pawn : wall){
      if (pawnsRebuild >= 3){
        break;
      }
      else if (pawn.isDead){
        pawnsRebuild ++;
        pawn.health = pawn.maxHealth;
        pawn.isDead = false;
        allies.add(pawn);
      }
    }
    soundManager.playClip(soundManager.healClip);
  }

  // Image loader. Very simple. Might expand to ImageAtlas


  // A simple bobbing animation
  int animationFrame = 1;
  int animationCounter = 0;
  public void simpleAnimation() {
    animationCounter++;

    if (animationCounter > 45) {
      animationCounter = 0;
    }

    if (animationCounter <= 15) {
      animationFrame = 1;
    } else if (animationCounter <= 30) {
      animationFrame = 2;
    } else {
      animationFrame = 3;
    }
  }

  int lastHealedCounter = 0;
  int castleHitElapsedTime = 0;
  int pieceRevivedElapsedTime = 0;
  int wallRepairedElapsedTime = 0;
  int turretRepairedElapsedTime = 0;
  int bishopRevivedElapsedTime = 0;
  int levelCounter = 1;
  public boolean enemyKingSlain = false;
  public boolean pawnBossSlain = false;
  public boolean rookBossSlain = false;
  public boolean bishopBossSlain = false;

  int threshHoldBossSpawns = 5000;

  private void healthCastle(int amount) {
    if (amount > 0){
      if (castleHealth + amount < CASTLE_MAX_HEALTH) {
        castleHealth += amount;
      } else {
        castleHealth = CASTLE_MAX_HEALTH;
      }
    }
  }

  private int bossRotationIndex = 0;

  private void castleLogic(){
    if (lastHealedCounter > 180){
      healthCastle(1);
      lastHealedCounter = 0;
    } else {
      lastHealedCounter++;
    }


   if (castleGotHit) {

     if (!redFlashScreen){
       redFlashScreen = true;
       soundManager.playClip(soundManager.castleHitClip);
     }
     if (castleHitElapsedTime > 20) {
       castleGotHit = false;
       castleHitElapsedTime = 0;
       redFlashScreen = false;
     } else {
       castleHitElapsedTime++;
     }
   }


    if (score > threshHoldBossSpawns * levelCounter){
      levelCounter++;
      switch(bossRotationIndex){
        case 0 -> {
          enemyManager.spawnKing();
          bossRotationIndex ++;
        }
        case 1 -> {
          enemyManager.spawnPawnBoss();
          bossRotationIndex++;
        }
        case 2 -> {
          enemyManager.spawnKing();
          bossRotationIndex ++;
        }
        case 3 -> {
          enemyManager.spawnRookBoss();
          bossRotationIndex = 0;
        }
      }
      soundManager.playClip(soundManager.kingSpawnClip);
    }

    // Revive everyone but the king as there is only one true king
    if (enemyKingSlain){
      if (!piecesGotRevived){
        piecesGotRevived = true;
        blueFlashScreen = true;
        healthCastle(20);
        player.revivePieces();
      }

      if (pieceRevivedElapsedTime > 60){
        enemyKingSlain = false;
        piecesGotRevived = false;
        pieceRevivedElapsedTime = 0;
        blueFlashScreen = false;
      } else {
        pieceRevivedElapsedTime++;
      }
    }

    if (pawnBossSlain){
      if (!wallRepaired){
        wallRepaired = true;
        blueFlashScreen = true;
        rebuildPawnWall();
      }

      if (wallRepairedElapsedTime > 60){
        pawnBossSlain = false;
        wallRepaired = false;
        blueFlashScreen = false;
      } else {
        wallRepairedElapsedTime++;
      }
    }

    if (rookBossSlain){
      if (!turretsRepaired){
        turretsRepaired = true;
        blueFlashScreen = true;
        rebuildTurrets();
      }

      if (turretRepairedElapsedTime > 60){
        rookBossSlain = false;
        turretsRepaired = false;
        blueFlashScreen = false;
      } else {
        turretRepairedElapsedTime++;
      }

      if (bishopBossSlain){
        if(!bishopsRevived){
          bishopsRevived = true;
          blueFlashScreen = true;

        }

        if (bishopRevivedElapsedTime > 60){
          bishopBossSlain = false;
          bishopsRevived = false;
          blueFlashScreen = false;
        } else {
          bishopRevivedElapsedTime++;
        }
      }
    }

    if (score > 10000 * reinforcementCount){
      reinforcementCount ++;
      System.out.println("BIGBOSS would spawn");
    }
  }

  private int reinforcementCount = 1;

  public int scoreIncreaseElapsedTime = 0;
  public void update() {
    if(gamePaused){
      updatePauseMenu();
    }
    if (gameOver){
      updateGameOverScreen();
    }

    if (!gameOver && !gamePaused) {
      castleLogic();

      if (scoreIncreaseElapsedTime > 6){
        scoreIncreaseElapsedTime = 0;
        score++;
      } else {
        scoreIncreaseElapsedTime ++;
      }

      simpleAnimation();

      if (!player.isDead) {
        player.playerUpdate();
      }

      for (ImmovableObject mapObject : mapObjects){
        mapObject.update();
      }
      mapObjects.removeIf(mapObject -> mapObject.isDead);


      for (Projectile projectile : projectiles){
        projectile.update();
      }
      projectiles.removeIf(projectile -> projectile.isDead);

      // Effects are separate to avoid bugs
      for (Projectile effect : effects){
        effect.update();
      }
      effects.removeIf(effect -> effect.isDead);

      // Enemy projectiles are seperate to avoid looping through all projectiles for player
      for (Projectile enemyBall : enemyBalls){
        enemyBall.update();
      }
      enemyBalls.removeIf(enemyBall -> enemyBall.isDead);

      for (Enemy enemy : enemies) {
        enemy.update();
      }
      enemies.removeIf(enemy -> enemy.isDead);

      for (Ally ally : allies){
        ally.update();
      }
      allies.removeIf(ally -> ally.isDead);

      enemyManager.updateSpawner();
      gameUpdate();
    }
    if (keyHandler.escapePressed && !gameOver){
      keyHandler.escapePressed = false;
      gamePaused = !gamePaused;
    }
    if (keyHandler.enterPressed){
      keyHandler.enterPressed = false;
    }

  }

  private void updatePauseMenu(){
    if(keyHandler.goingDown){
      keyHandler.goingDown = false;
      soundManager.playClip(soundManager.buttonHoverClip);
      pauseMenuIndex++;
    } else if (keyHandler.goingUp){
      keyHandler.goingUp = false;
      soundManager.playClip(soundManager.buttonHoverClip);
      pauseMenuIndex--;
    }
    if(keyHandler.enterPressed){
      keyHandler.enterPressed = false;
      if (pauseMenuIndex % 2 == 0){
        soundManager.playClip(soundManager.buttonClickClip);
        soundManager.stopMusic();
        Main.returnToMenu(this);
      } else if (pauseMenuIndex % 2 == 1){
        soundManager.playClip(soundManager.buttonClickClip);
        gamePaused = false;
      }
    }
  }


  private void updateGameOverScreen(){
    if(keyHandler.goingDown){
      keyHandler.goingDown = false;
      soundManager.playClip(soundManager.buttonHoverClip);
      pauseMenuIndex++;
    } else if (keyHandler.goingUp){
      keyHandler.goingUp = false;
      soundManager.playClip(soundManager.buttonHoverClip);
      pauseMenuIndex--;
    }
    if(keyHandler.enterPressed){
      keyHandler.enterPressed = false;
      if (pauseMenuIndex % 2 == 0){
        soundManager.playClip(soundManager.buttonClickClip);
        soundManager.stopMusic();
        Main.returnToMenu(this);
      } else if (pauseMenuIndex % 2 == 1){
        soundManager.playClip(soundManager.buttonClickClip);
        soundManager.stopMusic();
        Main.startMainGame(null, this, difficulty);
      }
    }
  }
  int gameStartCounter = 0;
  String startMessage = startingText + 3;
  private void startMessagePopUP(){
    if (gameStartCounter > 180) {
      gameStart = false;
      gameStartCounter = 0;
    } else if (gameStartCounter > 150) {
      startMessage = "";
      gameStartCounter++;
    } else if (gameStartCounter > 120) {
      startMessage = startingText + 1;
      gameStartCounter++;
    } else if (gameStartCounter > 90){
      startMessage = "";
      gameStartCounter++;
    } else if (gameStartCounter > 60){
      startMessage = startingText + 2;
      gameStartCounter++;
    } else if (gameStartCounter > 30){
      startMessage = "";
      gameStartCounter++;
    } else {
      gameStartCounter++;
    }
  }


  private void gameUpdate(){
    if (gameStart) {
      startMessagePopUP();
    }

    // Prepare for game over
    if (castleHealth <= 0){
      gameOver = true;
      castleHealth = 0;
      swapSoon = false;
      soundManager.stopMusic();
      repaint();
    }

  }

  // Carefull. Render method
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

    drawBackground(g2d);
    drawPlayer(g2d);
    drawAllies(g2d);
    drawEnemies(g2d);
    drawEntities(g2d);
    drawHealthBars(g2d);
    drawUI(g2d);
  }

  private void drawBackground(Graphics2D g2d){
    g2d.drawImage(textureManager.mapImage,0,0,this);
    for (ImmovableObject mapObject : mapObjects){
      g2d.drawImage(mapObject.skin, mapObject.x, mapObject.y, this);
    }
  }


  private final Color LIME = new Color(0,200,50);
  // Helper method for building health-bars
  private void createHealthBar(Graphics2D g2d, int x, int y, int width, int height, int health, int maxHealth, Color healthColor){
    g2d.setColor(new Color (200,10,10));
    g2d.fillRect(x, y - height - 5, width, height);
    int greenWidth= (int) (width * health / maxHealth);
    g2d.setColor(healthColor);
    g2d.fillRect(x, y - height - 5, greenWidth, height);
  }

  int animationOffset = 2;
  private void drawPlayer(Graphics2D g2d){
    // Draw selectedPiece at current position
    if (player.skin != null && !player.isDead) {
      if (animationFrame == 2){
        g2d.drawImage(player.skin, player.x - animationOffset, player.y + animationOffset, pieceWidth + animationOffset * 2, pieceHeight, this);
      } else if (animationFrame == 3){
        g2d.drawImage(player.skin, player.x + animationOffset, player.y - animationOffset, pieceWidth - animationOffset * 2, pieceHeight, this);
      } else {
        g2d.drawImage(player.skin, player.x, player.y, pieceWidth, pieceHeight, this);
      }

      // CoolDown UI
      float factor = 0f;
      if (player.abilityCoolDown != 0){
        factor = player.attackCoolDownCounter / (float) player.abilityCoolDown;
      }

      if (factor == 0){
        switch (player.facingDirection) {
          case UP_LEFT    -> g2d.drawImage(textureManager.arrowUpLeftImage, player.x - 56, player.y - 56, pieceWidth, pieceHeight, this);
          case UP_RIGHT   -> g2d.drawImage(textureManager.arrowUpRightImage, player.x + 56, player.y - 56, pieceWidth, pieceHeight, this);
          case DOWN_LEFT  -> g2d.drawImage(textureManager.arrowDownLeftImage, player.x - 56, player.y + 56, pieceWidth, pieceHeight, this);
          case DOWN_RIGHT -> g2d.drawImage(textureManager.arrowDownRightImage, player.x + 56, player.y + 56, pieceWidth, pieceHeight, this);
          case UP         -> g2d.drawImage(textureManager.arrowUpImage, player.x, player.y - 56, pieceWidth, pieceHeight, this);
          case DOWN       -> g2d.drawImage(textureManager.arrowDownImage, player.x, player.y + 56, pieceWidth, pieceHeight, this);
          case LEFT       -> g2d.drawImage(textureManager.arrowLeftImage, player.x - 56, player.y, pieceWidth, pieceHeight, this);
          default         -> g2d.drawImage(textureManager.arrowRightImage, player.x + 56, player.y, pieceWidth, pieceHeight, this);
        }
      } else {
        switch (player.facingDirection) {
          case UP_LEFT    -> g2d.drawImage(textureManager.greyArrowUpLeftImage, player.x - 56, player.y - 56, pieceWidth, pieceHeight, this);
          case UP_RIGHT   -> g2d.drawImage(textureManager.greyArrowUpRightImage, player.x + 56, player.y - 56, pieceWidth, pieceHeight, this);
          case DOWN_LEFT  -> g2d.drawImage(textureManager.greyArrowDownLeftImage, player.x - 56, player.y + 56, pieceWidth, pieceHeight, this);
          case DOWN_RIGHT -> g2d.drawImage(textureManager.greyArrowDownRightImage, player.x + 56, player.y + 56, pieceWidth, pieceHeight, this);
          case UP         -> g2d.drawImage(textureManager.greyArrowUpImage, player.x, player.y - 56, pieceWidth, pieceHeight, this);
          case DOWN       -> g2d.drawImage(textureManager.greyArrowDownImage, player.x, player.y + 56, pieceWidth, pieceHeight, this);
          case LEFT       -> g2d.drawImage(textureManager.greyArrowLeftImage, player.x - 56, player.y, pieceWidth, pieceHeight, this);
          default         -> g2d.drawImage(textureManager.greyArrowRightImage, player.x + 56, player.y, pieceWidth, pieceHeight, this);
        }
      }
      // Draw hitbox
      if (DEBUG_MODE) {
        g2d.setColor(Color.red);
        g2d.drawRect(player.x , player.y, pieceWidth, pieceHeight);
      }
    }
  }

  private void drawAllies(Graphics2D g2d){
    for (Ally ally : allies) {
      if (animationFrame == 2){
        g2d.drawImage(ally.skin, ally.x - animationOffset, ally.y + animationOffset, ally.width + animationOffset * 2, ally.height, this);
      } else if (animationFrame == 3){
        g2d.drawImage(ally.skin, ally.x + animationOffset, ally.y - animationOffset, ally.width - animationOffset * 2, ally.height, this);
      } else {
        g2d.drawImage(ally.skin, ally.x, ally.y, ally.width, ally.height, this);
      }
      // Draw hitbox
      if (DEBUG_MODE){
        g2d.setColor(Color.red);
        g2d.drawRect(ally.x, ally.y, ally.width, ally.height);
      }
    }
  }

  private void drawEntities(Graphics2D g2d){
    for (Projectile projectile : projectiles) {
      g2d.drawImage(projectile.skin, projectile.x, projectile.y, projectile.width, projectile.height, this);
      if (DEBUG_MODE){
        g2d.drawRect(projectile.x, projectile.y, projectile.width, projectile.height);
      }
    }
    for (Projectile projectile : effects){
      g2d.drawImage(projectile.skin, projectile.x, projectile.y, projectile.width, projectile.height, this);
      if (DEBUG_MODE){
        g2d.drawRect(projectile.x, projectile.y, projectile.width, projectile.height);
      }
    }
    for (Projectile projectile : enemyBalls){
      g2d.drawImage(projectile.skin, projectile.x, projectile.y, projectile.width, projectile.height, this);
      if (DEBUG_MODE){
        g2d.drawRect(projectile.x, projectile.y, projectile.width, projectile.height);
      }
    }
  }

  private void drawEnemies(Graphics2D g2d){
    for (Enemy enemy : enemies) {
      if (animationFrame == 2){
        g2d.drawImage(enemy.skin, enemy.x - animationOffset, enemy.y - animationOffset, enemy.width + animationOffset * 2, enemy.height, this);
      } else if (animationFrame == 3){
        g2d.drawImage(enemy.skin, enemy.x + animationOffset, enemy.y + animationOffset, enemy.width - animationOffset * 2, enemy.height, this);
      } else {
        g2d.drawImage(enemy.skin, enemy.x, enemy.y, enemy.width, enemy.height, this);
      }
      // draw hitbox
      if (DEBUG_MODE){
        g2d.setColor(Color.red);
        g2d.drawRect(enemy.x, enemy.y, enemy.width, enemy.height);
      }
    }
  }

  // Renders the healthbars
  void drawHealthBars(Graphics2D g2d){
    // Personal choice - only show health-bar when not at full health
    for (Enemy enemy : enemies) {
      if (enemy.isBoss){
        createHealthBar(g2d, enemy.x, enemy.y, enemy.width, 20, enemy.health, enemy.maxHealth, Color.YELLOW);
      }
      else if (enemy.health != enemy.maxHealth) {
        createHealthBar(g2d, enemy.x, enemy.y, enemy.width, 15, enemy.health, enemy.maxHealth, LIME);
      }

    }

    for (Ally ally : allies){
      if (ally.health != ally.maxHealth){
        createHealthBar(g2d, ally.x, ally.y, ally.width, 15, ally.health, ally.maxHealth, LIME);
      }
    }

    int playerMaxHealth = 100;
    int playerHealth = playerMaxHealth;
    switch(selectedPieceType){
      case ROOK -> {
        playerHealth = player.rookHealth;
        playerMaxHealth = player.ROOK_BASE_HEALTH;
      }
      case KNIGHT -> {
        playerHealth = player.knightHealth;
        playerMaxHealth = player.KNIGHT_BASE_HEALTH;
      }
      case BISHOP -> {
        playerHealth = player.bishopHealth;
        playerMaxHealth = player.BISHOP_BASE_HEALTH;
      }
      case QUEEN -> {
        playerHealth = player.queenHealth;
        playerMaxHealth = player.QUEEN_BASE_HEALTH;
      }
      case KING -> {
        playerHealth = player.kingHealth;
        playerMaxHealth = player.KING_BASE_HEALTH;
      }
    }

    // player.Player health-bar always on top
    if (!player.isDead) {
      createHealthBar(g2d, player.x, player.y, pieceWidth, 20, playerHealth, playerMaxHealth, LIME);
    }

    // Castle healthbar
    createHealthBar(g2d, 350, 60, 1200, 20, castleHealth, 100, Color.GRAY);
    g2d.setColor(Color.YELLOW);
    drawText(g2d, 0, 90, gameFontTiny, castleHealthText);
  }


  private int pauseMenuIndex = 100000;
  void drawUI(Graphics2D g2d) {
    g2d.setFont(gameFont);

    drawBottomBar(g2d);
    drawAbilityBar(g2d);

    if (gameOver) {
      drawGameOverScreen(g2d);
    }

    if (gamePaused) {
      drawPauseMenu(g2d);
    }

    if (gameStart && !gamePaused) {
      g2d.setColor(Color.WHITE);
      drawText(g2d,0,0, gameFont, startMessage);
    }
    if (swapSoon && !gamePaused && !gameOver){
      g2d.setColor(Color.YELLOW);
      drawText(g2d,0,0, gameFont, swappingSoonText);
    }

    if (!blueFlashScreen && redFlashScreen){
      g2d.setColor(new Color(255, 100 ,100, 50));
      g2d.fillRect(0,0, Main.WIDTH, Main.HEIGHT);
    }
    if (blueFlashScreen){
      g2d.setColor(new Color(100, 145 ,255, 100));
      g2d.fillRect(0,0, Main.WIDTH, Main.HEIGHT);
    }
  }

  private void drawBottomBar(Graphics2D g2d){
    int xPos = 653;
    int yPos = Main.HEIGHT- 110;
    int size = 96;
    int crossSize = 116;

    g2d.drawImage(textureManager.bottomBarImage,0, Main.HEIGHT - 56,  this);

    g2d.setColor(Color.WHITE);
    drawText(g2d, 10, Main.HEIGHT -12, gameFontTiny, scoreText + score);

    g2d.drawImage(textureManager.rookImage, xPos, yPos, size, size, this);
    g2d.drawImage(textureManager.knightImage, xPos + 129, yPos, size, size, this);
    g2d.drawImage(textureManager.kingImage, xPos + 259, yPos, size, size, this);
    g2d.drawImage(textureManager.queenImage, xPos + 387, yPos, size, size, this);
    g2d.drawImage(textureManager.bishopImage, xPos + 516, yPos, size, size, this);
    if (!player.rookAlive){
      g2d.drawImage(textureManager.unavailablePieceImage, xPos-10, yPos -5, crossSize, crossSize, this);
    }
    if (!player.knightAlive){
      g2d.drawImage(textureManager.unavailablePieceImage, xPos + 119, yPos - 5, crossSize, crossSize, this);
    }
    if (!player.kingAlive){
      g2d.drawImage(textureManager.unavailablePieceImage, xPos + 249, yPos - 5, crossSize, crossSize, this);
    }
    if (!player.queenAlive){
      g2d.drawImage(textureManager.unavailablePieceImage, xPos + 377, yPos - 5, crossSize, crossSize, this);
    }
    if (!player.bishopAlive){
      g2d.drawImage(textureManager.unavailablePieceImage, xPos + 505, yPos - 5, crossSize, crossSize, this);
    }
  }

  private void drawGameOverScreen(Graphics2D g2d){
    g2d.setColor(new Color(0,0,0,200));
    g2d.fillRect(0,0,Main.WIDTH, Main.HEIGHT);

    g2d.setColor(Color.RED);
    drawText(g2d,0,420, gameFont, gameOverText);

    if(pauseMenuIndex % 2 == 0){
      g2d.setColor(Color.YELLOW);
    } else {
      g2d.setColor(Color.WHITE);
    }
    drawText(g2d,0,550, gameFont, quitGameText);
    if(pauseMenuIndex % 2 == 1){
      g2d.setColor(Color.YELLOW);
    } else {
      g2d.setColor(Color.WHITE);
    }
    drawText(g2d, 0, 680, gameFont, restartText);
  }

  private void drawPauseMenu(Graphics2D g2d){
    g2d.setColor(new Color(0,0,0,200));
    g2d.fillRect(0,0,Main.WIDTH, Main.HEIGHT);

    if(pauseMenuIndex % 2 == 0){
      g2d.setColor(Color.YELLOW);
    } else {
      g2d.setColor(Color.WHITE);
    }
    drawText(g2d,0,0, gameFont, quitGameText);
    if(pauseMenuIndex % 2 == 1){
      g2d.setColor(Color.YELLOW);
    } else {
      g2d.setColor(Color.WHITE);
    }
    drawText(g2d, 0, 700, gameFont, resumeText);
  }

  private void drawAbilityBar(Graphics2D g2d){
  }

  // Helper method for rendering formatted text
  private void drawText(Graphics2D g2d,int xOverride, int yOverride, Font gameFont, String text){
    g2d.setFont(gameFont);
    // Get font metrics for positioning
    FontMetrics fm = g2d.getFontMetrics();
    int textWidth = fm.stringWidth(text);
    int textHeight = fm.getHeight();

    int x = (getWidth() - textWidth) / 2;
    int y = (getHeight() - textHeight) / 2 + fm.getAscent(); // ascent = baseline offset

    if (xOverride != 0 && yOverride != 0){
      g2d.drawString(text, xOverride, yOverride);
    } else if (xOverride != 0){
      g2d.drawString(text, xOverride, y);
    } else if (yOverride != 0){
      g2d.drawString(text, x, yOverride);
    }
    else {
      g2d.drawString(text, x, y);
    }
  }
}
