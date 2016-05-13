import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EndScreen extends JPanel{
	private JLabel score;
	private Window win;
	private int points;
	private JButton playAgain, mainMenu;
	
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
				win.nextLevel();
				win.startGame();
			}
		});
		
		mainMenu = new JButton("Main Menu");
		mainMenu.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		mainMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				win.getGame().getCat().newCat();
				win.startGame();
				win.nextLevel();
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
