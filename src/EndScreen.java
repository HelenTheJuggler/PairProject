import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EndScreen extends JPanel{
	JLabel title, score;
	JPanel panel;
	Window win;
	int points;
	public EndScreen(Window w){
		win = w;
		//panel = new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		title = new JLabel("Game Over");
		add(title);
		setBackground(new Color(0,0,0,0));
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
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
		this.add(button);
	}
	public void setScore(int scr){
		points = scr;
		score.setText("Score: " + points);
		
	}
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(win.getSettings().getTheme().getSkyImage(), 0, -600, null, null);
		super.paint(g);
	}
}
