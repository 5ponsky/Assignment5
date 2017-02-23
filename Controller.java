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

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

	}


}
