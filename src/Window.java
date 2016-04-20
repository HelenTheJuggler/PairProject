
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
		
		frame.setPreferredSize(new Dimension(700,400));
		frame.setMinimumSize(new Dimension(200,100));
		frame.setSize(new Dimension(700,400));
		//frame.setResizable(false);
		
		catapult = new Catapult();
		frame.setContentPane(catapult);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void startGame(){
		BufferedImage cat;
		try {
		    cat = ImageIO.read(new File("Pics\\KittenInCup.png"));
		    catapult.startLaunch(cat);
		} catch (IOException e) {
		}
	}
	
	public static void main(String[] args){
		Window win = new Window();
		win.startGame();
	}
}
