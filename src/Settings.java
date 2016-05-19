
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Settings extends JPanel{
	private Window win;
	private JPanel panel;
	
	private String[] names = {"Kitten","Cat"};
	private JLabel catTypeLabel;
	private JComboBox<String> catType;
	
	private JCheckBox cursorCheckBox;
	private JLabel cursorLabel;
	
	private String[] themeNames = {"Day", "Night", "Sunset"};
	private Theme[] themes;
	private JComboBox<String> themeBox;
	private Theme currentTheme;
	private JLabel themeLabel;
	
	private JButton back;
	
	private final int fontSize = 40;

	public Settings(Window w){
		win = w;
		setSize(new Dimension(700,400));
		setBackground(new Color(0,0,0,0));
		initThemes();
		setUpGUIComponents();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.LINE_END;
		add(catTypeLabel, c);
		
		c.gridx=0;
		c.gridy=2;
		add(themeLabel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		add(cursorLabel, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		add(catType, c);
		
		c.gridx = 1;
		c.gridy = 1;
		add(cursorCheckBox, c);
		
		c.gridx=1;
		c.gridy=2;
		add(themeBox, c);
		
		c.gridwidth = 2;
		c.gridy=3;
		c.gridx=0;
		c.anchor = GridBagConstraints.CENTER;
		add(back, c);
	}
	
	private void initThemes(){
		themes = new Theme[themeNames.length];
		try {
			BufferedImage sky = ImageIO.read(new File("Pics\\Skies\\DaySky.png"));
			Color groundColor = new Color(0, 102, 0);
			String catColor = "Orange";
			themes[0] = new Theme(sky, groundColor, null, catColor, "Black");
			
			sky = ImageIO.read(new File("Pics\\Skies\\NightSky.png"));
			groundColor = new Color(10, 54, 0);
			Color fontColor = new Color(255, 200, 0);
			catColor = "White";
			themes[1] = new Theme(sky, groundColor, fontColor, catColor, "Yellow");
			
			sky = ImageIO.read(new File("Pics\\Skies\\SunsetSky.png"));
			groundColor = Color.BLACK;
			fontColor = Color.BLACK;
			catColor = "Black";
			themes[2] = new Theme(sky, groundColor, fontColor, catColor, "Black");
		} catch (IOException e) {}
		currentTheme = themes[0];
	}
	
	private void setUpGUIComponents(){
		back = new JButton("Back");
		back.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				win.goToIntro();
			}
		});
		
		catType = new JComboBox<String>(names);
		catType.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
		catType.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				int selected = ((JComboBox)event.getSource()).getSelectedIndex();
				win.getGame().getCat().setType(names[selected]);
			}
		});
		
		
		cursorCheckBox = new JCheckBox();
		ImageIcon checked = new ImageIcon("Pics\\Buttons\\Checked.png");
		ImageIcon notChecked = new ImageIcon("Pics\\Buttons\\NotChecked.png");
		cursorCheckBox.setIcon(notChecked);
		cursorCheckBox.setPressedIcon(notChecked);
		cursorCheckBox.setRolloverIcon(notChecked);
		cursorCheckBox.setSelectedIcon(checked);
		cursorCheckBox.setBorder(null);
		cursorCheckBox.setBackground(new Color(0,0,0,0));
		cursorCheckBox.setSelected(true);
		cursorCheckBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(cursorCheckBox.isSelected()){
					win.setCustomCursor();
				}else{
					win.setDefaultCursor();
				}
			}
		});
		
		themeBox = new JComboBox<String>(themeNames);
		themeBox.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
		themeBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				int selected =  ((JComboBox)event.getSource()).getSelectedIndex();
				currentTheme = themes[selected];
				win.getGame().getCat().setColor(currentTheme.getCatColor());
				updateTheme();
			}
		});
		
		catTypeLabel = new JLabel("Type of Cat: ");
		catTypeLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
		catTypeLabel.setForeground(currentTheme.getFontColor());
		
		cursorLabel = new JLabel("Cat paw cursor: ");
		cursorLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
		cursorLabel.setForeground(currentTheme.getFontColor());
		
		themeLabel = new JLabel("Theme: ");
		themeLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
		themeLabel.setForeground(currentTheme.getFontColor());
	}
	
	private void updateTheme(){
		catTypeLabel.setForeground(currentTheme.getFontColor());
		cursorLabel.setForeground(currentTheme.getFontColor());
		themeLabel.setForeground(currentTheme.getFontColor());
		win.updatedTheme();
		repaint();
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(currentTheme.getSkyImage(), 0, -600, null, null);
		super.paint(g);
	}
	
	public Theme getTheme(){
		return currentTheme;
	}
}
	
