import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//
public class Obstacle {
	int x, y;
	Rectangle rect;
	BufferedImage img;
	
	public Obstacle(int xPos, int yPos, BufferedImage image){
		x = xPos;
		y = yPos;
		img = image;
		rect = new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
	public Obstacle(int xPos, int yPos){
		x = xPos;
		y = yPos;
		try{
			img =  ImageIO.read(new File("Pics\\DefaultObstacle.png"));
		}catch(Exception e){}
		rect = new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
	public Rectangle getRectangle(){
		return rect;
	}
	public BufferedImage getImage(){
		return img;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
