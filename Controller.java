import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Robot;
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

	// Update the controller (used for simple AI)
	void update() {

		// Evaluate each possible action
		double score_nothing = model.evaluateAction(ACTION_NOTHING, 0);
		double score_flap = model.evaluateAction(ACTION_FLAP, 0);
		double score_chuck = model.evaluateAction(ACTION_CHUCK, 0);

		// Do the best one
		if(score_chuck > score_flap && score_chuck > score_nothing)
			do_action(ACTION_CHUCK);
		else if(score_flap > score_nothing)
			do_action(ACTION_FLAP);
		else {
			 do_action(nothing);
			 if(score_nothing == 0.0)
				 System.out.println("Found no way to survive!");
		}
	}

	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseClicked(MouseEvent e) { }

}
