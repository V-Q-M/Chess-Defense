package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TextureManager {

    GamePanel gamePanel;

    public BufferedImage tileImage;
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

            rookImage = getTexture(0,0);
            rookHurtImage = getTexture(0,1);

            knightImage = getTexture(1, 0);
            knightHurtImage = getTexture(1, 1);

            bishopImage = getTexture(2, 0);
            bishopHurtImage = getTexture(2,1);

            queenImage = getTexture(3, 0);
            queenHurtImage = getTexture(3,1);

            kingImage = getTexture(4,0);
            kingHurtImage = getTexture(4,1);

            pawnImage = getTexture(5,0);
            pawnHurtImage = getTexture(5,1);

            enemyRookImage = getTexture(0,2);
            enemyRookHurtImage = getTexture(0,3);

            enemyKnightImage = getTexture(1,2);
            enemyKnightHurtImage = getTexture(1,3);

            enemyBishopImage = getTexture(2,2);
            enemyBishopHurtImage = getTexture(2,3);

            enemyQueenImage = getTexture(3,2);
            enemyQueenHurtImage = getTexture(3,3);

            enemyKingImage = getTexture(4,2);
            enemyKingHurtImage = getTexture(4,3);

            enemyPawnImage = getTexture(5,2);
            enemyPawnHurtImage = getTexture(5,3);

            arrowUpImage = getTexture(6, 0);
            arrowDownImage = getTexture(6, 1);
            arrowRightImage = getTexture(6,2);
            arrowLeftImage = getTexture(6,3);

            arrowDownRightImage = getTexture(7,0);
            arrowDownLeftImage = getTexture(7,1);
            arrowUpRightImage = getTexture(7,2);
            arrowUpLeftImage = getTexture(7, 3);

            cannonBallImage = getTexture(0, 4);
            cannonBallEnemyImage = getTexture(0,5);

            knightParticleImage = getTexture(0,6);

            explosionImage = getTexture(0,7);

            queenParticleImageUp = getTexture(3,4);
            queenParticleImageDown = getTexture(3,5);
            queenParticleImageRight = getTexture(3, 6);
            queenParticleImageLeft = getTexture(3, 7);

            bishopParticleImageUpRight = getTexture(1,4);
            bishopParticleImageUpLeft = getTexture(1,5);
            bishopParticleImageDownRight = getTexture(1,6);
            bishopParticleImageDownLeft = getTexture(1,7);

            enemyBishopParticleImageUpLeft = getTexture(2,5);
            enemyBishopParticleImageDownLeft = getTexture(2,7);

            unavailablePieceImage = getTexture(7,4);
            tileImage = imageAtlas.getSubimage(5 * TILE_SIZE, 4 * TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 2);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gamePanel, "Could not load images");
        }

    }
    private BufferedImage getTexture(int col, int row){
        return imageAtlas.getSubimage(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE ,TILE_SIZE);
    }
}
