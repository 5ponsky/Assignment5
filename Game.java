import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Game extends JFrame
{
	Model model;
	View view;
	int frameCounter;

	public Game()
	{
		model = new Model();
		Controller controller = new Controller(model);
		view = new View(controller, model);
		this.setSize(500, 500);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void run() {
		while(true) {
			model.update();
			view.repaint(); // Implicitly calls View.paintComponent

			// Sleep 50ms
			try {
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

			System.out.println("running...");
		}
	}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}
}
