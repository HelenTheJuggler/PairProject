import java.awt.Color;
import java.awt.image.BufferedImage;


public class Theme {
	private BufferedImage sky;
	private Color skyColor;
	private Color groundColor;
	private String catColor;
	
	public Theme(BufferedImage s, Color sc, Color gc, String cc){
		catColor = cc;
		sky = s;
		skyColor = sc;
		groundColor = gc;
	}
	
	public Color getGroundColor(){
		return groundColor;
	}
	
	public Color getSkyColor(){
		return skyColor;
	}
	
	public BufferedImage getSkyImage(){
		return sky;
	}
	
	public String getCatColor(){
		return catColor;
	}
}
