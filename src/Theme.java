import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Theme {
	private BufferedImage sky;
	private Color groundColor;
	private Color fontColor;
	private String catColor;
	private String titleColor;
	private String obsColor;
	
	private final String[] buttonNames = {"play", "qMark", "gear", "replay", "home"};
	private ImageIcon[] icons;
	
	private BufferedImage title;
	
	public Theme(BufferedImage s, Color gc, Color fc, String cc, String tc, String oc){
		catColor = cc;
		if(fc == null){
			fontColor = Color.black;
		}else{
			fontColor = fc;
		}
		sky = s;
		titleColor =tc;
		groundColor = gc;
		obsColor = oc;
		try{
			title = ImageIO.read(new File("Pics\\Title"+ titleColor + ".png"));
		}catch(Exception e){}
		initImageIcons();
	}
	
	private void initImageIcons(){
		String filePath = "Pics\\Buttons\\" + titleColor + "\\";
		
		ImageIcon[] i = {new ImageIcon(filePath + "Play1.png"), new ImageIcon(filePath + "Play2.png"),
			new ImageIcon(filePath + "qMark1.png"), new ImageIcon(filePath + "qMark2.png"),
			new ImageIcon(filePath + "Gear1.png"), new ImageIcon(filePath + "Gear2.png"),
			new ImageIcon(filePath + "Replay1.png"),new ImageIcon(filePath + "Replay2.png"),
			new ImageIcon(filePath + "Home1.png"), new ImageIcon(filePath + "Home2.png")};
		
		icons = i;
		for(int x=0; x<icons.length; x++){
			icons[x].setImage(icons[x].getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		}
	}
	
	public Color getGroundColor(){
		return groundColor;
	}
	
	public BufferedImage getSkyImage(){
		return sky;
	}
	
	public String getCatColor(){
		return catColor;
	}
	
	public Color getFontColor(){
		return fontColor;
	}
	
	public BufferedImage getTitle(){
		return title;
	}
	
	public String getObsColor(){
		return obsColor;
	}
	
	public ImageIcon[] getImageIcons(String buttonName){
		ImageIcon[] buttonIcons = new ImageIcon[2];
		for(int i=0; i<buttonNames.length; i++){
			if(buttonNames[i].equals(buttonName)){
				buttonIcons[0] = icons[i*2];
				buttonIcons[1] = icons[i*2+1];
			}
		}
		return buttonIcons;
	}
}
