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
		rect = new Rectangle(x, y);
		if(image!=null){
			img = image;
		}else{
			try{
				img =  ImageIO.read(new File("Pics\\DefaultObstacle.png"));
			}catch(Exception e){}
		}
		rect = new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
	/*public Obstacle(Rectangle r, BufferedImage image){
		rect = r;
		img = image;
		x = rect.x;
		x = rect.y;
	}*/
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
