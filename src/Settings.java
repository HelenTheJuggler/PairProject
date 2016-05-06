
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Settings extends JPanel{
	Window win;
	JPanel panel;
	
	String[] names = {"Normal","Other Cat"};
	JComboBox<String> catType;
	
	JCheckBox frictionCheckBox;
	boolean friction;
	
	String[] themes = {"Day", "Night"};
	BufferedImage[] skyImages;
	JComboBox<String> themeBox;
	BufferedImage currentTheme;

	public Settings(Window w){
		win = w;
		friction = false;
		setSize(new Dimension(700,400));
		initSkyImages();
		setUpGUIComponents();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		add(new JLabel("Type of Cat: "), c);
		
		c.gridx = 0;
		c.gridy = 1;
		add(catType, c);
		
		c.gridx = 1;
		c.gridy = 0;
		add(new JLabel("Friction"), c);
		
		c.gridx = 1;
		c.gridy = 1;
		add(frictionCheckBox, c);
		
		c.gridx=0;
		c.gridy=2;
		add(new JLabel("Theme"), c);
		
		c.gridx=1;
		c.gridy=2;
		add(themeBox, c);
	}
	
	private void initSkyImages(){
		skyImages = new BufferedImage[themes.length];
		BufferedImage sky = null;
		try {
		    sky = ImageIO.read(new File("Pics\\DaySky.png"));
		    skyImages[0] = sky;
		} catch (IOException e) {}
		currentTheme = skyImages[0];
	}
	
	private void setUpGUIComponents(){
		catType = new JComboBox<String>(names);
		catType.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				String selected = (String) ((JComboBox)event.getSource()).getSelectedItem();
				if(selected.equals(names[0])){
					win.getGame().setCat(new Cat(false));
				}
			}
		});
		
		
		frictionCheckBox = new JCheckBox();
		frictionCheckBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(frictionCheckBox.isSelected()){
					frictionCheckBox.setSelected(false);
				}
				else{
					frictionCheckBox.setSelected(true);
				}
				friction = !friction;
				win.getGame().getCat().setFriction(friction);
			}
		});
		
		themeBox = new JComboBox<String>(themes);
		themeBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				int selected =  ((JComboBox)event.getSource()).getSelectedIndex();
				currentTheme = skyImages[selected];
			}
		});
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(currentTheme, 0, -600, null, null);
		
		super.paint(g);
	}
	
	public BufferedImage getTheme(){
		return currentTheme;
	}
}
	
