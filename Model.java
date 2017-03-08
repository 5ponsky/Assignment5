import java.util.LinkedList;
import java.util.Iterator;
//import java.Controller.Action;

class Model {
  boolean isCopy;
  int addTubeWhenZero, maximumTubes;
  int d = 45; // How many steps to look ahead
  int k = 8; // Frame interval in which we calculate a decision

  Bird bird;
  Chuck chuck;
  Cloud cloud;
  Hand hand;
  Random random;
  Tube tube;
  LinkedList<Sprite> sprites;

  // Produce a clone of the Bird
  Model copy() { return new Model(this); }

  // Default constructor
  Model() {
    isCopy = false;
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

  // Copy constructor
  Model(Model m) {
	isCopy = true;
	this.sprites = new LinkedList<Sprite>();
    this.addTubeWhenZero = m.addTubeWhenZero;
    this.maximumTubes = m.maximumTubes;
    this.random = new Random(420);
    
    //this.tube = new Tube(this.random);
    this.bird = new Bird(this);
    //this.chuck = m.chuck;
    this.cloud = new Cloud(m.cloud);
    this.hand = new Hand(m.hand);
    //this.tube = m.tube;

    Iterator<Sprite> it = m.sprites.iterator();
    while(it.hasNext()) {
      Sprite s = it.next();
      //System.out.println(s);
      this.sprites.add(s.copy());
      //if(s.isBird())
    	  //this.bird = (Bird)s;
    }
  }

  // Update the world model
  public void update() {
	// Print stuff iff its the original
	

    // Update each item in the list of world entities
    Iterator<Sprite> it = sprites.iterator();
    while(it.hasNext()) {
      Sprite s = it.next();
      if(s.update()) {
        it.remove();
        //System.out.println("SPRITE REMOVED");
      }

    }

    // If enough time has passed, and we don't have too many tubes,
    // add one and reset the timer
    if(addTubeWhenZero <= 0 && maximumTubes < 4) {
      Tube t = new Tube(random);
      sprites.add(t);
      addTubeWhenZero = 25;
      //System.out.println("ADDED A NEW TUBE");
    }
    --addTubeWhenZero;

    if(bird.y_pos > 575)
        bird.energy = 0;
  }

  // Evaluate the best choice based on highest energy level
  double evaluateAction(Action action, int depth) {

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
       return copy.evaluateAction(Action.ACTION_NOTHING, depth + 1);
    else {
       double best = copy.evaluateAction(Action.ACTION_NOTHING, depth + 1);
       best = Math.max(best,
         copy.evaluateAction(Action.ACTION_FLAP, depth + 1));
       best = Math.max(best,
         copy.evaluateAction(Action.ACTION_CHUCK, depth + 1));
       return best;
    }

  }

  // Fires off the received action to the model
  void doAction(Action action) {
    if(action == Action.ACTION_FLAP) {
      this.onClick();
    } else if(action == Action.ACTION_CHUCK) {
    	if(!isCopy) { System.out.println("DOING STUFF"); }
      this.sendChuck();
    } else if(action == Action.ACTION_NOTHING) {
      // Do Nothing
    } else {
      throw new RuntimeException("Unexcepted Action: " + action);
    }
  }

  // Allow the bird to flap if the LMB is clicked
  public void onClick() {
    bird.flap();
  }

  // Summon the powers of Chuck Norris if RMB is clicked
  public void sendChuck() {
    chuck = new Chuck(this, random);
    sprites.add(chuck);
    bird.energy -= 30;
  }

}
