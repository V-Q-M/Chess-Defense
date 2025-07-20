package projectiles;

import main.Direction;
import main.GamePanel;
import main.TextureManager;

import java.awt.image.BufferedImage;

public class QueenSlice extends Projectile {

  // Specialized constructor
  public QueenSlice(GamePanel gamePanel, TextureManager textureManager, int x, int y, int size, int speed, int decay, int damage, BufferedImage skin, Direction direction) {
    super(gamePanel, textureManager, x, y, size, size);
    this.direction = direction;
    this.speed = speed;
    this.health = decay;
    this.damage = damage;
    this.skin = skin;
    this.destroyable = true;
  }

  @Override
  public void move() {
    switch (direction) {
      case UP_LEFT -> {
        y -= 15;
        x -= 15;
      }
      case UP_RIGHT -> {
        y -= 15;
        x += 15;
      }
      case DOWN_LEFT -> {
        y += 15;
        x -= 15;
      }
      case DOWN_RIGHT -> {
        y += 15;
        x += 15;
      }
      case UP -> {
        y -= speed;
      }
      case DOWN -> {
        y += speed;
      }
      case LEFT -> {
        x -= speed;
      }
      default -> {
        x += speed;
      }
    }
  }
}
