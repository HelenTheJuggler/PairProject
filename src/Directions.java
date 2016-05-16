
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Directions extends JPanel{
	Window win;
	JScrollPane scroll;
	JButton back;
	JPanel textPanel;
	ArrayList<String> text;
	
	public Directions(Window w){
		win = w;
		scroll = new JScrollPane();
		textPanel = new JPanel();
		textPanel.setBackground(null);
		setBackground(null);
		scroll.setBackground(null);
		
		text = new ArrayList<String>();
		String[] levText;
		for(int i=0; i<win.getLevelSet().numLevels(); i++){
			levText = win.getLevelSet().getLevel(i).getText().split("\n");
			for(int x = 0; x<levText.length; x++){
				text.add(levText[x]);
			}
		}
		
		JLabel label;
		for(int i=0; i<text.size(); i++){
			label = new JLabel(text.get(i));
			label.setBackground(null);
			label.setForeground(win.getSettings().getTheme().getFontColor());
			label.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
			label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			textPanel.add(label);
		}
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
		
		back = new JButton("Back");
		back.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				win.goToIntro();
			}
		});
		
		scroll.setViewportView(textPanel);
		setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);
		add(back, BorderLayout.SOUTH);
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(win.getSettings().getTheme().getSkyImage(), 0, -600, null, null);
		super.paint(g);	
	}

}
