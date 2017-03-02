import java.util.LinkedList;
import java.util.Iterator;

class Model {
  int addTubeWhenZero, maximumTubes;

  Bird bird;
  Chuck chuck;
  Cloud cloud;
  Hand hand;
  Random random;
  Tube tube;
  LinkedList<Sprite> sprites;

  Model() {
  addTubeWhenZero = 45;

  random = new Random(420);
  bird = new Bird(this);
  hand = new Hand(bird);
  cloud = new Cloud(random);
  tube = new Tube(random);
  sprites = new LinkedList<Sprite>();

  sprites.add(cloud);
  sprites.add(bird);
  sprites.add(hand);
  sprites.add(tube);
  }

  // Update the world model
  public void update() {

    // Update each item in the list of world entities
    Iterator<Sprite> it = sprites.iterator();
    while(it.hasNext()) {
      Sprite s = it.next();
      if(s.update()) {
        it.remove();
        System.out.println("SPRITE REMOVED");
      }

    }

    // If enough time has passed, and we don't have too many tubes,
    // add one and reset the timer
    if(addTubeWhenZero <= 0 && maximumTubes < 4) {
      Tube t = new Tube(random);
      sprites.add(t);
      addTubeWhenZero = 45;
      System.out.println("ADDED A NEW TUBE");
    }
    --addTubeWhenZero;

    if(bird.y_pos > 575) {
      System.out.println("GAME OVER!");
      System.exit(0);
    }

  }

  double evaluateAction(int action, int depth) {

    // Evaluate the state
    if(bird.energy <= 0.0)
      return 0.0;
    if(depth >= d)
      return bird.energy;

    // Simulate the action
    Model copy = new Model(this); // uses the copy constructor
    copy.doAction(action);
    copy.update(); // advance simulated time

    // Recurse
    if(depth % k != 0)
       return copy.evaluateAction(ACTION_NOTHING, depth + 1);
    else {
       double best = copy.evaluateAction(ACTION_NOTHING, depth + 1);
       best = Math.max(best,
         copy.evaluateAction(ACTION_FLAP, depth + 1));
       best = Math.max(best,
         copy.evaluateAction(ACTION_CHUCK, depth + 1));
       return best;
    }
  
  }

  public void onClick() {
    bird.flap();
  }

  public void sendChuck() {
    chuck = new Chuck(this, random);
    sprites.add(chuck);
    bird.energy -= 30;
  }

}
