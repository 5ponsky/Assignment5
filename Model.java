import java.util.LinkedList;
import java.util.Iterator;
//import java.Controller.Action;

class Model {
  boolean isCopy;
  int addTubeWhenZero, maximumTubes;
  int d = 35; // How many steps to look ahead
  int k = 4; // Frame interval in which we calculate a decision

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
    addTubeWhenZero = 25;

    random = new Random(420);
    bird = new Bird(this);
    hand = new Hand(bird);
    cloud = new Cloud(random);
    //tube = new Tube(random);
    sprites = new LinkedList<Sprite>();

    sprites.add(cloud);
    sprites.add(bird);
    sprites.add(hand);
    //sprites.add(tube);
  }

  // Copy constructor
  Model(Model m) {
	this.isCopy = true;
	this.sprites = new LinkedList<Sprite>();
    this.addTubeWhenZero = m.addTubeWhenZero;
    this.maximumTubes = m.maximumTubes;
    this.random = new Random(420);
    
    //this.cloud = new Cloud(m.cloud);
    //this.bird = new Bird(m.bird);
    //this.hand = new Hand(m.hand);
    //this.tube = new Tube(m.random);
    
    //this.sprites.add(this.cloud);
    //this.sprites.add(this.bird);
    //this.sprites.add(this.hand);
    //this.sprites.add(this.tube);

    Iterator<Sprite> it = m.sprites.iterator();
    while(it.hasNext()) {
      Sprite s = it.next();
      
      if(s.isTube()) {
    	//this.sprites.add(new Tube((Tube) s, this.random));
    	//this.tube = (Tube) s.copy();
    	this.tube = new Tube((Tube) s, this.random);
    	this.sprites.add(this.tube);
      } else if(s.isCloud()) {
      	//this.sprites.add(new Cloud((Cloud) s, this.random));
      	//this.cloud = (Cloud) s.copy();
    	this.cloud = new Cloud((Cloud) s, this.random);
      	this.sprites.add(this.cloud);
      } else if(s.isBird()){ 
      	//this.sprites.add(new Bird((Bird) s, this));
    	//this.bird = (Bird) s.copy();
      	this.bird = new Bird((Bird) s, this);
      	this.sprites.add(this.bird);
      } else if(s.isHand()){
      	//this.sprites.add(new Hand((Hand) s, this.bird));
    	//this.hand = (Hand) s.copy();
      	this.hand = new Hand((Hand) s, this.bird);
      	this.sprites.add(this.hand);
      } else if(s.isChuck()){
    	//this.sprites.add(new Chuck((Chuck) s, this, this.random));
    	//this.chuck = (Chuck) s.copy();
      	this.chuck = new Chuck((Chuck) s, this, this.random);
      	this.sprites.add(this.chuck);
      }
      
      //this.sprites.add(s);
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
    	//if(!isCopy) { System.out.println("DOING FLAP"); }
      this.onClick();
    } else if(action == Action.ACTION_CHUCK) {
    	//if(!isCopy) { System.out.println("DOING STUFF"); }
      this.sendChuck();
    } else if(action == Action.ACTION_NOTHING) {
    	//if(!isCopy) { System.out.println("DOING NOTHING"); }
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
