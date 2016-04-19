import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Obstacle {
	int x, y;
	Rectangle rect;
	BufferedImage img;
	public Obstacle(int xPos, int yPos, BufferedImage image){
		x = xPos;
		y = yPos;
		rect = new Rectangle(x, y);
		img = image;
	}
	public Obstacle(Rectangle r, BufferedImage image){
		rect = r;
		img = image;
		x = rect.x;
		x = rect.y;
		
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
