package entities;

import main.Direction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class QueenSlice extends Projectile {

  // Specialized constructor
  public QueenSlice(GamePanel gamePanel, int x, int y, int size, int speed, int decay, int damage, BufferedImage skin, Direction direction) {
    this.gamePanel = gamePanel;
    this.x = x;
    this.y = y;
    this.height = size;
    this.width = size;
    this.direction = direction;
    this.speed = speed;
    this.health = decay;
    this.damage = damage;
    this.skin = skin;
  }

  @Override
  public void moveProjectile(int speed) {
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
