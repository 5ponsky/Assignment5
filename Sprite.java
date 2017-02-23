import java.awt.Graphics;
import java.awt.Image;

abstract class Sprite {
  public static Image image1, image2;
  public int x_pos, y_pos;

  // Return true if the object is of type "Tube"
  abstract boolean isTube();

  // Return x_pos or y_pos
  abstract int xPos();
  abstract int yPos();

  // Return Image properties
  abstract int ImageW();
  abstract int ImageH();

  // Update the various components and properties of the object
  abstract boolean update();

  // Draw the image(s) of the object
  abstract void drawYourself(Graphics g);

  // Short-hand collision Detection
  abstract boolean collided(Sprite a, Sprite b);

  abstract boolean beenHit(double xVel);

  public void setImages(Image im1, Image im2) {
    image1 = im1;
    image2 = im2;
  }

  public void setImage(Image im1) {
    this.image1 = im1;
  }

  public void setPos(int x, int y) {
    x_pos = x;
    y_pos = y;
  }

  public static boolean collisionDetected(
    int a_x, int a_y, int a_w, int a_h, int b_x, int b_y, int b_w, int b_h) {

    if(a_x + a_w < b_x) // right -> left collision
      return false;
    if(a_x > b_x + b_w) // left -> right collision
      return false;
    if(a_y + a_h < b_y) // bottom -> top collision
      return false;
    if(a_y > b_y + b_h) // top -> bottom collision
      return false;

    System.out.println("HIT!");
    return true;
  }

  //public static bounce(Sprite a, Sprite b) {

}
