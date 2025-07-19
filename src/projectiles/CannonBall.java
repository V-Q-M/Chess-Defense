package projectiles;
import main.Direction;
import main.GamePanel;
import main.TextureManager;


public class CannonBall extends Projectile {

  // Specialized constructor
  public CannonBall(GamePanel gamePanel, TextureManager textureManager, int x, int y, int size, int speed, int damage, Direction direction) {
    super(gamePanel, textureManager, x, y, size, size);
    this.direction = direction;
    this.speed = speed;
    this.health = 200;
    this.damage = damage;
    this.diesOnHit = true;
    this.skin = textureManager.cannonBallImage;
  }

  @Override
  public void update(){
    checkAlive();
    animateBall();
    moveProjectile(speed);
  }
  int animateCounter = 0;

  public void animateBall(){
    if (animateCounter > 30) {
      animateCounter = 0;
      this.y += 12;
    } else {
      animateCounter++;
    }
 }
}
