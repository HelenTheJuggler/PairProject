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
	private JButton butt, mainMenu;
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
		butts.add(butt);
		add(Box.createRigidArea(new Dimension(0,50)));
		add(result);
		add(score);
		add(butts);
		
	}
	
	public void setScore(int scr, boolean last){
		butt.setVisible(true);
		if(!win.getGame().isComplete()){
			result.setText(INCOMPLETE);
			butt.setText("Play Again");
			butt.setActionCommand("Play Again");
		}else{
			result.setText(COMPLETE);
			butt.setText("Continue");
			butt.setActionCommand("Continue");
			if(last){
				butt.setVisible(false);
				result.setText("Level complete. Game over");
				win.getLevelSet().setCurrent(1);
			}
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
		
		butt = new JButton("Play Again");
		butt.setActionCommand("Play Again");
		butt.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		butt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				win.startLevel();
			}
		});
		
		mainMenu = new JButton("Main Menu");
		mainMenu.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		mainMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				win.goToIntro();
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
