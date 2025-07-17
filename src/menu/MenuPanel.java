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
    SoundManager soundManager = new SoundManager(this);
    BufferedImage backgroundImg;
    BufferedImage pawnImage;
    BufferedImage rookImage;
    BufferedImage kingImage;

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

    private boolean hoveringEasyMode = false;
    private boolean hoveringMediumMode = false;
    private boolean hoveringHardMode = false;
    private boolean hoveringGoBack = false;

    private boolean hoveringMusicSettingButton = false;
    private boolean hoveringLanguageSettingButton = false;
    private boolean hoveringDebugSettingButton = false;

    private boolean hoveringShopItemOne = false;
    private boolean hoveringShopItemTwo = false;
    private boolean hoveringShopItemThree = false;

    // If one of these is on, it shows that side menu
    private boolean showingShop = false;
    private boolean showingHelp = false;
    private boolean showingSettings = false;
    private boolean showingDifficultySelection;

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
        soundManager.loadSounds();
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
                soundManager.stopMusic();
                SettingsManager.musicOff = true;
            } else {
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
        } else {
            updateMainMenu();
        }
    }

    private void updateDifficultySelectionMenu(){
        // Pressing a key increments or decrements index
        if (keyHandler.goingUp){
            keyHandler.goingUp = false;
            buttonIndexY--;
            soundManager.playClip(soundManager.buttonHoverClip);
        } else if (keyHandler.goingDown){
            keyHandler.goingDown = false;
            buttonIndexY++;
            soundManager.playClip(soundManager.buttonHoverClip);
        }
        // Enter performs action on the button
        if (keyHandler.enterPressed || keyHandler.spacePressed){
            keyHandler.enterPressed = false;
            keyHandler.spacePressed = false;
            soundManager.playClip(soundManager.buttonClickClip);

            if (buttonIndexY % 4 == 0) {
                System.out.println(SettingsManager.easyText);
                soundManager.stopMusic();
                Main.startMainGame(this, null, "easy");
            } else if (buttonIndexY % 4 == 1) {
                System.out.println(SettingsManager.mediumText);
                Main.startMainGame(this, null, "medium");
            } else if (buttonIndexY % 4 == 2) {
                System.out.println(SettingsManager.hardText);
                Main.startMainGame(this, null, "hard");
            } else if (buttonIndexY % 4 == 3) {
                System.out.println(SettingsManager.goBackText);
                showingDifficultySelection = false;
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
            soundManager.playClip(soundManager.buttonClickClip);
        }
        else if (keyHandler.goingUp){
            keyHandler.goingUp = false;
            buttonIndexY--;
            soundManager.playClip(soundManager.buttonHoverClip);
        } else if (keyHandler.goingDown){
            keyHandler.goingDown = false;
            buttonIndexY++;
            soundManager.playClip(soundManager.buttonHoverClip);
        }

        // Enter performs action on the button
        if (keyHandler.enterPressed || keyHandler.spacePressed) {
            keyHandler.enterPressed = false;
            keyHandler.spacePressed = false;
            soundManager.playClip(soundManager.buttonClickClip);

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
            soundManager.playClip(soundManager.buttonClickClip);
        }
        else if (keyHandler.goingUp){
            keyHandler.goingUp = false;
            soundManager.playClip(soundManager.buttonHoverClip);
            buttonIndexY--;
        } else if (keyHandler.goingDown){
            keyHandler.goingDown = false;
            soundManager.playClip(soundManager.buttonHoverClip);
            buttonIndexY++;
        }

        // Enter performs action on the button
        if (keyHandler.enterPressed || keyHandler.spacePressed) {
            keyHandler.enterPressed = false;
            keyHandler.spacePressed = false;
            soundManager.playClip(soundManager.buttonClickClip);

            if (buttonIndexY % 3 == 0) {
                System.out.println("MusicSetting");
                SettingsManager.musicOff = !SettingsManager.musicOff;
            } else if (buttonIndexY % 3 == 1) {
                System.out.println("LanguageSetting");
                SettingsManager.languageGerman = !SettingsManager.languageGerman;

            } else if (buttonIndexY % 3 == 2) {
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
        if (buttonIndexY % 3 == 0) {
            hoveringMusicSettingButton = true;
        }
        else if (buttonIndexY % 3 == 1) {
            hoveringLanguageSettingButton = true;
        }
        else if (buttonIndexY % 3 == 2) {
            hoveringDebugSettingButton = true;
        }
    }

    private void updateHelpMenu(){
        if (keyHandler.escapePressed){
            keyHandler.escapePressed = false;
            showingHelp = false;
            soundManager.playClip(soundManager.buttonClickClip);
        }
        else if (keyHandler.goingRight){
            keyHandler.goingRight = false;
            currentHelpPage++;
            soundManager.playClip(soundManager.buttonClickClip);
        } else if (keyHandler.goingLeft){
            keyHandler.goingLeft = false;
            currentHelpPage--;
            soundManager.playClip(soundManager.buttonClickClip);
        }
    }

    private void updateMainMenu(){
        // Pressing a key increments or decrements index
        if (keyHandler.goingUp){
            keyHandler.goingUp = false;
            buttonIndexY--;
            soundManager.playClip(soundManager.buttonHoverClip);
        } else if (keyHandler.goingDown){
            keyHandler.goingDown = false;
            buttonIndexY++;
            soundManager.playClip(soundManager.buttonHoverClip);
        }

        // Enter performs action on the button
        if (keyHandler.enterPressed || keyHandler.spacePressed){
            keyHandler.enterPressed = false;
            keyHandler.spacePressed = false;
            soundManager.playClip(soundManager.buttonClickClip);

            if (buttonIndexY % 5 == 0) {
                System.out.println(SettingsManager.playText);
                showingDifficultySelection = true;
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


        hoveringEasyMode = false;
        hoveringMediumMode = false;
        hoveringHardMode = false;
        hoveringGoBack = false;

        hoveringMusicSettingButton = false;
        hoveringLanguageSettingButton = false;
        hoveringDebugSettingButton = false;

        hoveringShopItemOne = false;
        hoveringShopItemTwo = false;
        hoveringShopItemThree = false;

    }

    private void loadImages() {
        try {
            backgroundImg = ImageIO.read(getClass().getResourceAsStream("/background/BackgroundMenu.png"));
            pawnImage = ImageIO.read(getClass().getResourceAsStream("/background/pawn.png"));
            rookImage = ImageIO.read(getClass().getResourceAsStream("/background/rook.png"));
            kingImage = ImageIO.read(getClass().getResourceAsStream("/background/king.png"));
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
        } else {
            drawMainMenu(g2d);
        }
    }

    private Color translucentBlack = new Color(0, 0 ,0 , 220);

    private void drawDifficultySelection(Graphics2D g2d){
        g2d.setFont(gameFontMedium);

        // Title
        g2d.setColor(Color.WHITE);
        drawText(g2d,0,180, "Chess");
        drawText(g2d,0,260, "Defense");

        // Play button
        drawText(g2d,leftSpace + 8,500, SettingsManager.chooseDifficultyText);
        g2d.setFont(gameFontSmall);

        // Shop button
        if(hoveringEasyMode){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        g2d.drawImage(pawnImage, leftSpace, 520, 70, 70, this);
        drawText(g2d,leftSpace + 102,580, SettingsManager.easyText);

        if(hoveringMediumMode){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        g2d.drawImage(rookImage, leftSpace, 600, 70, 70, this);
        drawText(g2d, leftSpace + 102, 660, SettingsManager.mediumText);

        if(hoveringHardMode){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        g2d.drawImage(kingImage, leftSpace, 678, 70, 70, this);
        drawText(g2d,leftSpace + 102,740, SettingsManager.hardText);

        // GoBack button
        if(hoveringGoBack){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        drawText(g2d,leftSpace,820, SettingsManager.goBackText);
    }

    private void drawBackground(Graphics2D g2d){
        g2d.drawImage(backgroundImg, 0, 0, Main.WIDTH, Main.HEIGHT, this);
    }

    private void drawShop(Graphics2D g2d) {
        // Background
        g2d.setColor(translucentBlack);
        g2d.fillRect(100,100, Main.WIDTH - 200, Main.HEIGHT - 200);


        g2d.setFont(gameFont);
        g2d.setColor(Color.WHITE);
        drawText(g2d,0,230, "Shop");

        // Wall upgrade Button
        g2d.setFont(gameFontSmall);
        if(hoveringShopItemOne){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        drawText(g2d,150,350, SettingsManager.wallUpgradeText);
        g2d.setColor(Color.WHITE);
        g2d.setFont(gameFontTiny);
        drawText(g2d,155,400, SettingsManager.wallUpgradeDescriptionText);

        // King upgrade Button
        g2d.setFont(gameFontSmall);
        if(hoveringShopItemTwo){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        drawText(g2d,150,500, SettingsManager.kingUpgradeText);
        g2d.setColor(Color.WHITE);
        g2d.setFont(gameFontTiny);
        drawText(g2d,155,550, SettingsManager.kingUpgradeDescriptionText);

        g2d.setFont(gameFontSmall);
        if (hoveringShopItemThree){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        drawText(g2d,150,650, SettingsManager.queenUpgradeText);
        g2d.setColor(Color.WHITE);
        g2d.setFont(gameFontTiny);
        drawText(g2d,155,700, SettingsManager.queenUpgradeDescriptionText);
        drawText(g2d, 0, 950, SettingsManager.pressEscapeText);
    }

    private void drawSettings(Graphics2D g2d){
        // Background
        g2d.setColor(translucentBlack);
        g2d.fillRect(100,100, Main.WIDTH - 200, Main.HEIGHT - 200);


        g2d.setFont(gameFont);
        g2d.setColor(Color.WHITE);
        drawText(g2d,0,230, SettingsManager.settingsText);

        g2d.setFont(gameFontSmall);
        // Music button
        if(hoveringMusicSettingButton){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        if(SettingsManager.musicOff){
            drawText(g2d,0,400, SettingsManager.musicOffText);
        } else {
            drawText(g2d,0,400, SettingsManager.musicOnText);
        }

        // Language button
        if(hoveringLanguageSettingButton){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        if(SettingsManager.languageGerman){
            drawText(g2d,0,500, SettingsManager.languageGermanText);
        } else {
            drawText(g2d,0,500, SettingsManager.languageEnglishText);
        }
        // Debugmode button
        if(hoveringDebugSettingButton){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        if(SettingsManager.debugMode){
            drawText(g2d,0,600, SettingsManager.debugOnText);
        } else {
            drawText(g2d,0,600, SettingsManager.debugOffText);
        }
        g2d.setColor(Color.WHITE);
        g2d.setFont(gameFontTiny);
        drawText(g2d, 0, 950, SettingsManager.pressEscapeText);
    }

    private int currentHelpPage = 0;
    private void drawHelp(Graphics2D g2d){
        // Background
        g2d.setColor(translucentBlack);
        g2d.fillRect(100,100, Main.WIDTH - 200, Main.HEIGHT - 200);

        g2d.setFont(gameFont);
        g2d.setColor(Color.WHITE);
        drawText(g2d,0,230, SettingsManager.helpText);

        if (currentHelpPage % 3 == 0){
            g2d.setFont(gameFontSmall);
            drawText(g2d, 155, 320, SettingsManager.welcomeText);
            g2d.setColor(Color.WHITE);
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

            drawText(g2d, 155, 950, SettingsManager.pressLeftText);
            drawText(g2d, 1250, 950, SettingsManager.pressRightText);

        } else if (currentHelpPage % 3 == 1){
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
        } else {
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

            drawText(g2d, 0, 950, SettingsManager.pressEscapeText);
        }
        g2d.setFont(gameFontSmall);

    }

    int leftSpace = 50;
    private void drawMainMenu(Graphics2D g2d){
        g2d.setFont(gameFont);

        // Title
        g2d.setColor(Color.WHITE);
        drawText(g2d,0,180, "Chess");
        drawText(g2d,0,260, "Defense");

        // Play button
        if(hoveringPlay){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        drawText(g2d,leftSpace,500, SettingsManager.playText);

        g2d.setFont(gameFontSmall);
        // Shop button
        if(hoveringShop){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        drawText(g2d,leftSpace,580, SettingsManager.shopText);

        if(hoveringSettings){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        drawText(g2d, leftSpace, 660, SettingsManager.settingsText);

        if(hoveringHelp){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
        }
        drawText(g2d,leftSpace,740, SettingsManager.helpText);

        // Quit button
        if(hoveringQuit){
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.WHITE);
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