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
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		title = new JLabel("Game Over");
		this.add(title);
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		score = new JLabel("Score: ");
		//JPanel pan = new JPanel(new BoxLayout(this, BoxLayout.X_AXIS));
		JButton button = new JButton("Play Again");
		button.setActionCommand("Play Again");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
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
}
