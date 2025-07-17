package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TextureManager {

    GamePanel gamePanel;

    public BufferedImage tileImage;
    public BufferedImage mapImage;
    public BufferedImage bottomBarImage;
    public BufferedImage unavailablePieceImage;

    // Textures of the player pieces
    public BufferedImage rookImage;
    public BufferedImage rookHurtImage;
    public BufferedImage knightImage;
    public BufferedImage knightHurtImage;
    public BufferedImage bishopImage;
    public BufferedImage bishopHurtImage;
    public BufferedImage kingImage;
    public BufferedImage kingHurtImage;
    public BufferedImage queenImage;
    public BufferedImage queenHurtImage;
    public BufferedImage pawnImage;
    public BufferedImage pawnHurtImage;
    // Enemy textures
    public BufferedImage enemyRookImage;
    public BufferedImage enemyRookHurtImage;
    public BufferedImage enemyKnightImage;
    public BufferedImage enemyKnightHurtImage;
    public BufferedImage enemyBishopImage;
    public BufferedImage enemyBishopHurtImage;
    public BufferedImage enemyQueenImage;
    public BufferedImage enemyQueenHurtImage;
    public BufferedImage enemyKingImage;
    public BufferedImage enemyKingHurtImage;
    public BufferedImage enemyPawnImage;
    public BufferedImage enemyPawnHurtImage;

    // Projectile textures
    public BufferedImage arrowLeftImage;
    public BufferedImage arrowRightImage;
    public BufferedImage arrowUpImage;
    public BufferedImage arrowDownImage;
    public BufferedImage arrowDownLeftImage;
    public BufferedImage arrowDownRightImage;
    public BufferedImage arrowUpLeftImage;
    public BufferedImage arrowUpRightImage;
    public BufferedImage cannonBallImage;
    public BufferedImage cannonBallEnemyImage;
    public BufferedImage explosionImage;
    public BufferedImage queenParticleImageUp;
    public BufferedImage queenParticleImageDown;
    public BufferedImage queenParticleImageLeft;
    public BufferedImage queenParticleImageRight;
    public BufferedImage bishopParticleImageUpLeft;
    public BufferedImage bishopParticleImageUpRight;
    public BufferedImage bishopParticleImageDownLeft;
    public BufferedImage bishopParticleImageDownRight;
    public BufferedImage enemyBishopParticleImageDownLeft;
    public BufferedImage enemyBishopParticleImageUpLeft;
    public BufferedImage knightParticleImage;

    public BufferedImage stopwatchImage;

    BufferedImage imageAtlas;

    public TextureManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    final int TILE_SIZE = 16;
    void loadImages() {
        try
        {
            // No atlas
            bottomBarImage = ImageIO.read(getClass().getResourceAsStream("/background/BottomBar.png"));

            // Use an imageAtlas
            imageAtlas = ImageIO.read(getClass().getResourceAsStream("/imageAtlas.png"));

            rookImage = getTextureUpscaled(0,0, UPSCALED_SIZE);
            rookHurtImage = getTextureUpscaled(0,1, UPSCALED_SIZE);

            knightImage = getTextureUpscaled(1, 0, UPSCALED_SIZE);
            knightHurtImage = getTextureUpscaled(1, 1, UPSCALED_SIZE);

            bishopImage = getTextureUpscaled(2, 0, UPSCALED_SIZE);
            bishopHurtImage = getTextureUpscaled(2,1, UPSCALED_SIZE);

            queenImage = getTextureUpscaled(3, 0, UPSCALED_SIZE);
            queenHurtImage = getTextureUpscaled(3,1, UPSCALED_SIZE);

            kingImage = getTextureUpscaled(4,0, UPSCALED_SIZE);
            kingHurtImage = getTextureUpscaled(4,1, UPSCALED_SIZE);

            pawnImage = getTextureUpscaled(5,0, UPSCALED_SIZE);
            pawnHurtImage = getTextureUpscaled(5,1, UPSCALED_SIZE);

            enemyRookImage = getTextureUpscaled(0,2, UPSCALED_SIZE);
            enemyRookHurtImage = getTextureUpscaled(0,3, UPSCALED_SIZE);

            enemyKnightImage = getTextureUpscaled(1,2, UPSCALED_SIZE);
            enemyKnightHurtImage = getTextureUpscaled(1,3, UPSCALED_SIZE);

            enemyBishopImage = getTextureUpscaled(2,2, UPSCALED_SIZE);
            enemyBishopHurtImage = getTextureUpscaled(2,3, UPSCALED_SIZE);

            enemyQueenImage = getTextureUpscaled(3,2, UPSCALED_SIZE);
            enemyQueenHurtImage = getTextureUpscaled(3,3, UPSCALED_SIZE);

            enemyKingImage = getTextureUpscaled(4,2, UPSCALED_SIZE);
            enemyKingHurtImage = getTextureUpscaled(4,3, UPSCALED_SIZE);

            enemyPawnImage = getTextureUpscaled(5,2, UPSCALED_SIZE);
            enemyPawnHurtImage = getTextureUpscaled(5,3, UPSCALED_SIZE);

            arrowUpImage = getTextureUpscaled(6, 0, UPSCALED_SIZE);
            arrowDownImage = getTextureUpscaled(6, 1, UPSCALED_SIZE);
            arrowRightImage = getTextureUpscaled(6,2, UPSCALED_SIZE);
            arrowLeftImage = getTextureUpscaled(6,3, UPSCALED_SIZE);

            arrowDownRightImage = getTextureUpscaled(7,0, UPSCALED_SIZE);
            arrowDownLeftImage = getTextureUpscaled(7,1, UPSCALED_SIZE);
            arrowUpRightImage = getTextureUpscaled(7,2, UPSCALED_SIZE);
            arrowUpLeftImage = getTextureUpscaled(7, 3, UPSCALED_SIZE);

            cannonBallImage = getTextureUpscaled(0, 4, 64);
            cannonBallEnemyImage = getTextureUpscaled(0,5, 64);

            knightParticleImage = getTextureUpscaled(0,6, 128);

            explosionImage = getTextureUpscaled(0,7, 64);

            queenParticleImageUp = getTextureUpscaled(3,4, UPSCALED_SIZE);
            queenParticleImageDown = getTextureUpscaled(3,5, UPSCALED_SIZE);
            queenParticleImageRight = getTextureUpscaled(3, 6, UPSCALED_SIZE);
            queenParticleImageLeft = getTextureUpscaled(3, 7, UPSCALED_SIZE);

            bishopParticleImageUpRight = getTextureUpscaled(1,4, 96);
            bishopParticleImageUpLeft = getTextureUpscaled(1,5, 96);
            bishopParticleImageDownRight = getTextureUpscaled(1,6, 96);
            bishopParticleImageDownLeft = getTextureUpscaled(1,7, 96);

            enemyBishopParticleImageUpLeft = getTextureUpscaled(2,5, 96);
            enemyBishopParticleImageDownLeft = getTextureUpscaled(2,7, 96);

            stopwatchImage = getTextureUpscaled(5, 6,48);

            unavailablePieceImage = getTexture(7,4);
            tileImage = imageAtlas.getSubimage(5 * TILE_SIZE, 4 * TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 2);
            mapImage = generateBackgroundImage();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gamePanel, "Could not load images");
        }

    }

    private final int UPSCALED_SIZE = 128;

    private BufferedImage getTextureUpscaled(int col, int row, int desiredSize){
        BufferedImage texture = imageAtlas.getSubimage(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE ,TILE_SIZE);

        BufferedImage upscaledTexture = new BufferedImage(desiredSize, desiredSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = upscaledTexture.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        g2d.drawImage(texture, 0, 0, desiredSize, desiredSize, null);
        g2d.dispose();

        return upscaledTexture;
    }

    private BufferedImage getTexture(int col, int row){
        return imageAtlas.getSubimage(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }


    private BufferedImage generateBackgroundImage() {
        if (tileImage == null) {
            return null;
        }

        int sw = tileImage.getWidth() * 8;  // 32
        int sh = tileImage.getHeight() * 8; // 32

        BufferedImage background = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = background.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        for (int y = 0; y < Main.HEIGHT; y += sh) {
            for (int x = 0; x < Main.WIDTH; x += sw) {
                g2d.drawImage(tileImage, x, y, sw, sh, null);
            }
        }

        g2d.dispose();
        return background;
    }
}
