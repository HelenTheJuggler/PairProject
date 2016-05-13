import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LevelIntro extends JPanel{
	private Window win;
	private JPanel textBox;
	private JLabel levNum;
	private JButton skip;
	
	public LevelIntro(Window w){
		win = w;
		
		setMinimumSize(new Dimension(700,400));
		setSize(new Dimension(700,400));
		setBackground(new Color(0,0,0,0));
		
		textBox = new JPanel();
		textBox.setLayout(new BoxLayout(textBox, BoxLayout.PAGE_AXIS));
		textBox.setBackground(new Color(0,0,0,0));
		
		levNum = new JLabel();
		levNum.setFont(new Font(Font.DIALOG, Font.PLAIN, 60));
		levNum.setForeground(win.getSettings().getTheme().getFontColor());
		levNum.setBackground(new Color(0,0,0,0));
		
		skip = new JButton("Continue");
		skip.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		skip.setVisible(false);
		skip.setEnabled(false);
		skip.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				win.skipLevelIntro();
			}
		});
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.5;
		add(levNum,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.PAGE_START;
		c.weighty = 1;
		add(textBox, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 2;
		c.weighty = 0.3;
		add(skip, c);
		
		repaint();
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(win.getSettings().getTheme().getSkyImage(), 0, -600, null, null);
		super.paint(g);	
	}
	
	public void addSkip(){
		skip.setVisible(true);
		skip.setEnabled(true);
	}
	
	public void setText(Level l){	
		skip.setVisible(false);
		skip.setEnabled(false);
		
		String[] split = l.getText().split("\n");
		JLabel label;
		textBox.removeAll();
		for(int i=0; i<split.length; i++){
			label = new JLabel(split[i]);
			label.setForeground(win.getSettings().getTheme().getFontColor());
			label.setBackground(new Color(0,0,0,0));
			label.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
			label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			textBox.add(label);
		}
		
		levNum.setText("Level " + win.getLevelSet().getLevelNum());
		levNum.setForeground(win.getSettings().getTheme().getFontColor());
	}

}
