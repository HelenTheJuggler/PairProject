
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class IntroScreen extends JPanel{
	private JButton play;
	private JButton instructions;
	private JButton settings;
	private Window window;

	public IntroScreen(Window w){
		window = w;
		setSize(new Dimension(700, 400));
		setBackground(new Color(0,0,0,0));
		
		initButtons();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(0,0,0,0));
		buttonPanel.add(settings);
		settings.setSize(new Dimension(50,50));
		buttonPanel.add(play);
		play.setSize(new Dimension(150,50));
		buttonPanel.add(instructions);
		instructions.setSize(new Dimension(50,50));
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(Box.createRigidArea(new Dimension(5, 200)));
		add(buttonPanel);
	}
	
	private void initButtons(){
		play = new JButton();
		ImageIcon[] icons = window.getSettings().getTheme().getImageIcons("play");
		play.setIcon(icons[0]);
		play.setSelectedIcon(icons[1]);
		play.setRolloverIcon(icons[1]);
		play.setDisabledIcon(icons[0]);
		play.setBackground(null);
		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				window.startGame();
			}
		});
		
		instructions = new JButton();
		icons = window.getSettings().getTheme().getImageIcons("qMark");
		instructions.setIcon(icons[0]);
		instructions.setSelectedIcon(icons[1]);
		instructions.setRolloverIcon(icons[1]);
		instructions.setDisabledIcon(icons[0]);
		instructions.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				window.goToDirections();
			}
		});
		
		settings = new JButton();
		icons = window.getSettings().getTheme().getImageIcons("gear");
		settings.setIcon(icons[0]);
		settings.setSelectedIcon(icons[1]);
		settings.setRolloverIcon(icons[1]);
		settings.setDisabledIcon(icons[0]);
		settings.setSize(new Dimension(50,50));
		settings.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				window.goToSettings();
			}
		});
	}
	
	public void update(){
		repaint();
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(window.getSettings().getTheme().getSkyImage(), 0, -600, null, null);

		super.paint(g);
		BufferedImage title = window.getSettings().getTheme().getTitle();
		BufferedImage scaleTitle = Catapult.scaleImage(title, 600.0/title.getWidth());
		g2.drawImage(scaleTitle, getWidth()/2 - scaleTitle.getWidth()/2, 10, null, null);
	}
}
