import java.awt.Color;
import java.awt.image.BufferedImage;


public class Theme {
	private BufferedImage sky;
	private BufferedImage title;
	private Color groundColor;
	private Color fontColor;
	private String catColor;
	
	public Theme(BufferedImage s, BufferedImage t, Color gc, Color fc, String cc){
		catColor = cc;
		if(fc == null){
			fontColor = Color.black;
		}else{
			fontColor = fc;
		}
		sky = s;
		title = t;
		groundColor = gc;
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
}
