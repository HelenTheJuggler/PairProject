
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
		
		catapult = new Catapult();
		frame.setContentPane(catapult);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void startGame(){
		BufferedImage cat = null;
		try {
		    cat = ImageIO.read(new File("Catapult\\Pics\\KittenInCup.jpg"));
		    catapult.startLaunch(cat);
		} catch (IOException e) {
		}
	}
	
	public static void main(String[] args){
		Window win = new Window();
	}
}
