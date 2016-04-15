
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.JPanel;

public class Game extends JPanel implements ActionListener{
	Cat kitty;
	Window win;
//	Obstacle[] obs;
	Timer time;
	int score;
	Point release;
	Catapult cata;
	public Game(Window w){
		win = w;
		
	}
	public void paint(Graphics g){
		super.repaint();
		
	}
	public void launchComplete(){
		kitty.launch(cata.getReleaseVelocity(), this.getReleasePosition());
		time = new Timer();
	}
	public Point getReleasePosition(){
		return release;
	}
	
}
