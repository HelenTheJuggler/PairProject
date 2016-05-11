import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Goal {
	private Point goalPos;
	private BufferedImage goalImage;
	
	public Goal(Point pos){
		goalPos = pos;
		try{
			goalImage = ImageIO.read(new File("Pics\\Bird.jpg"));
			goalImage = Catapult.scaleImage(goalImage, 75.0/goalImage.getWidth());
		}catch(Exception e){}
	}
	
	public Goal(){
		goalPos = new Point(600, 300);
		try{
			goalImage = ImageIO.read(new File("Pics\\Bird.jpg"));
			goalImage = Catapult.scaleImage(goalImage, 75.0/goalImage.getWidth());
		}catch(Exception e){}
	}
	
	public int getX(){
		return (int) goalPos.getX();
	}
	
	public int getY(){
		return (int) goalPos.getY();
	}
	
	public BufferedImage getGoalImage(){
		return goalImage;
	}
	
	public Rectangle getGoalBounds(){
		return new Rectangle((int)goalPos.getX(), (int)goalPos.getY(), 
				goalImage.getWidth(), goalImage.getHeight());
	}
}
