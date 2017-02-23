import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Iterator;
import javax.imageio.ImageIO;

class Bird extends Sprite {
  boolean flapped;
  double gravity;
  int x_pos, y_pos, flapCounter, energy, allowDamageWhenZero;

  Model model;

  static Image bird_image_up = null;
  static Image bird_image_down = null;

  // Return false because a "Bird" isn't a "Tube"
  public boolean isTube() { return false; }

  // Return x_pos or y_pos
  public int xPos() { return x_pos; }
  public int yPos() { return y_pos; }

  // Return Image dimensions
  public int ImageW() { return bird_image_up.getWidth(null); }
  public int ImageH() { return bird_image_up.getHeight(null); }

  // Not needed atm
  public boolean beenHit(double xVel) { return false; }

  Bird(Model m) {
    model = m;
    x_pos = 10;
    y_pos = 250;
    gravity = -6.5;
    energy = 100;

    setPos(x_pos, y_pos);

    // Only load the sprites if they exist and an instance is created
    try {
      if(bird_image_up == null)
        this.bird_image_up = ImageIO.read(new File("bird1.png"));
      if(bird_image_down == null)
        this.bird_image_down = ImageIO.read(new File("bird2.png"));
    } catch(Exception e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }

  // Update the bird
  public boolean update() {

    // Allow the bird to recharge energy so long as it has more than 0,
    // and cap the energy at 100
    if(energy > 0) {
      energy += 1;

      gravity = gravity + 0.4;
      y_pos = y_pos + (int) gravity;
      --flapCounter;

      if(loseEnergy() && allowDamageWhenZero <= 0) {
        energy = energy - 65;
        allowDamageWhenZero = 25;
      }
      --allowDamageWhenZero;

      // Keep the energy levels from going out of bounds
      if(energy > 100)
        energy = 100;
      } else if (energy < 0)
        energy = 0;

      // Simulate a game "ceiling"
      if(y_pos < 0) {
        y_pos = 0;
        gravity = 0;
      }

    return false;
  }

  // Make the bird fly
  public void flap() {
    if(energy > 0) {
      gravity = gravity - 4.5;
      y_pos = y_pos - (int) gravity;
      flapCounter = 3;
    } else
      energy = 0;
  }

  // Check each item in the sprites list to see if it is a tube
  public boolean loseEnergy() {
    Iterator<Sprite> it = model.sprites.iterator();
    while(it.hasNext()) {
      Sprite s = it.next();

      if(s.isTube()) {
        if(collided(this, s))
            return true;
      }
    }
    return false;

  }

  public boolean collided(Sprite a, Sprite b) {
    if(collisionDetected(a.xPos(), a.yPos(), a.ImageW(), a.ImageH(),
                        b.xPos(), b.yPos(), b.ImageW(), b.ImageH() ) )
                          return true;
    return false;
  }

  public void drawYourself(Graphics g) {
    if(flapCounter <= 0)
      g.drawImage(this.bird_image_up, this.x_pos, this.y_pos, null);
    else
      g.drawImage(this.bird_image_down, this.x_pos, this.y_pos, null);
  }

}
