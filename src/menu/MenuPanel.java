package menu;

import main.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MenuPanel extends JPanel {
    MenuKeyHandler keyHandler = new MenuKeyHandler(this);
    SoundManager soundManager = new SoundManager(this, null);
    BufferedImage backgroundImg;
    BufferedImage normalBackgroundImg;
    BufferedImage hauntedBackgroundImg;
    BufferedImage knightImage;
    BufferedImage zombieRookImage;
    BufferedImage ghostRookImage;
    BufferedImage pawnImage;
    BufferedImage ghostPawnImage;
    BufferedImage whitePawnImage;
    BufferedImage whiteRookImage;
    BufferedImage whiteKingImage;
    BufferedImage rookImage;
    BufferedImage kingImage;
    BufferedImage ghostKingImage;

    Font gameFont;
    Font gameFontSmall;
    Font gameFontTiny;
    Font gameFontMedium;

    private int buttonIndexY = 100000;


    // Tracking the yellow hover effect
    private boolean hoveringPlay = false;
    private boolean hoveringShop = false;
    private boolean hoveringQuit = false;
    private boolean hoveringSettings = false;
    private boolean hoveringHelp = false;

    private boolean hoveringNormalGamemode;
    private boolean hoveringSpookyGamemode;

    private boolean hoveringEasyMode = false;
    private boolean hoveringMediumMode = false;
    private boolean hoveringHardMode = false;
    private boolean hoveringGoBack = false;

    private boolean hoveringMusicSettingButton = false;
    private boolean hoveringVolumeButton = false;
    private boolean hoveringLanguageSettingButton = false;
    private boolean hoveringDebugSettingButton = false;

    private boolean hoveringShopItemOne = false;
    private boolean hoveringShopItemTwo = false;
    private boolean hoveringShopItemThree = false;

    // If one of these is on, it shows that side menu
    private boolean showingShop = false;
    private boolean showingHelp = false;
    private boolean showingSettings = false;
    private boolean showingDifficultySelection = false;
    private boolean showingModeSelection = false;


    private Color textColor = Color.WHITE;
    private Color hoverColor = Color.YELLOW;

    // Carries the text values of the main menu

    // Window size
    public MenuPanel() {
        setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(keyHandler);

        this.loadImages();
        FontManager.loadFonts();
        gameFont = FontManager.gameFont70;
        gameFontMedium = FontManager.gameFont55;
        gameFontSmall = FontManager.gameFont40;
        gameFontTiny = FontManager.gameFont20;
        //soundManager.loadSounds();
        //soundManager.startMenuMusic();
        initializeSettings();
        readSettings();

        // Refreshrate. Might have to improve that
        new Timer(16, e -> update()).start(); // ~60 FPS
    }

    private void initializeSettings() {
        Path tempFilePath = Paths.get(System.getProperty("java.io.tmpdir"), "settings.txt");

        try (BufferedReader reader = Files.newBufferedReader(tempFilePath)) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            // Use the settings if needed
            System.out.println("Loaded settings:");
            lines.forEach(System.out::println);

        } catch (IOException e) {
            // File not found or failed to read — create default settings
            String[] defaultSettings = { "music on", "language english", "debug off" };
            FileManager.writeLinesToTempFile(defaultSettings);

            System.out.println("Default settings written to temp file.");
        }
    }

    // Reads the settings from a txt file and overwrites the default values
    private void readSettings(){
        String[] line = FileManager.readLinesFromTempFile();
        if (line != null) {
            System.out.println(line[0]);
            if (line[0].equals("music off")) {
                soundManager.setGlobalVolume(SettingsManager.volume);
                soundManager.stopMusic();
                SettingsManager.musicOff = true;
            } else {
                soundManager.setGlobalVolume(SettingsManager.volume);
                soundManager.stopMusic();
                soundManager.loadMusic();
                soundManager.startMenuMusic();
                SettingsManager.musicOff = false;
            }
            if (line[1].equals("language german")) {
                SettingsManager.switchToGerman();
            } else {
                SettingsManager.switchToEnglish();
            }
            if (line[2].equals("debug on")) {
                SettingsManager.debugMode = true;
            } else {
                SettingsManager.debugMode = false;
            }
        }
    }


    private void swapToSpookyTheme(){
        textColor = Color.GREEN;
        hoverColor = Color.MAGENTA;
        backgroundImg = hauntedBackgroundImg;
        pawnImage = ghostPawnImage;
        rookImage = ghostRookImage;
        kingImage = ghostKingImage;
        showingDifficultySelection = true;

        soundManager.startEerieMenuMusic();
    }

    private void revertToNormalTheme(){
        textColor = Color.WHITE;
        hoverColor = Color.YELLOW;
        backgroundImg = normalBackgroundImg;
        pawnImage = whitePawnImage;
        rookImage = whiteRookImage;
        kingImage = whiteKingImage;
        if (gameMode == GameMode.SPOOKY){
            soundManager.startMenuMusic();
        }
        gameMode = GameMode.NORMAL;
    }

    private void update(){
        updateMenuState();
        repaint();
    }

    private void updateMenuState(){
        if (showingShop){
            updateShopMenu();
        } else if (showingSettings){
            updateSettingsMenu();
        } else if (showingHelp){
            updateHelpMenu();
        } else if (showingDifficultySelection){
            updateDifficultySelectionMenu();
        } else if (showingModeSelection){
            updateModeSelectionMenu();
        } else {
            updateMainMenu();
        }
    }

    private GameMode gameMode;
    private void updateModeSelectionMenu(){
        // Pressing a key increments or decrements index
        if(keyHandler.escapePressed){
            keyHandler.escapePressed = false;
            System.out.println(SettingsManager.goBackText);
            soundManager.playClip("buttonClick");
            showingModeSelection = false;
            buttonIndexY = 100000;
            while(buttonIndexY % 5 != 0){
                buttonIndexY++;
            }
        }
        if (keyHandler.goingUp){
            keyHandler.goingUp = false;
            buttonIndexY--;
            //soundManager.playClip("buttonHover");
            soundManager.playClip("buttonHover");
        } else if (keyHandler.goingDown){
            keyHandler.goingDown = false;
            buttonIndexY++;
            //soundManager.playClip("buttonHover");
            soundManager.playClip("buttonHover");
        }
        // Enter performs action on the button
        if (keyHandler.enterPressed || keyHandler.spacePressed){
            keyHandler.enterPressed = false;
            keyHandler.spacePressed = false;
            //soundManager.playClip("buttonClick");
            soundManager.playClip("buttonClick");

            if (buttonIndexY % 3 == 0) {
                System.out.println(SettingsManager.normalModeText);
                gameMode = GameMode.NORMAL;
                showingDifficultySelection = true;
                while(buttonIndexY % 4 != 0){
                    buttonIndexY++;
                }
            } else if (buttonIndexY % 3 == 1) {
                System.out.println(SettingsManager.spookyModeText);
                gameMode = GameMode.SPOOKY;
                swapToSpookyTheme();
                while(buttonIndexY % 4 != 0){
                    buttonIndexY++;
                }
            } else if (buttonIndexY % 3 == 2){
                System.out.println(SettingsManager.goBackText);
                showingModeSelection = false;
                buttonIndexY = 100000;
                while(buttonIndexY % 5 != 0){
                    buttonIndexY++;
                }
            }
        }

        // Hover effect
        resetButtons(); // Resets hover effect
        // Color buttons correctly
        if (buttonIndexY % 3 == 0) {
            hoveringNormalGamemode = true;
        } else if (buttonIndexY % 3 == 1) {
            hoveringSpookyGamemode = true;
        } else if (buttonIndexY % 3 == 2) {
            hoveringGoBack = true;
        }
    }

    private boolean startingGame = false;
    private void updateDifficultySelectionMenu(){
        if(keyHandler.escapePressed){
            keyHandler.escapePressed = false;
            System.out.println(SettingsManager.goBackText);
            soundManager.playClip("buttonClick");
            showingDifficultySelection = false;
            revertToNormalTheme();
            while (buttonIndexY % 3 != 0){
                buttonIndexY++;
            }
        }
        // Pressing a key increments or decrements index
        if (keyHandler.goingUp){
            keyHandler.goingUp = false;
            buttonIndexY--;
            //soundManager.playClip("buttonHover");
            soundManager.playClip("buttonHover");
        } else if (keyHandler.goingDown){
            keyHandler.goingDown = false;
            buttonIndexY++;
            //soundManager.playClip("buttonHover");
            soundManager.playClip("buttonHover");
        }
        // Enter performs action on the button
        if (keyHandler.enterPressed || keyHandler.spacePressed){
            keyHandler.enterPressed = false;
            keyHandler.spacePressed = false;
            //soundManager.playClip("buttonClick");
            soundManager.playClip("buttonClick");

            if (buttonIndexY % 4 == 0) {
                System.out.println(SettingsManager.easyText);
                startingGame = true;
                repaint();
                SwingUtilities.invokeLater(() -> {
                    soundManager.stopMusic();
                    Main.startMainGame(this, null, gameMode, gameMode.EASY);
                });
            } else if (buttonIndexY % 4 == 1) {
                System.out.println(SettingsManager.mediumText);
                startingGame = true;
                repaint();
                SwingUtilities.invokeLater(() -> {
                    soundManager.stopMusic();
                    Main.startMainGame(this, null, gameMode, gameMode.MEDIUM);
                });
            } else if (buttonIndexY % 4 == 2) {
                System.out.println(SettingsManager.hardText);
                startingGame = true;
                repaint();
                SwingUtilities.invokeLater(() -> {
                    soundManager.stopMusic();
                    Main.startMainGame(this, null, gameMode, GameMode.HARD);
                });
            } else if (buttonIndexY % 4 == 3) {
                System.out.println(SettingsManager.goBackText);
                showingDifficultySelection = false;
                revertToNormalTheme();
                while (buttonIndexY % 3 != 0){
                    buttonIndexY++;
                }
            }
        }

        // Hover effect
        resetButtons(); // Resets hover effect
        // Color buttons correctly
        if (buttonIndexY % 4 == 0) {
            hoveringEasyMode = true;
        } else if (buttonIndexY % 4 == 1) {
            hoveringMediumMode = true;
        } else if (buttonIndexY % 4 == 2) {
            hoveringHardMode = true;
        } else if (buttonIndexY % 4 == 3) {
            hoveringGoBack = true;
        }
    }

    private void updateShopMenu(){
        if (keyHandler.escapePressed){
            keyHandler.escapePressed = false;
            showingShop = false;
            //soundManager.playClip("buttonClick");
            soundManager.playClip("buttonClick");

        }
        else if (keyHandler.goingUp){
            keyHandler.goingUp = false;
            buttonIndexY--;
            //soundManager.playClip("buttonHover");
            soundManager.playClip("buttonHover");
        } else if (keyHandler.goingDown){
            keyHandler.goingDown = false;
            buttonIndexY++;
            //soundManager.playClip("buttonHover");
            soundManager.playClip("buttonHover");
        }

        // Enter performs action on the button
        if (keyHandler.enterPressed || keyHandler.spacePressed) {
            keyHandler.enterPressed = false;
            keyHandler.spacePressed = false;
            //soundManager.playClip("buttonClick");
            soundManager.playClip("buttonClick");

            if (buttonIndexY % 3 == 0) {
                System.out.println("Item01");
            } else if (buttonIndexY % 3 == 1) {
                System.out.println("Item02");

            } else if (buttonIndexY % 3 == 2) {
                System.out.println("Item03");
            }
        }
        // Hover effect
        resetButtons();
        // Color buttons correctly
        if (buttonIndexY % 3 == 0) {
            hoveringShopItemOne = true;
        }
        else if (buttonIndexY % 3 == 1) {
            hoveringShopItemTwo = true;
        }
        else if (buttonIndexY % 3 == 2) {
            hoveringShopItemThree = true;
        }
    }


    private void updateSettingsMenu(){
        if (keyHandler.escapePressed){
            keyHandler.escapePressed = false;
            showingSettings = false;
            //soundManager.playClip("buttonClick");
            soundManager.playClip("buttonClick");
        }
        else if (keyHandler.goingUp){
            keyHandler.goingUp = false;
            //soundManager.playClip("buttonHover");
            soundManager.playClip("buttonHover");
            buttonIndexY--;
        } else if (keyHandler.goingDown){
            keyHandler.goingDown = false;
            //soundManager.playClip("buttonHover");
            soundManager.playClip("buttonHover");
            buttonIndexY++;
        }

        if (hoveringVolumeButton) {
            if (keyHandler.goingRight){
                keyHandler.goingRight = false;
                volumeBars = Math.min(volumeBars + 1, 10);
            } else if (keyHandler.goingLeft){
                keyHandler.goingLeft = false;
                volumeBars = Math.max(volumeBars - 1, 0);
            }
        }


        // Enter performs action on the button
        if (keyHandler.enterPressed || keyHandler.spacePressed ) {
            keyHandler.enterPressed = false;
            keyHandler.spacePressed = false;
            //soundManager.playClip("buttonClick");
            soundManager.playClip("buttonHover");

            if (buttonIndexY % 4 == 0){
                System.out.println("VolumeSetting");
                System.out.println(5 * volumeBars);
                SettingsManager.volume = 5 * volumeBars;
            }
            else if (buttonIndexY % 4 == 1) {
                System.out.println("MusicSetting");
                SettingsManager.musicOff = !SettingsManager.musicOff;
            } else if (buttonIndexY % 4 == 2) {
                System.out.println("LanguageSetting");
                SettingsManager.languageGerman = !SettingsManager.languageGerman;

            } else if (buttonIndexY % 4 == 3) {
                System.out.println("DebugSetting");
                SettingsManager.debugMode = !SettingsManager.debugMode;
            }

            SettingsManager.writeSettings();
            readSettings();
            System.out.println("APPLY SETTINGS");


        }
        // Hover effect
        resetButtons();
        // Color buttons correctly
        if (buttonIndexY % 4 == 0){
            hoveringVolumeButton = true;
        }
        else if (buttonIndexY % 4 == 1) {
            hoveringMusicSettingButton = true;
        }
        else if (buttonIndexY % 4 == 2) {
            hoveringLanguageSettingButton = true;
        }
        else if (buttonIndexY % 4 == 3) {
            hoveringDebugSettingButton = true;
        }
    }

    private void updateHelpMenu(){
        if (keyHandler.escapePressed){
            keyHandler.escapePressed = false;
            showingHelp = false;
            soundManager.playClip("buttonClick");
        }
        else if (keyHandler.goingRight){
            keyHandler.goingRight = false;
            currentHelpPage++;
            soundManager.playClip("buttonClick");
        } else if (keyHandler.goingLeft){
            keyHandler.goingLeft = false;
            currentHelpPage--;
            soundManager.playClip("buttonClick");
        }
    }

    private void updateMainMenu(){
        // Pressing a key increments or decrements index
        if (keyHandler.goingUp){
            keyHandler.goingUp = false;
            buttonIndexY--;
            soundManager.playClip("buttonHover");
        } else if (keyHandler.goingDown){
            keyHandler.goingDown = false;
            buttonIndexY++;
            soundManager.playClip("buttonHover");
        }

        // Enter performs action on the button
        if (keyHandler.enterPressed || keyHandler.spacePressed){
            keyHandler.enterPressed = false;
            keyHandler.spacePressed = false;
            soundManager.playClip("buttonClick");

            if (buttonIndexY % 5 == 0) {
                System.out.println(SettingsManager.playText);
                // set the index to hover on the first element
                while (buttonIndexY % 3 != 0){
                    buttonIndexY++;
                }
                showingModeSelection = true;
            } else if (buttonIndexY % 5 == 1) {
                System.out.println(SettingsManager.shopText);
                showingShop = true;
            } else if (buttonIndexY % 5 == 2) {
                System.out.println(SettingsManager.settingsText);
                showingSettings = true;
            } else if (buttonIndexY % 5 == 3) {
                System.out.println(SettingsManager.helpText);
                showingHelp = true;
            } else if (buttonIndexY % 5 == 4) {
                System.out.println(SettingsManager.quitText);
                System.exit(0);
            }
        }

        // Hover effect
        resetButtons(); // Resets hover effect
        // Color buttons correctly
        if (buttonIndexY % 5 == 0) {
            hoveringPlay = true;
        } else if (buttonIndexY % 5 == 1) {
            hoveringShop = true;
        } else if (buttonIndexY % 5 == 2) {
            hoveringSettings = true;
        } else if (buttonIndexY % 5 == 3) {
            hoveringHelp = true;
        } else if (buttonIndexY % 5 == 4) {
            hoveringQuit = true;
        }
    }

    // Helper method that removes hovering effects
    private void resetButtons(){
        hoveringShop = false;
        hoveringPlay = false;
        hoveringQuit = false;
        hoveringSettings = false;
        hoveringHelp = false;

        hoveringNormalGamemode = false;
        hoveringSpookyGamemode = false;

        hoveringEasyMode = false;
        hoveringMediumMode = false;
        hoveringHardMode = false;
        hoveringGoBack = false;

        hoveringMusicSettingButton = false;
        hoveringVolumeButton = false;
        hoveringLanguageSettingButton = false;
        hoveringDebugSettingButton = false;

        hoveringShopItemOne = false;
        hoveringShopItemTwo = false;
        hoveringShopItemThree = false;

    }

    private void loadImages() {
        try {
            normalBackgroundImg = ImageIO.read(getClass().getResourceAsStream("/background/BackgroundMenu.png"));
            hauntedBackgroundImg = ImageIO.read(getClass().getResourceAsStream("/background/hauntedBackground.png"));
            backgroundImg = normalBackgroundImg;

            knightImage = ImageIO.read(getClass().getResourceAsStream("/background/knight.png"));
            zombieRookImage = ImageIO.read(getClass().getResourceAsStream("/background/zombieRook.png"));
            whitePawnImage = ImageIO.read(getClass().getResourceAsStream("/background/pawn.png"));
            whiteRookImage = ImageIO.read(getClass().getResourceAsStream("/background/rook.png"));
            whiteKingImage = ImageIO.read(getClass().getResourceAsStream("/background/king.png"));
            pawnImage = whitePawnImage;
            rookImage = whiteRookImage;
            kingImage = whiteKingImage;
            ghostPawnImage = ImageIO.read(getClass().getResourceAsStream("/background/ghostPawn.png"));
            ghostRookImage = ImageIO.read(getClass().getResourceAsStream("/background/ghostRook.png"));
            ghostKingImage = ImageIO.read(getClass().getResourceAsStream("/background/ghostKing.png"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not load images");
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        drawBackground(g2d);

        if (showingShop){
            drawShop(g2d);
        } else if (showingSettings){
            drawSettings(g2d);
        } else if (showingHelp) {
            drawHelp(g2d);
        } else if (showingDifficultySelection){
            drawDifficultySelection(g2d);
        } else if (showingModeSelection){
            drawModeSelection(g2d);
        } else {
            drawMainMenu(g2d);
        }
    }

    private Color translucentBlack = new Color(0, 0 ,0 , 220);


    private void drawModeSelection(Graphics2D g2d){
        g2d.setFont(gameFontMedium);
        // Title
        g2d.setColor(textColor);
        drawText(g2d,0,180, "Chess");
        drawText(g2d,0,260, "Defense");

        // Play button
        drawText(g2d,leftSpace + 8,500, SettingsManager.chooseGamemodeText);
        g2d.setFont(gameFontSmall);

        // Shop button
        if(hoveringNormalGamemode){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        g2d.drawImage(knightImage, leftSpace, 520, 70, 70, this);
        drawText(g2d,leftSpace + 102,580, SettingsManager.normalModeText);

        if(hoveringSpookyGamemode){
            g2d.setColor(Color.MAGENTA);
        } else {
            g2d.setColor(Color.WHITE);
        }
        g2d.drawImage(zombieRookImage, leftSpace, 600, 70, 70, this);
        drawText(g2d, leftSpace + 102, 660, SettingsManager.spookyModeText);

        // GoBack button
        if(hoveringGoBack){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        drawText(g2d,leftSpace,820, SettingsManager.goBackText);

    }

    private void drawDifficultySelection(Graphics2D g2d){
        g2d.setFont(gameFontMedium);
        // Title
        g2d.setColor(textColor);
        drawText(g2d,0,180, "Chess");
        drawText(g2d,0,260, "Defense");

        // Play button
        drawText(g2d,leftSpace + 8,500, SettingsManager.chooseDifficultyText);
        g2d.setFont(gameFontSmall);

        // Shop button
        if(hoveringEasyMode){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        g2d.drawImage(pawnImage, leftSpace, 520, 70, 70, this);
        drawText(g2d,leftSpace + 102,580, SettingsManager.easyText);

        if(hoveringMediumMode){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        g2d.drawImage(rookImage, leftSpace, 600, 70, 70, this);
        drawText(g2d, leftSpace + 102, 660, SettingsManager.mediumText);

        if(hoveringHardMode){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        g2d.drawImage(kingImage, leftSpace, 678, 70, 70, this);
        drawText(g2d,leftSpace + 102,740, SettingsManager.hardText);

        // GoBack button
        if(hoveringGoBack){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        drawText(g2d,leftSpace,820, SettingsManager.goBackText);

        if (startingGame){

            g2d.setColor(translucentBlack);
            g2d.fillRect(400, 400, Main.WIDTH-800, Main.HEIGHT-800);
            g2d.setFont(gameFont);
            g2d.setColor(textColor);
            drawText(g2d, 0,0, SettingsManager.loadingText);
        }
    }

    private int hauntedCounter = 0;
    private void drawBackground(Graphics2D g2d){
        g2d.drawImage(backgroundImg, 0, 0, Main.WIDTH, Main.HEIGHT, this);
        if (gameMode == GameMode.SPOOKY) {
            hauntedCounter++;

            // Reset every 16 seconds (960 frames at 60 FPS)
            if (hauntedCounter >= 960) {
                hauntedCounter = 0;
            }

            // Flash twice during the cycle
            // First flash: frames 160–170 (~2.6–2.8 seconds)
            // Second flash: frames 320–330 (~5.3–5.5 seconds)
            if ((hauntedCounter >= 320 && hauntedCounter < 340) ||
                    (hauntedCounter >= 520 && hauntedCounter < 570)) {
                g2d.drawImage(ghostKingImage, 838, 417, 242, 242, this);
            }
        }
    }

    private void drawShop(Graphics2D g2d) {
        // Background
        g2d.setColor(translucentBlack);
        g2d.fillRect(100,100, Main.WIDTH - 200, Main.HEIGHT - 200);


        g2d.setFont(gameFont);
        g2d.setColor(textColor);
        drawText(g2d,0,230, "Shop");

        // Wall upgrade Button
        g2d.setFont(gameFontSmall);
        if(hoveringShopItemOne){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        drawText(g2d,150,350, SettingsManager.wallUpgradeText);
        g2d.setColor(textColor);
        g2d.setFont(gameFontTiny);
        drawText(g2d,155,400, SettingsManager.wallUpgradeDescriptionText);

        // King upgrade Button
        g2d.setFont(gameFontSmall);
        if(hoveringShopItemTwo){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        drawText(g2d,150,500, SettingsManager.kingUpgradeText);
        g2d.setColor(textColor);
        g2d.setFont(gameFontTiny);
        drawText(g2d,155,550, SettingsManager.kingUpgradeDescriptionText);

        g2d.setFont(gameFontSmall);
        if (hoveringShopItemThree){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        drawText(g2d,150,650, SettingsManager.queenUpgradeText);
        g2d.setColor(textColor);
        g2d.setFont(gameFontTiny);
        drawText(g2d,155,700, SettingsManager.queenUpgradeDescriptionText);
        drawText(g2d, 0, 950, SettingsManager.pressEscapeText);
    }

    private int volumeBars = SettingsManager.volume/5;
    private void drawSettings(Graphics2D g2d){
        // Background
        g2d.setColor(translucentBlack);
        g2d.fillRect(100,100, Main.WIDTH - 200, Main.HEIGHT - 200);

        g2d.setFont(gameFont);
        g2d.setColor(textColor);
        drawText(g2d,0,230, SettingsManager.settingsText);

        g2d.setFont(gameFontSmall);
        drawText(g2d,0, 330, SettingsManager.volumeText);

        int y = 380;

        int layoutWidth = 90 * 10 - (90 - 80); // spacing * 10 - (spacing - buttonWidth) = 890
        int grayBgWidth = layoutWidth + 10 * 2; // padding on both sides = 920
        int grayBgX = (1920 - grayBgWidth) / 2; // center on 1920 screen
        int startX = grayBgX + 10; // padding

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(grayBgX, y - 8, grayBgWidth, 40 + 16); // height + top/bottom padding

        for (int i = 0; i < 10; i++) {
            if (i < volumeBars){
                if(hoveringVolumeButton){
                    g2d.setColor(Color.YELLOW);
                } else {
                    g2d.setColor(Color.WHITE);
                }
            } else {
                g2d.setColor(Color.BLACK);
            }
            g2d.fillRect(startX + i * 90, y, 80, 40); // button width = 80, spacing = 90
        }

        // Music button
        if(hoveringMusicSettingButton){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        if(SettingsManager.musicOff){
            drawText(g2d,0,540, SettingsManager.musicOffText);
        } else {
            drawText(g2d,0,540, SettingsManager.musicOnText);
        }

        // Language button
        if(hoveringLanguageSettingButton){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        if(SettingsManager.languageGerman){
            drawText(g2d,0,630, SettingsManager.languageGermanText);
        } else {
            drawText(g2d,0,630, SettingsManager.languageEnglishText);
        }
        // Debugmode button
        if(hoveringDebugSettingButton){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        if(SettingsManager.debugMode){
            drawText(g2d,0,720, SettingsManager.debugOnText);
        } else {
            drawText(g2d,0,720, SettingsManager.debugOffText);
        }
        g2d.setColor(textColor);
        g2d.setFont(gameFontTiny);
        drawText(g2d, 0, 950, SettingsManager.pressEscapeText);
    }

    private int currentHelpPage = 100000;
    private void drawHelp(Graphics2D g2d){
        // Background
        g2d.setColor(translucentBlack);
        g2d.fillRect(100,100, Main.WIDTH - 200, Main.HEIGHT - 200);

        g2d.setFont(gameFont);
        g2d.setColor(textColor);
        drawText(g2d,0,230, SettingsManager.helpText);

        if (currentHelpPage % 4 == 0){
            g2d.setFont(gameFontSmall);
            drawText(g2d, 155, 320, SettingsManager.welcomeText);
            g2d.setColor(textColor);
            g2d.setFont(gameFontTiny);
            drawText(g2d,155,375, SettingsManager.helpTextArray[0]);
            drawText(g2d,155,425, SettingsManager.helpTextArray[1]);
            g2d.setFont(gameFontSmall);
            drawText(g2d, 155, 540, SettingsManager.controlsText);
            g2d.setFont(gameFontTiny);
            drawText(g2d,155,595, SettingsManager.helpTextArray[2]);
            drawText(g2d,155,645, SettingsManager.helpTextArray[3]);
            drawText(g2d,155,695, SettingsManager.helpTextArray[4]);

            drawText(g2d,155,800, SettingsManager.helpTextArray[5]);
            drawText(g2d,155,845, SettingsManager.helpTextArray[6]);

            drawText(g2d, 1250, 950, SettingsManager.pressRightText);

        } else if (currentHelpPage % 4 == 1){
            g2d.setFont(gameFontSmall);
            drawText(g2d, 155, 320, SettingsManager.piecesText);

            drawText(g2d,155,405, SettingsManager.rookNameText);
            g2d.setFont(gameFontTiny);
            drawText(g2d,155,450, SettingsManager.helpTextArray[7]);
            drawText(g2d,155,500, SettingsManager.helpTextArray[8]);
            g2d.setFont(gameFontSmall);
            drawText(g2d, 155, 600, SettingsManager.knightNameText);
            g2d.setFont(gameFontTiny);
            drawText(g2d,155,645, SettingsManager.helpTextArray[9]);
            drawText(g2d, 155, 695, SettingsManager.helpTextArray[10]);

            g2d.setFont(gameFontSmall);
            drawText(g2d, 155, 790, SettingsManager.bishopNameText);
            g2d.setFont(gameFontTiny);
            drawText(g2d,155,835, SettingsManager.helpTextArray[11]);
            drawText(g2d, 155, 885, SettingsManager.helpTextArray[12]);

            drawText(g2d, 155, 950, SettingsManager.pressLeftText);
            drawText(g2d, 1250, 950, SettingsManager.pressRightText);
        } else if (currentHelpPage % 4 == 2){
            g2d.setFont(gameFontSmall);
            drawText(g2d, 155, 320, SettingsManager.queenNameText);
            g2d.setFont(gameFontTiny);
            drawText(g2d, 155, 365, SettingsManager.helpTextArray[13]);
            drawText(g2d, 155, 410, SettingsManager.helpTextArray[14]);

            g2d.setFont(gameFontSmall);
            drawText(g2d, 155, 500, SettingsManager.kingNameText);
            g2d.setFont(gameFontTiny);
            drawText(g2d, 155, 545, SettingsManager.helpTextArray[15]);
            drawText(g2d, 155, 590, SettingsManager.helpTextArray[16]);

            drawText(g2d, 155, 950, SettingsManager.pressLeftText);
            drawText(g2d, 1250, 950, SettingsManager.pressRightText);
        } else if (currentHelpPage % 4 == 3){
            g2d.setFont(gameFontSmall);
            g2d.setColor(Color.GREEN);
            drawText(g2d, 155, 320, SettingsManager.spookyModeDefinitionText);
            g2d.setFont(gameFontTiny);
            g2d.setColor(Color.WHITE);
            drawText(g2d, 155, 370, SettingsManager.helpTextArray[17]);
            drawText(g2d, 155, 415, SettingsManager.helpTextArray[18]);
            drawText(g2d, 155, 470, SettingsManager.helpTextArray[19]);
            drawText(g2d, 155, 515, SettingsManager.helpTextArray[20]);

            drawText(g2d, 0, 950, SettingsManager.pressEscapeText);
        }
        g2d.setFont(gameFontSmall);

    }

    int leftSpace = 50;
    private void drawMainMenu(Graphics2D g2d){
        g2d.setFont(gameFont);

        // Title
        g2d.setColor(textColor);
        drawText(g2d,0,180, "Chess");
        drawText(g2d,0,260, "Defense");

        // Play button
        if(hoveringPlay){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        drawText(g2d,leftSpace,500, SettingsManager.playText);

        g2d.setFont(gameFontSmall);
        // Shop button
        if(hoveringShop){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        drawText(g2d,leftSpace,580, SettingsManager.shopText);

        if(hoveringSettings){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        drawText(g2d, leftSpace, 660, SettingsManager.settingsText);

        if(hoveringHelp){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        drawText(g2d,leftSpace,740, SettingsManager.helpText);

        // Quit button
        if(hoveringQuit){
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(textColor);
        }
        drawText(g2d,leftSpace,820, SettingsManager.quitText);

    }

    // Helper method for drawing formatted text
    void drawText(Graphics2D g2d, int x, int y, String text){
        // Get font metrics for positioning
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        int force_x = (getWidth() - textWidth) / 2;
        int force_y = (getHeight() - textHeight) / 2 + fm.getAscent(); // ascent = baseline offset
        if (x == 0 && y == 0){
            g2d.drawString(text, force_x, force_y);
        } else if (x == 0){
            g2d.drawString(text, force_x, y);
        } else if (y == 0){
            g2d.drawString(text, x, force_y);
        }
        else {
            g2d.drawString(text, x, y);
        }
    }
}