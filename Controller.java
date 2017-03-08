import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Robot;
import java.lang.Exception;
import javax.swing.SwingUtilities;


class Controller implements ActionListener, MouseListener
{
	View view;
	Model model;
	Robot robot;

	Controller(Model m)
	{
		model = m;
	}

	void setView(View v) {
		view = v;
		view.addMouseListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		robot.mouseWheel(0);
	}

	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e))
			model.sendChuck();
		else
			model.onClick();
	}

/*
	// Fires off the received action to the model
	void doAction(Action a) {
		if(a == Action.ACTION_FLAP) {
			model.onClick();
		} else if(a == Action.ACTION_CHUCK) {
			model.sendChuck();
		} else if(a == Action.ACTION_NOTHING) {
			System.out.println("HERE");
		} else {
			throw new RuntimeException("Unexcepted Action: " + a);
		}
	}
*/

	// Update the controller (used for simple AI)
	void update() {

		// Evaluate each possible action
		double score_nothing = model.evaluateAction(Action.ACTION_NOTHING, 0);
		double score_flap = model.evaluateAction(Action.ACTION_FLAP, 0);
		double score_chuck = model.evaluateAction(Action.ACTION_CHUCK, 0);

		// Do the best one
		if(score_chuck > score_flap && score_chuck > score_nothing) {
			model.doAction(Action.ACTION_CHUCK);
		} else if(score_flap > score_nothing) {
			model.doAction(Action.ACTION_FLAP);
		} else {
			 model.doAction(Action.ACTION_NOTHING);
			 if(score_nothing == 0.0)
				 System.out.println("Found no way to survive!");
		}
	}

	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseClicked(MouseEvent e) { }

}
