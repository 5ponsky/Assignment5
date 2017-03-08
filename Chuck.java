import java.awt.Image;
import java.awt.Graphics;
import java.io.File;
import java.util.Iterator;
import javax.imageio.ImageIO;

class Chuck extends Sprite {
  double gravity, xVel;
  int x_pos, y_pos;

  Model model;

  static Image chuck_image = null;

  // Return false because a "Chuck" isn't a "Tube"
  public boolean isTube() { return false; }
  
  // Return true because a "Chuck" is a "Bird"
  public boolean isBird() { return false; }

  // Return Image dimensions
  public int ImageW() { return chuck_image.getWidth(null); }
  public int ImageH() { return chuck_image.getHeight(null); }

  // Return x_pos or y_pos
  public int xPos() { return x_pos; }
  public int yPos() { return y_pos; }

  // Not needed atm
  public boolean beenHit(double xVel) { return false; }

  // Produce a clone of the Tube
  Chuck copy() { return new Chuck(this); }

  Chuck(Model m, Random r) {
    model = m;
    x_pos = -100;
    y_pos = (r.nextInt(175) + 250);
    gravity = (r.nextInt(14) - 15.5);
    xVel = (r.nextInt(4) + 11.0);

    setPos(x_pos, y_pos);

    try {
      if(chuck_image == null)
        Chuck.chuck_image = ImageIO.read(new File("chuck_norris.png"));
    } catch(Exception e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  //System.out.println("CHUCK ACTIVATED!");

  }

  Chuck(Chuck c) {
	this.model = c.model;
    this.gravity = c.gravity;
    this.xVel = c.xVel;
    this.x_pos = c.x_pos;
    this.y_pos = c.y_pos;
  }

  public boolean update() {

    // Simulate gravity
    gravity = gravity + 0.2;
    y_pos = y_pos + (int) gravity;

    x_pos = x_pos + (int) xVel;

    if(judoKick()) {
      gravity = -4.5;
      xVel = -xVel;
    }

    if(x_pos > 585 || y_pos > 510)
      return true;
    return false;
  }

  // HI-YA!
  public boolean judoKick() {
    Iterator<Sprite> it = model.sprites.iterator();
    while(it.hasNext()) {
      Sprite s = it.next();

      if(s.isTube()) {
        if(collided(this, s)) {

            // If the tube has already been hit, return false
            // as we want nothing to collide with a "dead" tube
            if(s.beenHit(xVel))
              return false;
            return true;
          }
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
    g.drawImage(Chuck.chuck_image, this.x_pos, this.y_pos, null);
  }

}
