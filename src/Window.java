
import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

public class Window {
	JFrame frame;
	JPanel content;
	Game game;
	Settings settings;
	/*Directions direction;
	IntroScreen intro;
	EndScreen end;
	*/
	
	
	public Window(){
		frame = new JFrame("CATapult");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setPreferredSize(new Dimension(700,400));
		frame.setMinimumSize(new Dimension(200,100));
		frame.setSize(new Dimension(700,400));
		//frame.setResizable(false);
		
		game = new Game(this);
		settings = new Settings(this);
		/*direction = new Directions();
		intro = new IntroScreen();
		end = new EndScreen();
		 */
		frame.setContentPane(game);
		
		frame.pack();
		frame.setVisible(true);
	}
	public void gameComplete(){
		//endScreen.setScore(game.getScore());
		//frame.setContentPane(endScreen);
	}
	public void goToIntro(){
		//frame.setContentPane(intro);
	}
	public void goToDirections(){
		//frame.setContentPane(settings);
	}
	public void launchComplete(){
		game.launchComplete();
		frame.setContentPane(game);
	}
	public void startGame(){
		/*game.getCatapult().startLaunch(game.getCat());
		frame.setContentPane(game);
		 */
	}
	public Settings getSettings(){
		return settings;
	}
	public Game getGame(){
		return game;
	}
	public static void main(String[] args){
		Window win = new Window();
	}
}
