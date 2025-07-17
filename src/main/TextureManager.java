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
    public BufferedImage enemyBishopImage;
    public BufferedImage enemyBishopHurtImage;
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

    public TextureManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    void loadImages() {
        try {
            tileImage = ImageIO.read(getClass().getResourceAsStream("/background/earth.png"));
            bottomBarImage = ImageIO.read(getClass().getResourceAsStream("/background/BottomBar.png"));
            unavailablePieceImage = ImageIO.read(getClass().getResourceAsStream("/background/unavailable.png"));

            rookImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/white/rook.png"));
            rookHurtImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/white/rook_hurt.png"));
            knightImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/white/knight.png"));
            knightHurtImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/white/knight_hurt.png"));
            bishopImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/white/bishop.png"));
            bishopHurtImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/white/bishop_hurt.png"));
            kingImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/white/king.png"));
            kingHurtImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/white/king_hurt.png"));
            queenImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/white/queen.png"));
            queenHurtImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/white/queen_hurt.png"));
            pawnImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/white/pawn.png"));
            pawnHurtImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/white/pawn_hurt.png"));

            enemyRookImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/black/rook.png"));
            enemyRookHurtImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/black/rook_hurt.png"));
            enemyBishopImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/black/bishop.png"));
            enemyBishopHurtImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/black/bishop_hurt.png"));
            enemyKingImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/black/king.png"));
            enemyKingHurtImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/black/king_hurt.png"));
            enemyPawnImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/black/pawn.png"));
            enemyPawnHurtImage = ImageIO.read(getClass().getResourceAsStream("/chess-pieces/black/pawn_hurt.png"));

            arrowLeftImage = ImageIO.read(getClass().getResourceAsStream("/particles/arrowMarkers/arrowLeft.png"));
            arrowRightImage = ImageIO.read(getClass().getResourceAsStream("/particles/arrowMarkers/arrowRight.png"));
            arrowUpImage = ImageIO.read(getClass().getResourceAsStream("/particles/arrowMarkers/arrowUp.png"));
            arrowDownImage = ImageIO.read(getClass().getResourceAsStream("/particles/arrowMarkers/arrowDown.png"));
            arrowUpLeftImage = ImageIO.read(getClass().getResourceAsStream("/particles/arrowMarkers/arrowUpLeft.png"));
            arrowUpRightImage = ImageIO.read(getClass().getResourceAsStream("/particles/arrowMarkers/arrowUpRight.png"));
            arrowDownLeftImage = ImageIO.read(getClass().getResourceAsStream("/particles/arrowMarkers/arrowDownLeft.png"));
            arrowDownRightImage = ImageIO.read(getClass().getResourceAsStream("/particles/arrowMarkers/arrowDownRight.png"));
            cannonBallImage = ImageIO.read(getClass().getResourceAsStream("/particles/cannonBalls/cannonball.png"));
            cannonBallEnemyImage = ImageIO.read(getClass().getResourceAsStream("/particles/cannonBalls/cannonballEnemy.png"));
            explosionImage = ImageIO.read(getClass().getResourceAsStream("/particles/explosions/explosion.png"));
            queenParticleImageUp = ImageIO.read(getClass().getResourceAsStream("/particles/queenParticles/queenParticlesUp.png"));
            queenParticleImageDown = ImageIO.read(getClass().getResourceAsStream("/particles/queenParticles/queenParticlesDown.png"));
            queenParticleImageLeft = ImageIO.read(getClass().getResourceAsStream("/particles/queenParticles/queenParticlesLeft.png"));
            queenParticleImageRight = ImageIO.read(getClass().getResourceAsStream("/particles/queenParticles/queenParticlesRight.png"));
            bishopParticleImageUpLeft = ImageIO.read(getClass().getResourceAsStream("/particles/bishopLance/bishopLanceUpLeft.png"));
            bishopParticleImageUpRight = ImageIO.read(getClass().getResourceAsStream("/particles/bishopLance/bishopLanceUpRight.png"));
            bishopParticleImageDownLeft = ImageIO.read(getClass().getResourceAsStream("/particles/bishopLance/bishopLanceDownLeft.png"));
            bishopParticleImageDownRight = ImageIO.read(getClass().getResourceAsStream("/particles/bishopLance/bishopLanceDownRight.png"));
            enemyBishopParticleImageDownLeft = ImageIO.read(getClass().getResourceAsStream("/particles/enemyBishopLance/enemyBishopLanceDownLeft.png"));
            enemyBishopParticleImageUpLeft = ImageIO.read(getClass().getResourceAsStream("/particles/enemyBishopLance/enemyBishopLanceUpRight.png"));
            knightParticleImage = ImageIO.read(getClass().getResourceAsStream("/particles/knightParticles/knightParticles.png"));

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gamePanel, "Could not load images");
        }
    }
}
