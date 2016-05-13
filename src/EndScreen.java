import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EndScreen extends JPanel{
	private JLabel score, result;
	private Window win;
	private int points;
	private JButton playAgain, mainMenu, cont;
	private final String COMPLETE = "Level complete";
	private final String INCOMPLETE = "Level failed";
	
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
		butts.add(cont);
		butts.add(playAgain);
		add(Box.createRigidArea(new Dimension(0,50)));
		add(result);
		add(score);
		add(butts);
		
	}
	
	public void setScore(int scr){
		cont.setEnabled(true);
		if(!win.getGame().isComplete()){
			cont.setEnabled(false);
			result.setText(INCOMPLETE);
		}else{
			result.setText(COMPLETE);
		}
		points = scr;
		score.setText("Score: " + points);
	}

	private void setUp(){
		result = new JLabel();
		result.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
		result.setForeground(win.getSettings().getTheme().getFontColor());
		result.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		score = new JLabel("Score: " + win.getGame().getScore());
		score.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
		score.setForeground(win.getSettings().getTheme().getFontColor());
		score.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		playAgain = new JButton("Play Again");
		playAgain.setActionCommand("Play Again");
		playAgain.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		playAgain.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				win.getGame().getCat().newCat();
				win.startGame();
				win.getLevelIntro().addSkip();
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
		
		cont = new JButton("Continue");
		cont.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		cont.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				win.getGame().getCat().newCat();
				win.nextLevel();
				win.startGame();
			}
		});
	}
	
	public void update(){
		score.setForeground(win.getSettings().getTheme().getFontColor());
		result.setForeground(win.getSettings().getTheme().getFontColor());
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(win.getSettings().getTheme().getSkyImage(), 0, -600, null, null);
		super.paint(g);
	}
	
}
