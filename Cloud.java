import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;

class Cloud extends Sprite {
  int x_pos, y_pos;
  Random random;
  static Image cloud_image = null;

  // Return false because a "Cloud" isn't a "Tube"
  public boolean isTube() { return false; }

  // Return x_pos or y_pos
  public int xPos() { return x_pos; }
  public int yPos() { return y_pos; }

  // Return Image dimensions
  public int ImageW() { return cloud_image.getWidth(null); }
  public int ImageH() { return cloud_image.getHeight(null); }

  // Return true if we detect/want a collision
  public boolean collided(Sprite a, Sprite b) { return false; }

  // Not needed atm
  public boolean beenHit(double xVel) { return false; }

  Cloud(Random r) {
    random = r;
    x_pos = 575;

    setPos(x_pos, y_pos);

    // Only load the sprites if they exist and an instance is created
    try {
      if(cloud_image == null)
        Cloud.cloud_image = ImageIO.read(new File("cloud.png"));
    } catch(Exception e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }

  Cloud(Sprite s) {
    this.x_pos = s.x_pos;
    this.y_pos = s.y_pos;

  }

  public boolean update() {
    x_pos = x_pos - 2;

    if(x_pos < -501) {
      x_pos = 501;
      y_pos = random.nextInt(250) + 50;
    }
    return false;
  }

  public void drawYourself(Graphics g) {
    g.drawImage(Cloud.cloud_image, this.x_pos, this.y_pos, null);
  }

}
