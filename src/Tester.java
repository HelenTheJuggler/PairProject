import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Tester {
	JFrame frame;
	JPanel content;
	Game game;
	
	public Tester(){
		frame = new JFrame("CATapult");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setPreferredSize(new Dimension(700,400));
		frame.setMinimumSize(new Dimension(200,100));
		frame.setSize(new Dimension(700,400));
		//frame.setResizable(false);
		
		game = new Game(new Window());
		frame.setContentPane(game);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		Tester win = new Tester();
	}
}
