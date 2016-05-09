import java.awt.Color;
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
	
	private final String[] buttonNames = {"play", "qMark", "gear", "replay", "home"};
	private ImageIcon[] icons;
	
	private BufferedImage title;
	
	public Theme(BufferedImage s, Color gc, Color fc, String cc, String tc){
		catColor = cc;
		if(fc == null){
			fontColor = Color.black;
		}else{
			fontColor = fc;
		}
		sky = s;
		titleColor =tc;
		groundColor = gc;
		try{
			title = ImageIO.read(new File("Pics\\Title"+ titleColor + ".png"));
		}catch(Exception e){}
		initImageIcons();
	}
	
	private void initImageIcons(){
		ImageIcon play;
		ImageIcon playSelected;
		ImageIcon qMark;
		ImageIcon qMarkSelected;
		ImageIcon gear;
		ImageIcon gearSelected;
		ImageIcon replay;
		ImageIcon replaySelected;
		ImageIcon home;
		ImageIcon homeSelected;
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
