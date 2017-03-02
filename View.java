import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Iterator;

@SuppressWarnings("serial")
class View extends JPanel
{
	JButton b1;
	Model model;
	Controller controller;

	View(Controller c, Model m)
	{
		model = m;
		controller = c;
		controller.setView(this);
	}

	public void paintComponent(Graphics g) {
		// Draw background
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// Draw either up or down sprite
		Iterator<Sprite> it = model.sprites.iterator();
		while(it.hasNext()) {
			Sprite s = it.next();
			s.drawYourself(g);
		}

		// Draw energy bar
		g.setColor(new Color(0, 255, 0));
		g.fillRect(425, 200, 75, 2 * model.bird.energy);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(424, 199, 76, 201);
	}

}
