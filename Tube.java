import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;

class Tube extends Sprite {
  double gravity, xVel;
  int x_pos, y_pos;
  boolean tubeUpwards, isKicked;
  Random random;

  final int TUBEBOUND = 115;

  static Image tube_down_image = null;
  static Image tube_up_image = null;

  // Return true because a "Tube" is a "Tube"
  public boolean isTube() { return true; }

  // Return x_pos or y_pos
  public int xPos() { return x_pos; }
  public int yPos() { return y_pos; }

  // Return Image dimensions
  public int ImageW() { return tube_down_image.getWidth(null); }
  public int ImageH() { return tube_down_image.getHeight(null); }

  // Return true if we detect/want a collision
  public boolean collided(Sprite a, Sprite b) { return false; }

  // Produce a clone of the Tube
  Sprite copy() { return new Tube(this); }

  // Default constructor
  Tube(Random r) {
    random = r;
    gravity = -4.5;
    x_pos = 555;
    isKicked = false;

    setPos(x_pos, y_pos);

    // Only load the sprites if they exist and an instance is created
    try {
      if(tube_up_image == null)
			   Tube.tube_up_image = ImageIO.read(new File("tube_up.png"));
      if(tube_down_image == null)
			   Tube.tube_down_image = ImageIO.read(new File("tube_down.png"));
      //this.setImages(Tube.tube_up_image, Tube.tube_up_image);
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

    tubeUpwards = random.nextBoolean();

    if(tubeUpwards)
      y_pos = random.nextInt(TUBEBOUND) + 250;
    else
      y_pos = random.nextInt(TUBEBOUND) - 250;
  }

  // Copy constructor
  Tube(Tube t) {
    this.gravity = t.gravity;
    this.xVel = t.xVel;
    this.x_pos = t.x_pos;
    this.y_pos = t.y_pos;
    this.tubeUpwards = t.tubeUpwards;
    this.isKicked = t.isKicked;
    this.random = t.random;
  }

  public boolean update() {
    if(this.isKicked) {
      x_pos = x_pos + (int) xVel;
      gravity = gravity + 0.4;
      y_pos = y_pos + (int) gravity;
    } else
      x_pos = x_pos - 6;
    if(x_pos < -56)
      return true;
    return false;
  }

  public boolean beenHit(double x) {
    xVel = x;

    // Fire off if the tube hasn't been hit until now
    if(!isKicked) {
      isKicked = true;
      return false;
    }
    return true;
  }

  public void drawYourself(Graphics g) {
    if(this.tubeUpwards)
  		g.drawImage(Tube.tube_up_image, this.x_pos, this.y_pos, null);
  	else
  		g.drawImage(Tube.tube_down_image, this.x_pos, this.y_pos, null);
  }

}
