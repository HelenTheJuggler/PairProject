import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LevelScreen extends JPanel{
	private Window win;
	private JButton back;
	private JLabel text;
	
	public LevelScreen(Window w){
		win = w;
		
		setBackground(new Color(0,0,0,0));
		
		back = new JButton("Back");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				win.goToIntro();
			}
		});
		
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.weightx = 1;
		con.weighty = 1;
		con.insets = new Insets(10,10,10,10);
		
		int n = 1;
		JButton butt;
		for(int r=0; r<3; r++){
			for(int c=0; c<5; c++){
				con.gridx = c;
				con.gridy = r+1;
				butt = new JButton("" + n);
				butt.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
				butt.setActionCommand(butt.getText());
				butt.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						win.startLevel(Integer.parseInt(e.getActionCommand()));
					}
				});
				add(butt, con);
				n++;
			}
		}
		
		JLabel text = new JLabel("Choose a level");
		text.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
		text.setForeground(win.getSettings().getTheme().getFontColor());
		text.setBackground(new Color(0,0,0,0));
		con.gridx = 0;
		con.gridy = 0;
		con.gridwidth = 5;
		add(text, con);
		
		con.gridx = 2;
		con.gridy = 4;
		con.gridwidth = 1;
		add(back, con);
	}
	
	public void update(){
		text.setForeground(win.getSettings().getTheme().getFontColor());
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(win.getSettings().getTheme().getSkyImage(), 0, -600, null, null);
		super.paint(g);
	}
}
