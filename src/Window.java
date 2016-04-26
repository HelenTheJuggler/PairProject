
import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

public class Window {
	JFrame frame;
	JPanel content;
	CardLayout layout;
	Game game;
	Settings settings;
	Directions direction;
	IntroScreen intro;
	EndScreen end;
	
	final private String GAME = "game screen";
	final private String SETTINGS = "settings screen";
	final private String INTRO = "title screen";
	final private String END = "game over";
	final private String DIRECTIONS = "directions screen";
	
	
	public Window(){
		frame = new JFrame("CATapult");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setPreferredSize(new Dimension(700,400));
		frame.setMinimumSize(new Dimension(200,100));
		frame.setSize(new Dimension(700,400));
		//frame.setResizable(false);
		
		game = new Game(this);
		settings = new Settings(this);
		direction = new Directions();
		intro = new IntroScreen();
		end = new EndScreen();
		
		content = new JPanel();
		content.add(game);
		content.add(settings);
		content.add(intro);
		content.add(end);
		content.add(direction);
		
		layout = new CardLayout();
		layout.addLayoutComponent(game, GAME);
		layout.addLayoutComponent(settings, SETTINGS);
		layout.addLayoutComponent(intro, INTRO);
		layout.addLayoutComponent(end, END);
		layout.addLayoutComponent(direction, DIRECTIONS);
		content.setLayout(layout);
		layout.show(content, GAME);
		
		frame.setContentPane(content);
		
		frame.pack();
		frame.setVisible(true);
	}
	public void gameComplete(){
		//endScreen.setScore(game.getScore());
		layout.show(content, END);
	}
	public void goToIntro(){
		layout.show(content, INTRO);
	}
	public void goToDirections(){
		layout.show(content, DIRECTIONS);
	}
	public void startGame(){
		game.startLaunch();
		layout.show(content, GAME);
	}
	public Settings getSettings(){
		return settings;
	}
	public Game getGame(){
		return game;
	}
	public static void main(String[] args){
		Window win = new Window();
		win.startGame();
	}
}
