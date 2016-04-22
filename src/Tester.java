import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
		
		Catapult cat = new Catapult(null);
		frame.setContentPane(cat);
		
		BufferedImage kitty = null;
		try {
		    kitty = ImageIO.read(new File("Pics\\KittenInCup.png"));
		} catch (IOException e) {}
		
		frame.pack();
		frame.setVisible(true);
		cat.startLaunch(kitty);
	}
	
	public static void main(String[] args){
		Tester win = new Tester();
	}
}
