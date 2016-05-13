
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

public class Window {
	private JFrame frame;
	private JPanel content;
	private CardLayout layout;
	private Game game;
	private Settings settings;
	private Directions direction;
	private IntroScreen intro;
	private LevelIntro levIntro;
	private EndScreen end;
	
	private LevelSet levSet;
	private Timer waitTime;
	
	final private String GAME = "game screen";
	final private String SETTINGS = "settings screen";
	final private String INTRO = "title screen";
	final private String END = "game over";
	final private String DIRECTIONS = "directions screen";
	final private String LEV_INTRO = "level inroduction";
	
	public Window(){
		frame = new JFrame("CATapult");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setPreferredSize(new Dimension(700,400));
		frame.setMinimumSize(new Dimension(200,100));
		frame.setSize(new Dimension(700,400));
		frame.setResizable(false);
		
		settings = new Settings(this);
		game = new Game(this);
		direction = new Directions();
		intro = new IntroScreen(this);
		end = new EndScreen(this);
		levIntro = new LevelIntro(this);
		
		levSet = new LevelSet();
		waitTime = new Timer(2000, new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				waitTime.stop();
				levIntro.addSkip();
			}
		});
		
		content = new JPanel();
		content.add(game);
		content.add(settings);
		content.add(intro);
		content.add(end);
		content.add(direction);
		content.add(levIntro);
		
		layout = new CardLayout();
		layout.addLayoutComponent(game, GAME);
		layout.addLayoutComponent(settings, SETTINGS);
		layout.addLayoutComponent(intro, INTRO);
		layout.addLayoutComponent(end, END);
		layout.addLayoutComponent(direction, DIRECTIONS);
		layout.addLayoutComponent(levIntro, LEV_INTRO);
		content.setLayout(layout);
		
		layout.show(content, INTRO);
		
		frame.setContentPane(content);
		
		frame.pack();
		frame.setVisible(true);
		frame.createBufferStrategy(2);
	}
	public void gameComplete(){
		end.setScore(game.getScore());
		layout.show(content, END);
	}
	public void goToIntro(){
		layout.show(content, INTRO);
	}
	public void goToDirections(){
		layout.show(content, DIRECTIONS);
	}
	public void startGame(){
		levIntro.setText(levSet.getCurrent());
		layout.show(content, LEV_INTRO);
		waitTime.start();
	}
	public void nextLevel(){
		levSet.nextLevel();
	}
	public void goToSettings(){
		layout.show(content, SETTINGS);
	}
	public Settings getSettings(){
		return settings;
	}
	public Game getGame(){
		return game;
	}
	public void updatedTheme(){
		end.update();
	}
	public LevelSet getLevelSet(){
		return levSet;
	}
	public LevelIntro getLevelIntro(){
		return levIntro;
	}
	public void skipLevelIntro(){
		waitTime.stop();
		game.startLaunch(levSet.getCurrent());
		layout.show(content, GAME);
	}
	
	public static void main(String[] args){
		new Window();
	}
}
