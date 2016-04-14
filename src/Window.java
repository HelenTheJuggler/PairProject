
import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

public class Window {
	JFrame frame;
	JPanel content;
	Catapult catapult;
	
	public Window(){
		frame = new JFrame("CATapult");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setPreferredSize(new Dimension(800,400));
		frame.setSize(new Dimension(800,400));
		frame.setResizable(false);
		
		catapult = new Catapult();
		frame.setContentPane(catapult);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void startGame(){
		ImageIcon icon = new ImageIcon("PairProject\\Pics\\KittenInCup.jpg");
		Image cat = icon.getImage();
		System.out.println(cat);
		catapult.startLaunch(cat);
		
		/*try {
		    cat = ImageIO.read(new File("PairProject\\Pics\\KittenInCup.jpg"));
		    catapult.startLaunch(cat);
		} catch (IOException e) {
		}*/
	}
	
	public static void main(String[] args){
		Window win = new Window();
	}
}
