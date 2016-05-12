import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class EndScreen extends JPanel{
	private JLabel title, score;
	private JPanel panel;
	private Window win;
	private int points;
	private JButton playAgain, mainMenu;
	private String[] themeNames = {"Day", "Night"};
	private Theme[] themes;
	private JComboBox<String> themeBox;
	private Theme currentTheme;
	public EndScreen(Window w){
		win = w;
		setSize(new Dimension(700, 400));
		setBackground(new Color(0,0,0,0));
		
		setUp();
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel butts = new JPanel();
		butts.setBackground(new Color(0,0,0,0));
		butts.add(mainMenu);
		butts.add(playAgain);
		add(Box.createRigidArea(new Dimension(0,50)));
		add(score);
		add(butts);
		
		//panel = new JPanel();
	/*	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		title = new JLabel("Game Over");
		title.setSize(100, 100);add(title);
		setBackground(new Color(0,0,0,0));
		//title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		score = new JLabel("Score: ");
		JButton button = new JButton("Play Again");
		button.setActionCommand("Play Again");
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				win.getGame().getCat().newCat();
				win.startGame();
			}
		});
		button.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.add(button);*/
	}
	
	
	public void setScore(int scr){
		points = scr;
		score.setText("Score: " + points);
	}
	private void setUp(){
		score = new JLabel("Score: " + win.getGame().getScore());
		score.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
		score.setForeground(win.getSettings().getTheme().getFontColor());
		
		playAgain = new JButton("Play Again");
		playAgain.setActionCommand("Play Again");
		playAgain.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		playAgain.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				win.getGame().getCat().newCat();
				win.startGame();
			}
		});
		
		mainMenu = new JButton("Main Menu");
		mainMenu.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		mainMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				win.getGame().getCat().newCat();
				win.startGame();
				win.goToIntro();
			}
		});
	}
	public void update(){
		score.setForeground(win.getSettings().getTheme().getFontColor());
	}
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(win.getSettings().getTheme().getSkyImage(), 0, -600, null, null);
		super.paint(g);		
	}
}
