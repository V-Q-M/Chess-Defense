package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TextureManager {

    GamePanel gamePanel;

    public BufferedImage earthTileImage;
    public BufferedImage snowTileImage;
    public BufferedImage grassTileImage;
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
    public BufferedImage greyArrowLeftImage;
    public BufferedImage greyArrowRightImage;
    public BufferedImage greyArrowUpImage;
    public BufferedImage greyArrowDownImage;
    public BufferedImage greyArrowDownLeftImage;
    public BufferedImage greyArrowDownRightImage;
    public BufferedImage greyArrowUpLeftImage;
    public BufferedImage greyArrowUpRightImage;



    public BufferedImage cannonBallImage;
    public BufferedImage cannonBallEarthEnemyImage;
    public BufferedImage cannonBallSnowEnemyImage;
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
    public BufferedImage knightEarthParticleImage;
    public BufferedImage knightSnowParticleImage;

    public BufferedImage rockImage;
    public BufferedImage bushImage;
    public BufferedImage iceImage;

    public BufferedImage stopwatchImage;

    BufferedImage imageAtlas;

    public TextureManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    final int TILE_SIZE = 16;
    final int UPSCALED_SIZE = 128;
    void loadImages(Map map) {
        try
        {
            // No atlas
            bottomBarImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/BottomBar.png")));

            // Use an imageAtlas
            imageAtlas = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/imageAtlas.png")));

            rookImage = getTextureUpscaled(0, 0, TILE_SIZE, UPSCALED_SIZE);
            rookHurtImage = getTextureUpscaled(0, 1, TILE_SIZE, UPSCALED_SIZE);

            knightImage = getTextureUpscaled(1, 0, TILE_SIZE, UPSCALED_SIZE);
            knightHurtImage = getTextureUpscaled(1, 1, TILE_SIZE, UPSCALED_SIZE);

            bishopImage = getTextureUpscaled(2, 0, TILE_SIZE, UPSCALED_SIZE);
            bishopHurtImage = getTextureUpscaled(2, 1, TILE_SIZE, UPSCALED_SIZE);

            queenImage = getTextureUpscaled(3, 0, TILE_SIZE, UPSCALED_SIZE);
            queenHurtImage = getTextureUpscaled(3, 1, TILE_SIZE, UPSCALED_SIZE);

            kingImage = getTextureUpscaled(4, 0, TILE_SIZE, UPSCALED_SIZE);
            kingHurtImage = getTextureUpscaled(4, 1, TILE_SIZE, UPSCALED_SIZE);

            pawnImage = getTextureUpscaled(5, 0, TILE_SIZE, UPSCALED_SIZE);
            pawnHurtImage = getTextureUpscaled(5, 1, TILE_SIZE, UPSCALED_SIZE);

            enemyRookImage = getTextureUpscaled(0, 2, TILE_SIZE, UPSCALED_SIZE);
            enemyRookHurtImage = getTextureUpscaled(0, 3, TILE_SIZE, UPSCALED_SIZE);

            enemyKnightImage = getTextureUpscaled(1, 2, TILE_SIZE, UPSCALED_SIZE);
            enemyKnightHurtImage = getTextureUpscaled(1, 3, TILE_SIZE, UPSCALED_SIZE);

            enemyBishopImage = getTextureUpscaled(2, 2, TILE_SIZE, UPSCALED_SIZE);
            enemyBishopHurtImage = getTextureUpscaled(2, 3, TILE_SIZE, UPSCALED_SIZE);

            enemyQueenImage = getTextureUpscaled(3, 2, TILE_SIZE, UPSCALED_SIZE);
            enemyQueenHurtImage = getTextureUpscaled(3, 3, TILE_SIZE, UPSCALED_SIZE);

            enemyKingImage = getTextureUpscaled(4, 2, TILE_SIZE, UPSCALED_SIZE);
            enemyKingHurtImage = getTextureUpscaled(4, 3, TILE_SIZE, UPSCALED_SIZE);

            enemyPawnImage = getTextureUpscaled(5, 2, TILE_SIZE, UPSCALED_SIZE);
            enemyPawnHurtImage = getTextureUpscaled(5, 3, TILE_SIZE, UPSCALED_SIZE);

            arrowUpImage = getTextureUpscaled(6, 0, TILE_SIZE, UPSCALED_SIZE);
            arrowDownImage = getTextureUpscaled(6, 1, TILE_SIZE, UPSCALED_SIZE);
            arrowRightImage = getTextureUpscaled(6, 2, TILE_SIZE, UPSCALED_SIZE);
            arrowLeftImage = getTextureUpscaled(6, 3, TILE_SIZE, UPSCALED_SIZE);

            arrowDownRightImage = getTextureUpscaled(7, 0, TILE_SIZE, UPSCALED_SIZE);
            arrowDownLeftImage = getTextureUpscaled(7, 1, TILE_SIZE, UPSCALED_SIZE);
            arrowUpRightImage = getTextureUpscaled(7, 2, TILE_SIZE, UPSCALED_SIZE);
            arrowUpLeftImage = getTextureUpscaled(7, 3, TILE_SIZE, UPSCALED_SIZE);

            greyArrowUpImage = getTextureUpscaled(6, 4, TILE_SIZE, UPSCALED_SIZE);
            greyArrowDownImage = getTextureUpscaled(6, 5, TILE_SIZE, UPSCALED_SIZE);
            greyArrowRightImage = getTextureUpscaled(6,6, TILE_SIZE, UPSCALED_SIZE);
            greyArrowLeftImage = getTextureUpscaled(6,7, TILE_SIZE, UPSCALED_SIZE);

            greyArrowDownRightImage = getTextureUpscaled(7,4, TILE_SIZE, UPSCALED_SIZE);
            greyArrowDownLeftImage = getTextureUpscaled(7,5, TILE_SIZE, UPSCALED_SIZE);
            greyArrowUpRightImage = getTextureUpscaled(7,6, TILE_SIZE, UPSCALED_SIZE);
            greyArrowUpLeftImage = getTextureUpscaled(7, 7, TILE_SIZE, UPSCALED_SIZE);


            cannonBallImage = getTextureUpscaled(0, 8, TILE_SIZE, 64);
            cannonBallEarthEnemyImage = getTextureUpscaled(0,9, TILE_SIZE, 64);
            cannonBallSnowEnemyImage = getTextureUpscaled(0,10, TILE_SIZE, 64);

            knightEarthParticleImage = getTextureUpscaled(5,8, TILE_SIZE, 128);
            knightSnowParticleImage = getTextureUpscaled(5,9, TILE_SIZE, 128);

            explosionImage = getTextureUpscaled(0,11, TILE_SIZE, 64);

            queenParticleImageUp = getTextureUpscaled(3,8, TILE_SIZE, UPSCALED_SIZE);
            queenParticleImageDown = getTextureUpscaled(3,9, TILE_SIZE, UPSCALED_SIZE);
            queenParticleImageRight = getTextureUpscaled(3, 10, TILE_SIZE, UPSCALED_SIZE);
            queenParticleImageLeft = getTextureUpscaled(3, 11, TILE_SIZE, UPSCALED_SIZE);

            bishopParticleImageUpRight = getTextureUpscaled(1,8, TILE_SIZE, 96);
            bishopParticleImageUpLeft = getTextureUpscaled(1,9, TILE_SIZE, 96);
            bishopParticleImageDownRight = getTextureUpscaled(1,10, TILE_SIZE, 96);
            bishopParticleImageDownLeft = getTextureUpscaled(1,11, TILE_SIZE, 96);

            enemyBishopParticleImageUpLeft = getTextureUpscaled(2,9, TILE_SIZE, 96);
            enemyBishopParticleImageDownLeft = getTextureUpscaled(2,11, TILE_SIZE, 96);

            stopwatchImage = getTextureUpscaled(6, 8, TILE_SIZE ,48);

            rockImage = getTextureUpscaled(6,9, TILE_SIZE, UPSCALED_SIZE);
            iceImage = getTextureUpscaled(6, 10, TILE_SIZE * 2, UPSCALED_SIZE * 2);

            unavailablePieceImage = getTexture(7,8);
            earthTileImage = imageAtlas.getSubimage(8 * TILE_SIZE, 0 * TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 2);
            snowTileImage = imageAtlas.getSubimage(8 * TILE_SIZE, 2 * TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 2);
            mapImage = generateBackgroundImage(map);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gamePanel, "Could not load images");
        }

    }

    private BufferedImage getTextureUpscaled(int col, int row, int originalSize, int desiredSize){
        BufferedImage texture = imageAtlas.getSubimage(col * TILE_SIZE, row * TILE_SIZE, originalSize ,originalSize);

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


    private BufferedImage generateBackgroundImage(Map mapType) {
        BufferedImage tileImage;

        switch (mapType){
            case Map.SNOW -> tileImage = snowTileImage;
            case Map.WOODS -> tileImage = grassTileImage;
            default -> tileImage = earthTileImage;
        }


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
