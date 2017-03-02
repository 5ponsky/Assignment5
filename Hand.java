import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;

class Hand extends Sprite {
  boolean gotcha;
  double speed;
  int x_pos, y_pos;
  Bird bird;
  static Image open_hand = null;
  static Image closed_hand = null;

  // Return false because a "Hand" isn't a "Tube"
  public boolean isTube() { return false; }

  // Return x_pos or y_pos
  public int xPos() { return x_pos; }
  public int yPos() { return y_pos; }

  // Return Image dimensions
  public int ImageW() { return open_hand.getWidth(null); }
  public int ImageH() { return open_hand.getHeight(null); }

  // Return true if we detect/want a collision
  public boolean collided(Sprite a, Sprite b) { return false; }

  // Not needed atm
  public boolean beenHit(double xVel) { return false; }

  Hand(Bird b) {
    bird = b;
    gotcha = false;
    x_pos = 10;
    y_pos = 550;

    setPos(x_pos, y_pos);

    // Only load the sprites if they exist and an instance is created
    try {
      if(open_hand == null)
        Hand.open_hand = ImageIO.read(new File("hand1.png"));
      if(closed_hand == null)
        Hand.closed_hand = ImageIO.read(new File("hand2.png"));
    } catch(Exception e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }

  Hand(Sprite s) {
    this.gotcha = s.gotcha;
    this.speed = s.speed;
    this.x_pos = s.x_pos;
    this.y_pos = s.y_pos;


  }

  Sprite clone() {
    return new Sprite(this);
  }

  public boolean update() {
    grab();
    return false;
  }

  public void grab() {

    x_pos = bird.x_pos + 5;
    if(bird.energy <= 0) {
      if(y_pos > bird.y_pos - 15 && y_pos < bird.y_pos + 15) {
        gotcha = true;
        y_pos = y_pos + 3;
        bird.y_pos = y_pos;
      } else {
        y_pos = y_pos - 5;
      }
    }
  }

  public void drawYourself(Graphics g) {
    if(gotcha)
      g.drawImage(Hand.closed_hand, this.x_pos, this.y_pos, null);
    else
      g.drawImage(Hand.open_hand, this.x_pos, this.y_pos, null);
  }

}
